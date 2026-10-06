package com.sunglasses.linglingcalculator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.sunglasses.linglingcalculator.dialog.UpdateDialog
import com.sunglasses.linglingcalculator.utils.UpdateChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        // 返回按钮
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            finish()
        }

        // 动态获取版本号
        val tvVersion = findViewById<TextView>(R.id.tv_version)
        try {
            val versionName = packageManager.getPackageInfo(packageName, 0).versionName
            tvVersion.text = versionName
        } catch (e: Exception) {
            tvVersion.text = "1.7.0"
        }

        // 检查更新点击
        findViewById<LinearLayout>(R.id.row_check_update).setOnClickListener {
            checkUpdate()
        }

        // Github仓库点击
        findViewById<LinearLayout>(R.id.row_github).setOnClickListener {
            // ⚠️ 请务必把下面的 YOUR_USERNAME 替换为您的真实 GitHub 用户名！
            val url = "https://github.com/Sunglasses-MaxBin/Lingling-Calculator"
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            } catch (e: Exception) {
                Toast.makeText(this, "无法打开链接", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkUpdate() {
        val dialog = UpdateDialog(this)
        dialog.show() // 此时弹窗内部已经是“正在检查”状态，无需再手动调用 setLoading()

        // 使用 lifecycleScope，自动绑定 Activity 生命周期，避免内存泄漏和界面销毁后崩溃
        lifecycleScope.launch {
            try {
                // 获取版本号（在 IO 线程执行，不阻塞主线程）
                val versionName = withContext(Dispatchers.IO) {
                    packageManager.getPackageInfo(packageName, 0).versionName ?: "1.0.0"
                }

                // 执行网络检查（UpdateChecker 内部已经使用了 withContext(Dispatchers.IO)，此处安全）
                val result = UpdateChecker.checkForUpdate(versionName)

                // 确保在更新 UI 之前 Activity 没有被销毁
                if (isFinishing || isDestroyed) {
                    dialog.dismiss()
                    return@launch
                }

                result.onSuccess { info ->
                    if (info.isUpdateAvailable) {
                        dialog.showUpdateAvailable(info) {
                            openUrl(info.releaseUrl)
                            dialog.dismiss()
                        }
                    } else {
                        dialog.showLatest(info) {
                            openUrl(UpdateChecker.getRepoUrl())
                            dialog.dismiss()
                        }
                    }
                }.onFailure { e ->
                    dialog.showError(e.message ?: "未知错误")
                }
            } catch (e: Exception) {
                if (!isFinishing && !isDestroyed) {
                    dialog.showError("获取版本信息失败：${e.message}")
                }
            }
        }
    }

    private fun openUrl(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            Toast.makeText(this, "无法打开链接", Toast.LENGTH_SHORT).show()
        }
    }
}