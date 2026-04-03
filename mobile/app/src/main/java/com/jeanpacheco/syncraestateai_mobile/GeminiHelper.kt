package com.jeanpacheco.syncraestateai_mobile

import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.jeanpacheco.syncraestateai_mobile.BuildConfig.*

object GeminiHelper {

    suspend fun generateSmartPitch(clientName: String, requirement: String): String {
        return withContext(Dispatchers.IO) { // Mandamos la tarea a un hilo secundario para no congelar la app
            try {
                // Inicializamos el modelo (gemini-2.5-flash es el más rápido para texto)
                val generativeModel = GenerativeModel(
                    modelName = "gemini-2.5-flash",
                    apiKey = GEMINI_API_KEY // <-- ¡Adiós al texto quemado!
                )

                // Este es el "Super Prompt" que le da el contexto a la IA
                val prompt = """
                    Actúa como un asesor inmobiliario experto y altamente persuasivo de la agencia "Syncra". 
                    Escribe un mensaje de seguimiento (Smart Pitch) para enviarlo por WhatsApp a tu cliente llamado $clientName.
                    Sabemos que el cliente está buscando lo siguiente: $requirement.
                    El mensaje debe ser empático, amigable, profesional, no muy largo y debe terminar con una pregunta atractiva que lo invite a agendar una visita o responder.
                    No dejes espacios en blanco como [Tu Nombre], actúa directamente como el asesor.
                """.trimIndent()

                // Llamamos a Gemini
                val response = generativeModel.generateContent(prompt)

                // Retornamos el texto generado
                response.text ?: "No se pudo generar el mensaje."

            } catch (e: Exception) {
                "Error al conectar con la IA: ${e.localizedMessage}"
            }
        }
    }
}