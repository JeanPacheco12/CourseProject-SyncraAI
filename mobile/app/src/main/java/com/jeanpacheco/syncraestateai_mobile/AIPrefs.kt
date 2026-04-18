package com.jeanpacheco.syncraestateai_mobile

import android.content.Context

class AIPrefs(context: Context) {
    private val prefs = context.getSharedPreferences("ai_settings", Context.MODE_PRIVATE)

    // Guardar las 4 opciones exactas de la pantalla
    fun saveSettings(tone: String, length: String, autoGenerate: Boolean, translate: Boolean) {
        prefs.edit().apply {
            putString("TONE", tone)
            putString("LENGTH", length)
            putBoolean("AUTO_GENERATE", autoGenerate)
            putBoolean("TRANSLATE", translate)
            apply()
        }
    }

    // Leer las opciones (con valores por defecto basados en la UI)
    fun getTone(): String = prefs.getString("TONE", "Formal") ?: "Formal"
    fun getLength(): String = prefs.getString("LENGTH", "Corto") ?: "Corto" // <-- Solo cambiamos el texto por defecto aquí
    fun getAutoGenerate(): Boolean = prefs.getBoolean("AUTO_GENERATE", true)
    fun getTranslate(): Boolean = prefs.getBoolean("TRANSLATE", false)
}