package com.sunglasses.linglingcalculator.utils

import android.content.Context
import android.content.SharedPreferences

object UiSettingsManager {
    private const val PREFS_NAME = "ui_settings"
    private const val KEY_SCALE = "scale_factor"
    private const val KEY_DENSITY = "density_dpi"

    fun getScale(context: Context): Float =
        getPrefs(context).getFloat(KEY_SCALE, 1.0f)

    fun getDensity(context: Context): Int =
        getPrefs(context).getInt(KEY_DENSITY, context.resources.displayMetrics.densityDpi)

    fun setScale(context: Context, scale: Float) {
        getPrefs(context).edit().putFloat(KEY_SCALE, scale).apply()
    }

    fun setDensity(context: Context, density: Int) {
        getPrefs(context).edit().putInt(KEY_DENSITY, density).apply()
    }

    fun reset(context: Context) {
        getPrefs(context).edit().clear().apply()
    }

    private fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
}