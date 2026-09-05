package com.sunglasses.linglingcalculator

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.sunglasses.linglingcalculator.utils.UiSettingsManager

class PreviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 从 Intent 获取参数，若无则使用已保存的设置
        val scale = intent.getFloatExtra("scale", UiSettingsManager.getScale(this))
        val density = intent.getIntExtra("density", UiSettingsManager.getDensity(this))

        // 应用密度设置
        val metrics = resources.displayMetrics
        val finalDensity = (density * scale).toInt().coerceAtLeast(72)
        metrics.density = finalDensity / 160f
        metrics.scaledDensity = metrics.density * resources.configuration.fontScale
        metrics.densityDpi = finalDensity

        setContentView(R.layout.activity_main)

        // 禁用所有按钮，仅作预览
        val root = findViewById<ViewGroup>(android.R.id.content)
        disableButtons(root)
    }

    private fun disableButtons(view: View) {
        if (view is Button) {
            view.isEnabled = false
        } else if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                disableButtons(view.getChildAt(i))
            }
        }
    }
}