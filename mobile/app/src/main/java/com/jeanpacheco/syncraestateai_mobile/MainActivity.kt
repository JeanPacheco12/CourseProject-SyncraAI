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

            // --- 1. NUEVE CLIENTES CON FOTOS COHERENTES ---
            // --- 1. NUEVE CLIENTES ENLAZADOS A PROPIEDADES REALES ---
            val clientes = listOf(
                mapOf(
                    "id" to "cli_1", "name" to "Anderson Souza", "status" to "Firma hoy",
                    "phone" to "+502 4555 0101", "email" to "anderson@email.com", "budget" to "Q. 3,500,000",
                    "location" to "Zona 14, Ciudad de Guatemala", "interest" to "Apartamento", "profession" to "Ingeniero",
                    "nationality" to "Brasileño", "dob" to "12 de Mayo, 1990", "requirement" to "Apartamento moderno y seguro.",
                    "time" to "Hoy",
                    "profileImageUrl" to "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?q=80&w=500&auto=format&fit=crop",
                    "interestedPropertyId" to "prop_2" // Apto Vista Hermosa
                ),
                mapOf(
                    "id" to "cli_2", "name" to "Elena Martínez", "status" to "Firma hoy",
                    "phone" to "+502 5555 0102", "email" to "elena.m@email.com", "budget" to "Q. 1,200,000",
                    "location" to "Antigua Guatemala", "interest" to "Casa colonial", "profession" to "Diseñadora",
                    "nationality" to "Española", "dob" to "20 de Agosto, 1995", "requirement" to "Casa con acabados coloniales.",
                    "time" to "Ayer",
                    "profileImageUrl" to "https://images.unsplash.com/photo-1494790108377-be9c29b29330?q=80&w=500&auto=format&fit=crop",
                    "interestedPropertyId" to "prop_6" // Chalet El Paredón
                ),
                mapOf(
                    "id" to "cli_3", "name" to "Carlos Ruiz", "status" to "Visita hoy",
                    "phone" to "+502 3255 0103", "email" to "cruiz@email.com", "budget" to "Q. 800,000",
                    "location" to "Carretera a El Salvador", "interest" to "Terreno", "profession" to "Contador",
                    "nationality" to "Guatemalteco", "dob" to "05 de Enero, 1985", "requirement" to "Terreno para construir.",
                    "time" to "Hace 2 días",
                    "profileImageUrl" to "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?q=80&w=500&auto=format&fit=crop",
                    "interestedPropertyId" to "prop_5" // Terreno Comercial
                ),
                mapOf(
                    "id" to "cli_4", "name" to "Lucía Gómez", "status" to "Visita hoy",
                    "phone" to "+502 4455 0104", "email" to "lucia.g@email.com", "budget" to "Q. 2,500,000",
                    "location" to "Zona 15, Ciudad de Guatemala", "interest" to "Casa", "profession" to "Empresaria",
                    "nationality" to "Mexicana", "dob" to "14 de Marzo, 1982", "requirement" to "Casa con amplio jardín.",
                    "time" to "Hace 3 días",
                    "profileImageUrl" to "https://images.unsplash.com/photo-1487412720507-e7ab37603c6f?q=80&w=500&auto=format&fit=crop",
                    "interestedPropertyId" to "prop_7" // Casa en Zona 14
                ),
                mapOf(
                    "id" to "cli_5", "name" to "Roberto Chang", "status" to "Entrega",
                    "phone" to "+502 5555 0105", "email" to "r.chang@email.com", "budget" to "Q. 1,800,000",
                    "location" to "Cayalá", "interest" to "Apartamento", "profession" to "Chef",
                    "nationality" to "Chino", "dob" to "30 de Noviembre, 1989", "requirement" to "Cerca de zonas comerciales.",
                    "time" to "Hace 1 semana",
                    "profileImageUrl" to "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?q=80&w=500&auto=format&fit=crop",
                    "interestedPropertyId" to "prop_4" // Penthouse Cayalá
                ),
                mapOf(
                    "id" to "cli_6", "name" to "Marina Torres", "status" to "Entrega",
                    "phone" to "+502 3355 0106", "email" to "marina.t@email.com", "budget" to "Q. 950,000",
                    "location" to "Mixco", "interest" to "Casa de condominio", "profession" to "Profesora",
                    "nationality" to "Guatemalteca", "dob" to "18 de Julio, 1993", "requirement" to "Algo pequeño y seguro.",
                    "time" to "Hace 2 semanas",
                    "profileImageUrl" to "https://images.unsplash.com/photo-1554151228-14d9def656e4?q=80&w=500&auto=format&fit=crop",
                    "interestedPropertyId" to "prop_3" // Casa en Condominio
                ),
                mapOf(
                    "id" to "cli_7", "name" to "Fernando Ruiz", "status" to "Seguimiento",
                    "phone" to "+502 5555 9876", "email" to "fruiz.negocios@hotmail.com", "budget" to "Q. 950,000",
                    "location" to "Carretera a El Salvador", "interest" to "Casa Mixco", "profession" to "Ingeniero en Sistemas",
                    "nationality" to "Salvadoreño", "dob" to "03 de Noviembre, 1985", "requirement" to "Casa 3 habs con jardín amplio",
                    "time" to "Ayer",
                    "profileImageUrl" to "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?q=80&w=500&auto=format&fit=crop",
                    "interestedPropertyId" to "prop_8" // Casa Mixco
                ),
                mapOf(
                    "id" to "cli_8", "name" to "Darren Smith", "status" to "Nuevo",
                    "phone" to "+502 6789 0034", "email" to "darren.smith@email.com", "budget" to "Q. 2,500,000",
                    "location" to "Zona 10, Ciudad de Guatemala", "interest" to "Looking for a house in the city center", "profession" to "Digital Nomad",
                    "nationality" to "Estadounidense", "dob" to "04 de Julio, 1990", "requirement" to "Fast internet and quiet workspace.",
                    "time" to "Hace 10 minutos",
                    "profileImageUrl" to "https://images.unsplash.com/photo-1539571696357-5a69c17a67c6?q=80&w=500&auto=format&fit=crop",
                    "interestedPropertyId" to "prop_1" // Cabaña Tecpán
                ),
                mapOf(
                    "id" to "cli_9", "name" to "Alfonso Jaramillo", "status" to "Nuevo",
                    "phone" to "+502 3344 5566", "email" to "ajaramillo@empresa.com", "budget" to "Q. 5,000,000",
                    "location" to "Zona 14, Ciudad de Guatemala", "interest" to "Penthouse Cayalá", "profession" to "Inversionista",
                    "nationality" to "Colombiano", "dob" to "15 de Septiembre, 1980", "requirement" to "Alta seguridad y vistas a los volcanes.",
                    "time" to "Hoy",
                    "profileImageUrl" to "https://images.unsplash.com/photo-1500048993953-d23a436266cf?q=80&w=500&auto=format&fit=crop",
                    "interestedPropertyId" to "prop_4" // Penthouse Cayalá
                )
            )

            // --- 2. OCHO PROPIEDADES ACTUALIZADAS ---
            val propiedades = listOf(
                mapOf(
                    "id" to "prop_1", "title" to "Cabaña en Tecpán", "location" to "Tecpan Guatemala, Chimaltenango",
                    "price" to 8000L, "status" to "Disponibles", "type" to "Cabaña", "interested" to 12L,
                    "habitaciones" to 2L, "banos" to 1L, "parqueos" to 2L,
                    "descripcion" to "Hermosa cabaña rodeada de bosque, ideal para fines de semana.",
                    "metraje" to 85L, "nivel" to 1L, "mantenimiento" to 300L,
                    "amenidades" to listOf("Área de barbacoa (BBQ)", "Senderos ecológicos", "Seguridad 24/7", "Pet Friendly"),
                    "images" to listOf("https://images.unsplash.com/photo-1449844908441-8829872d2607?q=80&w=800"),
                    "imageGalleryUrlList" to listOf(
                        "https://images.unsplash.com/photo-1449844908441-8829872d2607?q=80&w=2070",
                        "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=80&w=2070",
                        "https://images.unsplash.com/photo-1504280390367-361c6d9f38f4?q=80&w=2070"
                    )
                ),
                mapOf(
                    "id" to "prop_2", "title" to "Apto Vista Hermosa", "location" to "Zona 15, Ciudad de Guatemala",
                    "price" to 1200000L, "status" to "Pendientes", "type" to "Apartamento", "interested" to 5L,
                    "habitaciones" to 3L, "banos" to 2L, "parqueos" to 2L,
                    "descripcion" to "Moderno apartamento con excelente iluminación y acabados de lujo.",
                    "metraje" to 120L, "nivel" to 4L, "mantenimiento" to 800L,
                    "amenidades" to listOf("Gimnasio equipado", "Piscina climatizada", "Seguridad 24/7", "Área de coworking"),
                    "images" to listOf("https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?q=80&w=800"),
                    "imageGalleryUrlList" to listOf(
                        "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?q=80&w=2070",
                        "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?q=80&w=2070",
                        "https://images.unsplash.com/photo-1484154218962-a197022b5858?q=80&w=2070"
                    )
                ),
                mapOf(
                    "id" to "prop_3", "title" to "Casa en Condominio", "location" to "Fraijanes",
                    "price" to 850000L, "status" to "Cerradas", "type" to "Casa", "interested" to 2L,
                    "habitaciones" to 4L, "banos" to 3L, "parqueos" to 3L,
                    "descripcion" to "Amplia propiedad familiar dentro de exclusivo condominio con doble garita.",
                    "metraje" to 250L, "nivel" to 2L, "mantenimiento" to 1200L,
                    "amenidades" to listOf("Casa club", "Canchas deportivas", "Juegos infantiles", "Seguridad 24/7"),
                    "images" to listOf("https://images.unsplash.com/photo-1512917774080-9991f1c4c750?q=80&w=800"),
                    "imageGalleryUrlList" to listOf(
                        "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?q=80&w=2070",
                        "https://images.unsplash.com/photo-1605276374104-dee2a0ed3cd6?q=80&w=2070",
                        "https://images.unsplash.com/photo-1513584684374-8bdb7489feef?q=80&w=2070"
                    )
                ),
                mapOf(
                    "id" to "prop_4", "title" to "Penthouse Cayalá", "location" to "Zona 16, Ciudad de Guatemala",
                    "price" to 3500000L, "status" to "Visitas", "type" to "Penthouse", "interested" to 8L,
                    "habitaciones" to 3L, "banos" to 3L, "parqueos" to 3L,
                    "descripcion" to "Lujo y exclusividad con vistas panorámicas a toda la ciudad y amenidades premium.",
                    "metraje" to 310L, "nivel" to 8L, "mantenimiento" to 2500L,
                    "amenidades" to listOf("Piscina privada", "Helipuerto", "Gimnasio equipado", "Lounge bar"),
                    "images" to listOf("https://images.unsplash.com/photo-1600607687920-4e2a09cf159d?q=80&w=800"),
                    "imageGalleryUrlList" to listOf(
                        "https://images.unsplash.com/photo-1600607687920-4e2a09cf159d?q=80&w=2070",
                        "https://images.unsplash.com/photo-1600566752355-35792bedcfea?q=80&w=2070",
                        "https://images.unsplash.com/photo-1600121848594-d8644e57abab?q=80&w=2070"
                    )
                ),
                mapOf(
                    "id" to "prop_5", "title" to "Terreno Comercial", "location" to "Carretera a El Salvador",
                    "price" to 500000L, "status" to "Disponibles", "type" to "Terreno", "interested" to 15L,
                    "habitaciones" to 0L, "banos" to 0L, "parqueos" to 10L,
                    "descripcion" to "Terreno totalmente plano y urbanizado, ideal para plaza comercial.",
                    "metraje" to 1500L, "nivel" to 0L, "mantenimiento" to 0L,
                    "amenidades" to listOf("Acceso asfaltado", "Pozo propio", "Energía trifásica"),
                    "images" to listOf("https://images.unsplash.com/photo-1500382017468-9049fed747ef?q=80&w=800"),
                    "imageGalleryUrlList" to listOf(
                        "https://images.unsplash.com/photo-1500382017468-9049fed747ef?q=80&w=2032",
                        "https://images.unsplash.com/photo-1500076656116-558758c991c1?q=80&w=2071",
                        "https://images.unsplash.com/photo-1524813686514-a57563d77200?q=80&w=2070"
                    )
                ),
                mapOf(
                    "id" to "prop_6", "title" to "Chalet en El Paredón", "location" to "Sipacate, Escuintla",
                    "price" to 8000L, "status" to "Pendientes", "type" to "Chalet", "interested" to 18L,
                    "habitaciones" to 3L, "banos" to 2L, "parqueos" to 3L,
                    "descripcion" to "Espectacular chalet moderno cerca del mar, ideal para descansar o rentar en Airbnb.",
                    "metraje" to 180L, "nivel" to 2L, "mantenimiento" to 500L,
                    "amenidades" to listOf("Frente al mar", "Piscina privada", "Hamacas", "Aire acondicionado"),
                    "images" to listOf("https://images.unsplash.com/photo-1499793983690-e29da59ef1c2?q=80&w=800"),
                    "imageGalleryUrlList" to listOf(
                        "https://images.unsplash.com/photo-1499793983690-e29da59ef1c2?q=80&w=2070",
                        "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?q=80&w=2070",
                        "https://images.unsplash.com/photo-1590311825124-73ec52331a44?q=80&w=2070"
                    )
                ),
                mapOf(
                    "id" to "prop_7", "title" to "Casa en Zona 14", "location" to "Zona 14, Ciudad de Guatemala",
                    "price" to 250000L, "status" to "Pendientes", "type" to "Casa", "interested" to 8L,
                    "habitaciones" to 4L, "banos" to 3L, "parqueos" to 2L,
                    "descripcion" to "Amplia casa en sector exclusivo, finos acabados y jardín trasero ideal para mascotas.",
                    "metraje" to 350L, "nivel" to 2L, "mantenimiento" to 1500L,
                    "amenidades" to listOf("Jardín amplio", "Estudio", "Seguridad 24/7", "Parqueo de visitas"),
                    "images" to listOf("https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?q=80&w=800"),
                    "imageGalleryUrlList" to listOf(
                        "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?q=80&w=2075",
                        "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?q=80&w=2070",
                        "https://images.unsplash.com/photo-1600607687644-c7171b42498f?q=80&w=2070"
                    )
                ),
                mapOf(
                    "id" to "prop_8", "title" to "Casa Mixco", "location" to "Zona 9 de Mixco",
                    "price" to 40000L, "status" to "Visitas", "type" to "Casa", "interested" to 14L,
                    "habitaciones" to 3L, "banos" to 2L, "parqueos" to 2L,
                    "descripcion" to "Cómoda casa dentro de garita de seguridad, ambiente familiar y tranquilo.",
                    "metraje" to 140L, "nivel" to 2L, "mantenimiento" to 400L,
                    "amenidades" to listOf("Canchas deportivas", "Salón de usos múltiples", "Seguridad 24/7"),
                    "images" to listOf("https://images.unsplash.com/photo-1583608205776-bfd35f0d9f83?q=80&w=800"),
                    "imageGalleryUrlList" to listOf(
                        "https://images.unsplash.com/photo-1583608205776-bfd35f0d9f83?q=80&w=2070",
                        "https://images.unsplash.com/photo-1570129477492-45c003edd2be?q=80&w=2070",
                        "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?q=80&w=2070"
                    )
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

                        // Ruta 12: Pantalla de Edición de Propiedad
                        composable(
                            route = "edit_property/{propertyId}",
                            arguments = listOf(navArgument("propertyId") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val propertyId = backStackEntry.arguments?.getString("propertyId") ?: ""
                            EditPropertyScreen(navController = navController, propertyId = propertyId)
                        }

                        // Ruta 13: Pantalla para la configuración local del agente (Perfil del agente)
                        composable("settings") {
                            SettingsScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}