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
    private const val GITHUB_API_URL = "https://api.github.com/repos/Sunglasses-MaxBin/Lingling-Calculator/releases/latest"
    private const val GITHUB_REPO_URL = "https://github.com/Sunglasses-MaxBin/Lingling-Calculator"

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
                    val latestVersion = json.getString("tag_name").removePrefix("v")
                    val releaseUrl = json.getString("html_url")

                    Result.success(
                        UpdateInfo(
                            latestVersion = latestVersion,
                            currentVersion = currentVersion,
                            isUpdateAvailable = compareVersions(latestVersion, currentVersion) > 0,
                            releaseUrl = releaseUrl
                        )
                    )
                } else {
                    Result.failure(Exception("檢查更新失敗，請稍後再試"))
                }
            } catch (e: Exception) {
                Result.failure(Exception("網絡連接失敗，請檢查網絡設定"))
            }
        }
    }

    private fun compareVersions(v1: String, v2: String): Int {
        val parts1 = v1.split(".").map { it.toIntOrNull() ?: 0 }
        val parts2 = v2.split(".").map { it.toIntOrNull() ?: 0 }
        for (i in 0 until maxOf(parts1.size, parts2.size)) {
            val p1 = if (i < parts1.size) parts1[i] else 0
            val p2 = if (i < parts2.size) parts2[i] else 0
            if (p1 != p2) return p1.compareTo(p2)
        }
        return 0
    }

    fun getRepoUrl() = GITHUB_REPO_URL
}