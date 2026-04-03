package com.jeanpacheco.syncraestateai_mobile

import com.google.ai.client.generativeai.GenerativeModel
import android.content.Context // <-- ¡Este era el import que faltaba!
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.jeanpacheco.syncraestateai_mobile.BuildConfig.*

object GeminiHelper {

    suspend fun generarSmartPitch(context: Context, datosCliente: String): String? {
        return try {
            val generativeModel = GenerativeModel(
                modelName = "gemini-2.5-flash",
                apiKey = BuildConfig.GEMINI_API_KEY
            )

            // 1. Leemos las configuraciones de tu pantalla
            val prefs = AIPrefs(context)
            val tono = prefs.getTone()
            val longitud = prefs.getLength()
            val traducir = prefs.getTranslate()

            // 2. Regla condicional para tu interruptor de traducción
            val reglaTraduccion = if (traducir) {
                "- IMPORTANTE: Detecta el idioma nativo de los datos del cliente y escribe la propuesta en ESE mismo idioma."
            } else {
                "- Escribe la propuesta en el idioma predeterminado de la plataforma (Español)."
            }

            // 3. Armamos el Prompt exacto
            val promptMaestro = """
            Eres un experto agente inmobiliario de Syncra Estate. Genera un Smart Pitch para este prospecto:
            Datos del cliente: $datosCliente
            
            REGLAS ESTRICTAS DE REDACCIÓN:
            - Tono de comunicación: $tono.
            - Longitud requerida: $longitud.
            $reglaTraduccion
        """.trimIndent()

            val response = generativeModel.generateContent(promptMaestro)
            response.text

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}