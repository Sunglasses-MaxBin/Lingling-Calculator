package com.sunglasses.linglingcalculator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunglasses.linglingcalculator.adapter.SettingsAdapter
import com.sunglasses.linglingcalculator.dialog.AboutDialog
import com.sunglasses.linglingcalculator.dialog.ThemeDialog
import com.sunglasses.linglingcalculator.dialog.UpdateDialog
import com.sunglasses.linglingcalculator.utils.SettingsManager
import com.sunglasses.linglingcalculator.utils.UpdateChecker
import kotlinx.coroutines.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        settingsManager = SettingsManager.getInstance(this)
        settingsManager.applyTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener { finish() }

        val rvSettings = findViewById<RecyclerView>(R.id.rvSettings)
        rvSettings.layoutManager = LinearLayoutManager(this)
        rvSettings.adapter = SettingsAdapter(
            onAppearanceClick = { showThemeDialog() },
            onAboutClick = { showAboutDialog() }
        )
    }

    private fun showThemeDialog() {
        ThemeDialog(this) { isDark ->
            settingsManager.setThemeMode(isDark)
            Toast.makeText(this, if (isDark) "已切换深色主题" else "已切换浅色主题", Toast.LENGTH_SHORT).show()
        }.show()
    }

    private fun checkUpdate() {
        val dialog = UpdateDialog(this)
        dialog.setLoading()
        dialog.show()

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val versionName = withContext(Dispatchers.IO) {
                    packageManager.getPackageInfo(packageName, 0).versionName ?: "1.0.0"
                }
                val result = UpdateChecker.checkForUpdate(versionName)
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
                dialog.showError("获取版本信息失败")
            }
        }
    }

    private fun showAboutDialog() = AboutDialog(this).show()

    private fun openUrl(url: String) {
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: Exception) {
            Toast.makeText(this, "无法打开链接", Toast.LENGTH_SHORT).show()
        }
    }
}