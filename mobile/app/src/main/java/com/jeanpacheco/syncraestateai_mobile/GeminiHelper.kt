package com.jeanpacheco.syncraestateai_mobile

import android.content.Context
import com.google.ai.client.generativeai.GenerativeModel
// ---> NUEVOS IMPORTS PARA LEER EL USUARIO <---
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await // MUY IMPORTANTE PARA QUE FUNCIONE EL .await()

object GeminiHelper {

    suspend fun generarSmartPitch(context: Context, datosCliente: String): String? {
        return try {
            // ---> 1. OBTENER EL NOMBRE DEL AGENTE DESDE FIREBASE <---
            var agentName = "Agente de Syncra Estate" // Nombre por defecto de seguridad
            val currentUserId = FirebaseAuth.getInstance().currentUser?.uid

            // Si hay un usuario logueado, vamos a buscar su nombre a la colección "users"
            if (currentUserId != null) {
                val db = FirebaseFirestore.getInstance()
                val userDoc = db.collection("users").document(currentUserId).get().await()

                val nombre = userDoc.getString("nombre") ?: ""
                val apellido = userDoc.getString("apellido") ?: ""

                // Unimos nombre y apellido (Ej: "Jean Pacheco")
                if (nombre.isNotEmpty()) {
                    agentName = "$nombre $apellido".trim()
                }
            }

            // ---> 2. CONFIGURACIÓN DEL MODELO <---
            // Pasamos al modelo 2.5-flash para evitar alucinaciones con firmas falsas y mejorar el razonamiento
            val generativeModel = GenerativeModel(
                modelName = "gemini-2.5-flash",
                apiKey = BuildConfig.GEMINI_API_KEY
            )

            // 3. Leemos las configuraciones de tu pantalla
            val prefs = AIPrefs(context)
            val tono = prefs.getTone()
            val longitud = prefs.getLength()
            val traducir = prefs.getTranslate()

            // Regla condicional para tu interruptor de traducción
            val reglaTraduccion = if (traducir) {
                "- IDIOMA (¡CRÍTICO!): Analiza el idioma de las notas e intereses del cliente. Si el texto del cliente está en INGLÉS (ej. 'looking for a house'), DEBES redactar absolutamente todo el Smart Pitch en INGLÉS. Adapta tu idioma al del prospecto obligatoriamente."
            } else {
                "- IDIOMA: Redacta el mensaje estrictamente en Español, sin importar en qué idioma estén los datos del cliente."
            }

            // ---> 4. PROMPT MAESTRO DINÁMICO <---
            val promptMaestro = """
            Eres un experto agente inmobiliario de Syncra Estate. Tu nombre real es $agentName.
            Genera un Smart Pitch persuasivo, directo y listo para enviarse a este prospecto:
            
            Datos del cliente: $datosCliente
            
            REGLAS ESTRICTAS DE REDACCIÓN:
            - Tono de comunicación: $tono.
            - Longitud requerida: $longitud.
            - IMPORTANTE: Despídete y firma el mensaje al final utilizando obligatoriamente tu nombre ($agentName). NUNCA dejes marcadores de posición vacíos como [Tu Nombre], [Nombre del Agente] o similares.
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