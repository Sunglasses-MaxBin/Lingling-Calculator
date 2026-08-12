package com.sunglasses.linglingcalculator.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

class SettingsManager private constructor(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("lingling_settings", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_THEME = "theme_mode"
        private const val THEME_DARK = "dark"
        private const val THEME_LIGHT = "light"

        @Volatile
        private var instance: SettingsManager? = null

        fun getInstance(context: Context): SettingsManager {
            return instance ?: synchronized(this) {
                instance ?: SettingsManager(context).also { instance = it }
            }
        }
    }

    fun setThemeMode(isDark: Boolean) {
        prefs.edit().putString(KEY_THEME, if (isDark) THEME_DARK else THEME_LIGHT).apply()
        applyTheme(isDark)
    }

    fun isDarkMode(): Boolean = prefs.getString(KEY_THEME, THEME_LIGHT) == THEME_DARK

    fun applyTheme(isDark: Boolean = isDarkMode()) {
        AppCompatDelegate.setDefaultNightMode(
            if (isDark) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
    }
}