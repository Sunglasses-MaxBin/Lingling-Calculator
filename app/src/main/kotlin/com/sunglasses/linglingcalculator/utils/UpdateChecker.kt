package com.sunglasses.linglingcalculator.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class UpdateInfo(
    val latestVersion: String,
    val currentVersion: String,
    val isUpdateAvailable: Boolean,
    val releaseUrl: String
)

object UpdateChecker {
    // ⚠️ 请务必把下面的 YOUR_USERNAME 替换为您的真实 GitHub 用户名！
    private const val GITHUB_API_URL = "https://api.github.com/repos/Sunglasses-MaxBin/Lingling-Calculator/releases/latest"
    private const val GITHUB_REPO_URL = "https://github.com/Sunglasses-MaxBin/Lingling-Calculator"

    /**
     * 检查更新
     * 注意：这是一个 suspend 函数，内部使用 withContext(Dispatchers.IO) 确保网络请求在主线程之外执行
     */
    suspend fun checkForUpdate(currentVersion: String): Result<UpdateInfo> {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(GITHUB_API_URL)
                val connection = url.openConnection() as HttpURLConnection
                connection.setRequestProperty("Accept", "application/vnd.github.v3+json")
                connection.connectTimeout = 10000
                connection.readTimeout = 10000

                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().readText()
                    val json = JSONObject(response)
                    
                    // 获取最新版本号（去除前缀 'v'，例如 v1.7.1 -> 1.7.1）
                    val latestVersion = json.getString("tag_name").removePrefix("v")
                    val releaseUrl = json.getString("html_url")

                    // 比较版本
                    val isUpdateAvailable = compareVersions(latestVersion, currentVersion) > 0

                    Result.success(
                        UpdateInfo(
                            latestVersion = latestVersion,
                            currentVersion = currentVersion,
                            isUpdateAvailable = isUpdateAvailable,
                            releaseUrl = releaseUrl
                        )
                    )
                } else if (connection.responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                    // 404 通常意味着仓库/用户名不对，或者还没有发布 Release
                    Result.failure(Exception("未找到发布版本，请检查 GitHub 仓库设置"))
                } else {
                    Result.failure(Exception("检查更新失败（错误码：${connection.responseCode}）"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("网络连接失败，请检查网络设置"))
            }
        }
    }

    /**
     * 比较两个语义化版本号
     * @return 正数表示 v1 > v2，负数表示 v1 < v2，0 表示相等
     */
    private fun compareVersions(v1: String, v2: String): Int {
        val parts1 = v1.split(".").map { it.toIntOrNull() ?: 0 }
        val parts2 = v2.split(".").map { it.toIntOrNull() ?: 0 }

        val maxLength = maxOf(parts1.size, parts2.size)
        for (i in 0 until maxLength) {
            val p1 = if (i < parts1.size) parts1[i] else 0
            val p2 = if (i < parts2.size) parts2[i] else 0
            if (p1 != p2) {
                return p1.compareTo(p2)
            }
        }
        return 0
    }

    fun getRepoUrl(): String {
        return GITHUB_REPO_URL
    }
}