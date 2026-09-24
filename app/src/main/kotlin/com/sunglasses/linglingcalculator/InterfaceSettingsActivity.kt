package com.sunglasses.linglingcalculator

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sunglasses.linglingcalculator.utils.UiSettingsManager

class InterfaceSettingsActivity : AppCompatActivity() {

    private lateinit var etScale: EditText
    private lateinit var etDensity: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interface_settings)

        etScale = findViewById(R.id.et_scale)
        etDensity = findViewById(R.id.et_density)

        // 返回按钮
        findViewById<ImageButton>(R.id.btn_back).setOnClickListener {
            finish()
        }

        // 显示当前设置
        etScale.setText(UiSettingsManager.getScale(this).toString())
        etDensity.setText(UiSettingsManager.getDensity(this).toString())

        findViewById<Button>(R.id.btn_save).setOnClickListener {
            saveSettings()
        }

        // 预览按钮：传递当前输入值
        findViewById<Button>(R.id.btn_preview).setOnClickListener {
            previewWithCurrentSettings()
        }

        findViewById<Button>(R.id.btn_reset).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("恢复默认")
                .setMessage("是否恢复默认？")
                .setPositiveButton("是") { _, _ ->
                    UiSettingsManager.reset(this)
                    Toast.makeText(this, "已恢复默认，请重启应用", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton("否", null)
                .show()
        }
    }

    private fun saveSettings() {
        val scale = etScale.text.toString().toFloatOrNull()
        if (scale == null || scale < 0.25f || scale > 5.0f) {
            Toast.makeText(this, "倍数必须在 0.25 ~ 5.0 之间", Toast.LENGTH_SHORT).show()
            return
        }

        val density = etDensity.text.toString().toIntOrNull()
        if (density == null || density < 72) {
            Toast.makeText(this, "Density 最小为 72", Toast.LENGTH_SHORT).show()
            return
        }

        UiSettingsManager.setScale(this, scale)
        UiSettingsManager.setDensity(this, density)
        Toast.makeText(this, "设置已保存，请重启应用", Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun previewWithCurrentSettings() {
        val scale = etScale.text.toString().toFloatOrNull()
        if (scale == null || scale < 0.25f || scale > 5.0f) {
            Toast.makeText(this, "倍数必须在 0.25 ~ 5.0 之间", Toast.LENGTH_SHORT).show()
            return
        }

        val density = etDensity.text.toString().toIntOrNull()
        if (density == null || density < 72) {
            Toast.makeText(this, "Density 最小为 72", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, PreviewActivity::class.java).apply {
            putExtra("scale", scale)
            putExtra("density", density)
        }
        startActivity(intent)
    }
}
