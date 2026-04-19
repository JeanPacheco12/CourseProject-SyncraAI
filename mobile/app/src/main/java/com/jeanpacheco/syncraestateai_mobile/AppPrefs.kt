package com.jeanpacheco.syncraestateai_mobile

import android.content.Context

class AppPrefs(context: Context) {
    private val prefs = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    // 1. Guardar y leer el Modo Oscuro
    fun isDarkMode(): Boolean = prefs.getBoolean("DARK_MODE", false) // Por defecto: apagado

    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean("DARK_MODE", enabled).apply()
    }

    // Nota: ¡Aquí mismo agregaremos los de notificaciones y biometría después!
}