package com.jeanpacheco.syncraestateai_mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

// Estos son los imports nuevos y agregados para la función de navegación que me faltaban:
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.jeanpacheco.syncraestateai_mobile.ui.theme.SyncraEstateAIMobileTheme

import androidx.navigation.NavType
import androidx.navigation.navArgument

import com.google.firebase.firestore.FirebaseFirestore
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*

        fun resetearBaseDeDatos() {
            val db = FirebaseFirestore.getInstance()

            // --- 1. SIETE CLIENTES (Adaptados a tus campos exactos) ---
            val clientes = listOf(
                mapOf(
                    "id" to "cli_1", "name" to "Anderson Souza", "status" to "Nuevo",
                    "phone" to "+502 4555 0101", "email" to "anderson@email.com", "budget" to "Q. 3,500,000",
                    "location" to "Zona 14, Ciudad de Guatemala", "interest" to "Apartamento", "profession" to "Ingeniero",
                    "nationality" to "Brasileño", "dob" to "12 de Mayo, 1990", "requirement" to "Apartamento moderno y seguro.",
                    "time" to "Hoy"
                ),
                mapOf(
                    "id" to "cli_2", "name" to "Elena Martínez", "status" to "Nuevo",
                    "phone" to "+502 5555 0102", "email" to "elena.m@email.com", "budget" to "Q. 1,200,000",
                    "location" to "Antigua Guatemala", "interest" to "Casa colonial", "profession" to "Diseñadora",
                    "nationality" to "Española", "dob" to "20 de Agosto, 1995", "requirement" to "Casa con acabados coloniales.",
                    "time" to "Ayer"
                ),
                mapOf(
                    "id" to "cli_3", "name" to "Carlos Ruiz", "status" to "En Seguimiento",
                    "phone" to "+502 3255 0103", "email" to "cruiz@email.com", "budget" to "Q. 800,000",
                    "location" to "Carretera a El Salvador", "interest" to "Terreno", "profession" to "Contador",
                    "nationality" to "Guatemalteco", "dob" to "05 de Enero, 1985", "requirement" to "Terreno para construir.",
                    "time" to "Hace 2 días"
                ),
                mapOf(
                    "id" to "cli_4", "name" to "Lucía Gómez", "status" to "Visita hoy",
                    "phone" to "+502 4455 0104", "email" to "lucia.g@email.com", "budget" to "Q. 2,500,000",
                    "location" to "Zona 15, Ciudad de Guatemala", "interest" to "Casa", "profession" to "Empresaria",
                    "nationality" to "Mexicana", "dob" to "14 de Marzo, 1982", "requirement" to "Casa con amplio jardín.",
                    "time" to "Hace 3 días"
                ),
                mapOf(
                    "id" to "cli_5", "name" to "Roberto Chang", "status" to "Cita",
                    "phone" to "+502 5555 0105", "email" to "r.chang@email.com", "budget" to "Q. 1,800,000",
                    "location" to "Cayalá", "interest" to "Apartamento", "profession" to "Chef",
                    "nationality" to "Chino", "dob" to "30 de Noviembre, 1989", "requirement" to "Cerca de zonas comerciales.",
                    "time" to "Hace 1 semana"
                ),
                mapOf(
                    "id" to "cli_6", "name" to "Marina Torres", "status" to "Cerrado",
                    "phone" to "+502 3355 0106", "email" to "marina.t@email.com", "budget" to "Q. 950,000",
                    "location" to "Mixco", "interest" to "Casa de condominio", "profession" to "Profesora",
                    "nationality" to "Guatemalteca", "dob" to "18 de Julio, 1993", "requirement" to "Algo pequeño y seguro.",
                    "time" to "Hace 2 semanas"
                ),
                mapOf( // Este es el que me pasaste en la captura
                    "id" to "cli_7", "name" to "Fernando Ruiz", "status" to "Pendiente",
                    "phone" to "+502 5555 9876", "email" to "fruiz.negocios@hotmail.com", "budget" to "Q. 950,000",
                    "location" to "Carretera a El Salvador", "interest" to "Casa Mixco", "profession" to "Ingeniero en Sistemas",
                    "nationality" to "Salvadoreño", "dob" to "03 de Noviembre, 1985", "requirement" to "Casa 3 habs con jardín amplio",
                    "time" to "Ayer"
                ),
                // --- 8. DARREN SMITH (El de la notificación en inglés) ---
                mapOf(
                    "id" to "cli_8", "name" to "Darren Smith", "status" to "Nuevo",
                    "phone" to "+502 6789 0034", "email" to "darren.smith@email.com", "budget" to "Q. 2,500,000",
                    "location" to "Zona 10, Ciudad de Guatemala", "interest" to "Looking for a house in the city center", "profession" to "Digital Nomad",
                    "nationality" to "Estadounidense", "dob" to "04 de Julio, 1990", "requirement" to "I need a fast internet connection and a quiet workspace. Preferably close to cafes.",
                    "time" to "Hace 10 minutos"
                ),

                // --- 9. ALFONSO JARAMILLO ---
                mapOf(
                    "id" to "cli_9", "name" to "Alfonso Jaramillo", "status" to "Nuevo",
                    "phone" to "+502 3344 5566", "email" to "ajaramillo@empresa.com", "budget" to "Q. 5,000,000",
                    "location" to "Zona 14, Ciudad de Guatemala", "interest" to "Penthouse Cayalá", "profession" to "Inversionista",
                    "nationality" to "Colombiano", "dob" to "15 de Septiembre, 1980", "requirement" to "Alta seguridad y vistas a los volcanes.",
                    "time" to "Hoy"
                )
            )

            // --- 2. CINCO PROPIEDADES (Adaptadas a tus campos exactos) ---
            val propiedades = listOf(
                mapOf( // Esta es la cabaña de tu captura
                    "id" to "prop_1", "title" to "Cabaña en Tecpán", "location" to "Tecpan Guatemala, Chimaltenango",
                    "price" to 8000, "status" to "Disponible", "type" to "Cabaña", "interested" to 12
                ),
                mapOf(
                    "id" to "prop_2", "title" to "Apto Vista Hermosa", "location" to "Zona 15, Ciudad de Guatemala",
                    "price" to 1200000, "status" to "Pendiente", "type" to "Apartamento", "interested" to 5
                ),
                mapOf(
                    "id" to "prop_3", "title" to "Casa en Condominio", "location" to "Fraijanes",
                    "price" to 850000, "status" to "Cerrada", "type" to "Casa", "interested" to 2
                ),
                mapOf(
                    "id" to "prop_4", "title" to "Penthouse Cayalá", "location" to "Zona 16, Ciudad de Guatemala",
                    "price" to 3500000, "status" to "Visita", "type" to "Penthouse", "interested" to 8
                ),
                mapOf(
                    "id" to "prop_5", "title" to "Terreno Comercial", "location" to "Carretera a El Salvador",
                    "price" to 500000, "status" to "Disponible", "type" to "Terreno", "interested" to 15
                )
            )

            // SUBIR A FIRESTORE
            clientes.forEach { db.collection("clients").document(it["id"] as String).set(it) }
            propiedades.forEach { db.collection("properties").document(it["id"] as String).set(it) }
        }

        resetearBaseDeDatos() */

        // Mantenemos esto que tenías, hace que la app se vea moderna (sin barra negra arriba).
        enableEdgeToEdge()

        setContent {
            SyncraEstateAIMobileTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // AQUÍ EMPIEZA LA MAGIA DEL ROUTER (mapa de direcciones de la app).
                    val navController = rememberNavController()

                    // startDestination = "onboarding" le dice que arranque en la pantalla del edificio.
                    NavHost(navController = navController, startDestination = "onboarding") {

                        // Ruta 1: La pantalla del edificio (Welcome).
                        composable("onboarding") {
                            OnboardingScreen(navController = navController)
                        }

                        // Ruta 2: El carrusel de pestañas (nuestro próximo objetivo).
                        composable("onboarding_pager") {
                            OnboardingPagerScreen(navController = navController)
                        }

                        // Ruta 3: El Login.
                        composable("login") {
                            LoginScreen(navController = navController)
                        }

                        // Ruta 4: El Home Screen.
                        composable("home_screen") {
                            HomeScreen(navController = navController)
                        }

                        // Ruta 4.5.1: Pantalla para el perfil del agente.
                        composable("agent_profile") {
                            AgentProfileScreen(navController = navController)
                        }

                        // Ruta 4.5.2: Catálogo completo de propiedades.
                        composable("all_properties") {
                            AllPropertiesScreen(navController = navController)
                        }

                        // Ruta 5: Pantalla de Clientes (Management Pages).
                        composable("clients") {
                            ClientsScreen(navController = navController)
                        }

                        // Ruta 6: Pantalla del perfil del cliente (AHORA DINÁMICA).
                        composable(
                            route = "client_profile/{clientId}",
                            arguments = listOf(navArgument("clientId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val clientId = backStackEntry.arguments?.getString("clientId")
                            ClientProfileScreen(navController = navController, clientId = clientId ?: "")
                        }

                        // Ruta 7: Pantalla para detalles de propiedad (AHORA DINÁMICA).
                        composable(
                            route = "property_detail/{propertyId}",
                            arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            // Extraemos el ID que viene en la ruta
                            val propertyId = backStackEntry.arguments?.getString("propertyId")

                            // Si por alguna razón es nulo, le pasamos un string vacío para que no truene la app
                            PropertyDetailScreen(navController = navController, propertyId = propertyId ?: "")
                        }

                        // Ruta 8: Pantalla dedicada para la Agenda (Calendario moderno).
                        composable("agenda") {
                            AgendaScreen(navController = navController)
                        }

                        // Ruta 9: Pantalla dedicada para las estadísiticas del agente (Perfil del agente).
                        composable("statistics") {
                            StatisticsScreen(navController = navController)
                        }

                        // Ruta 10: Pantalla para la configuración de la IA del agente (Perfil del agente).
                        composable("ai_config") {
                            AIConfigScreen(navController = navController)
                        }

                        // Ruta 11: Pantalla para la Ayuda y Soporte del agente (Perfil del agente)
                        composable("support") {
                            SupportScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}