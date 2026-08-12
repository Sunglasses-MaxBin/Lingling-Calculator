package com.sunglasses.linglingcalculator.dialog

import android.app.AlertDialog
import android.content.Context
import com.sunglasses.linglingcalculator.utils.SettingsManager

class ThemeDialog(
    context: Context,
    private val onThemeSelected: (Boolean) -> Unit
) {
    private val dialog: AlertDialog

    init {
        val settingsManager = SettingsManager.getInstance(context)
        val isDark = settingsManager.isDarkMode()

        dialog = AlertDialog.Builder(context)
            .setTitle("选择主题")
            .setSingleChoiceItems(
                arrayOf("浅色主题", "深色主题"),
                if (isDark) 1 else 0
            ) { _, which ->
                onThemeSelected(which == 1)
                dialog.dismiss()
            }
            .setNegativeButton("取消") { _, _ -> dialog.dismiss() }
            .create()
    }

    fun show() = dialog.show()
}