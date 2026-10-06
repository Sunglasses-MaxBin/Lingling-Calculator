package com.sunglasses.linglingcalculator

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sunglasses.linglingcalculator.dialog.ThemeDialog
import com.sunglasses.linglingcalculator.utils.SettingsManager

class SettingsActivity : AppCompatActivity() {

    private lateinit var settingsManager: SettingsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        settingsManager = SettingsManager.getInstance(this)
        settingsManager.applyTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // 返回按钮
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            finish()
        }

        // 外观设置
        findViewById<View>(R.id.card_appearance).setOnClickListener {
            showThemeDialog()
        }

        // 界面设置
        findViewById<View>(R.id.card_interface).setOnClickListener {
            startActivity(Intent(this, InterfaceSettingsActivity::class.java))
        }

        // 关于本应用（改为跳转 Activity）
        findViewById<View>(R.id.card_about).setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

    private fun showThemeDialog() {
        ThemeDialog(this) { isDark ->
            settingsManager.setThemeMode(isDark)
            Toast.makeText(this, if (isDark) "已切换到深色主题" else "已切换到浅色主题", Toast.LENGTH_SHORT).show()
        }.show()
    }
}