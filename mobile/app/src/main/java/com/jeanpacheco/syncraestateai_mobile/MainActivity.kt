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
                    "id" to "cli_1", "name" to "Anderson Souza", "status" to "Firma hoy",
                    "phone" to "+502 4555 0101", "email" to "anderson@email.com", "budget" to "Q. 3,500,000",
                    "location" to "Zona 14, Ciudad de Guatemala", "interest" to "Apartamento", "profession" to "Ingeniero",
                    "nationality" to "Brasileño", "dob" to "12 de Mayo, 1990", "requirement" to "Apartamento moderno y seguro.",
                    "time" to "Hoy"
                ),
                mapOf(
                    "id" to "cli_2", "name" to "Elena Martínez", "status" to "Firma hoy",
                    "phone" to "+502 5555 0102", "email" to "elena.m@email.com", "budget" to "Q. 1,200,000",
                    "location" to "Antigua Guatemala", "interest" to "Casa colonial", "profession" to "Diseñadora",
                    "nationality" to "Española", "dob" to "20 de Agosto, 1995", "requirement" to "Casa con acabados coloniales.",
                    "time" to "Ayer"
                ),
                mapOf(
                    "id" to "cli_3", "name" to "Carlos Ruiz", "status" to "Visita hoy",
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
                    "id" to "cli_5", "name" to "Roberto Chang", "status" to "Entrega",
                    "phone" to "+502 5555 0105", "email" to "r.chang@email.com", "budget" to "Q. 1,800,000",
                    "location" to "Cayalá", "interest" to "Apartamento", "profession" to "Chef",
                    "nationality" to "Chino", "dob" to "30 de Noviembre, 1989", "requirement" to "Cerca de zonas comerciales.",
                    "time" to "Hace 1 semana"
                ),
                mapOf(
                    "id" to "cli_6", "name" to "Marina Torres", "status" to "Entrega",
                    "phone" to "+502 3355 0106", "email" to "marina.t@email.com", "budget" to "Q. 950,000",
                    "location" to "Mixco", "interest" to "Casa de condominio", "profession" to "Profesora",
                    "nationality" to "Guatemalteca", "dob" to "18 de Julio, 1993", "requirement" to "Algo pequeño y seguro.",
                    "time" to "Hace 2 semanas"
                ),
                mapOf( // Este es el que me pasaste en la captura
                    "id" to "cli_7", "name" to "Fernando Ruiz", "status" to "Seguimiento",
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

            // --- LISTA COMPLETA DE 8 PROPIEDADES ACTUALIZADA CON GALERÍA ---
val propiedades = listOf(
    // 1. Cabaña en Tecpán
    mapOf(
        "id" to "prop_1", "title" to "Cabaña en Tecpán", "location" to "Tecpan Guatemala, Chimaltenango",
        "price" to 8000, "status" to "Disponible", "type" to "Cabaña", "interested" to 12,
        "habitaciones" to 2, "banos" to 1, "parqueos" to 2,
        "descripcion" to "Hermosa cabaña rodeada de bosque, ideal para fines de semana.",
        "imageGalleryUrlList" to listOf(
            "https://images.unsplash.com/photo-1449844908441-8829872d2607?q=80&w=2070",
            "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=80&w=2070",
            "https://images.unsplash.com/photo-1504280390367-361c6d9f38f4?q=80&w=2070"
        ),
        "metraje" to 85, "nivel" to 1, "mantenimiento" to 300,
        "amenidades" to listOf("Área de barbacoa (BBQ)", "Senderos ecológicos", "Seguridad 24/7", "Pet Friendly")
    ),
    // 2. Apto Vista Hermosa
    mapOf(
        "id" to "prop_2", "title" to "Apto Vista Hermosa", "location" to "Zona 15, Ciudad de Guatemala",
        "price" to 1200000, "status" to "Pendiente", "type" to "Apartamento", "interested" to 5,
        "habitaciones" to 3, "banos" to 2, "parqueos" to 2,
        "descripcion" to "Moderno apartamento con excelente iluminación y acabados de lujo.",
        "imageGalleryUrlList" to listOf(
            "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?q=80&w=2070",
            "https://images.unsplash.com/photo-1502672260266-1c1ef2d93688?q=80&w=2070",
            "https://images.unsplash.com/photo-1484154218962-a197022b5858?q=80&w=2070"
        ),
        "metraje" to 120, "nivel" to 4, "mantenimiento" to 800,
        "amenidades" to listOf("Gimnasio equipado", "Piscina climatizada", "Seguridad 24/7", "Área de coworking")
    ),
    // 3. Casa en Condominio
    mapOf(
        "id" to "prop_3", "title" to "Casa en Condominio", "location" to "Fraijanes",
        "price" to 850000, "status" to "Cerrada", "type" to "Casa", "interested" to 2,
        "habitaciones" to 4, "banos" to 3, "parqueos" to 3,
        "descripcion" to "Amplia propiedad familiar dentro de exclusivo condominio con doble garita.",
        "imageGalleryUrlList" to listOf(
            "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?q=80&w=2070",
            "https://images.unsplash.com/photo-1605276374104-dee2a0ed3cd6?q=80&w=2070",
            "https://images.unsplash.com/photo-1513584684374-8bdb7489feef?q=80&w=2070"
        ),
        "metraje" to 250, "nivel" to 2, "mantenimiento" to 1200,
        "amenidades" to listOf("Casa club", "Canchas deportivas", "Juegos infantiles", "Seguridad 24/7")
    ),
    // 4. Penthouse Cayalá
    mapOf(
        "id" to "prop_4", "title" to "Penthouse Cayalá", "location" to "Zona 16, Ciudad de Guatemala",
        "price" to 3500000, "status" to "Visita", "type" to "Penthouse", "interested" to 8,
        "habitaciones" to 3, "banos" to 3, "parqueos" to 3,
        "descripcion" to "Lujo y exclusividad con vistas panorámicas a toda la ciudad y amenidades premium.",
        "imageGalleryUrlList" to listOf(
            "https://images.unsplash.com/photo-1600607687920-4e2a09cf159d?q=80&w=2070",
            "https://images.unsplash.com/photo-1600566752355-35792bedcfea?q=80&w=2070",
            "https://images.unsplash.com/photo-1600121848594-d8644e57abab?q=80&w=2070"
        ),
        "metraje" to 310, "nivel" to 8, "mantenimiento" to 2500,
        "amenidades" to listOf("Piscina privada", "Helipuerto", "Gimnasio equipado", "Lounge bar")
    ),
    // 5. Terreno Comercial
    mapOf(
        "id" to "prop_5", "title" to "Terreno Comercial", "location" to "Carretera a El Salvador",
        "price" to 500000, "status" to "Disponible", "type" to "Terreno", "interested" to 15,
        "habitaciones" to 0, "banos" to 0, "parqueos" to 10,
        "descripcion" to "Terreno totalmente plano y urbanizado, ideal para plaza comercial.",
        "imageGalleryUrlList" to listOf(
            "https://images.unsplash.com/photo-1500382017468-9049fed747ef?q=80&w=2032",
            "https://images.unsplash.com/photo-1500076656116-558758c991c1?q=80&w=2071"
        ),
        "metraje" to 1500, "nivel" to 0, "mantenimiento" to 0,
        "amenidades" to listOf("Acceso asfaltado", "Pozo propio", "Energía trifásica")
    ),
    // 6. El Chalet (ESTE ES EL DEL VIDEO)
    mapOf(
        "id" to "prop_6", "title" to "Chalet en El Paredón", "location" to "Sipacate, Escuintla",
        "price" to 8000, "status" to "Pendientes", "type" to "Chalet", "interested" to 18,
        "habitaciones" to 3, "banos" to 2, "parqueos" to 3,
        "descripcion" to "Espectacular chalet moderno cerca del mar, ideal para descansar o rentar en Airbnb.",
        "imageGalleryUrlList" to listOf(
            "https://images.unsplash.com/photo-1499793983690-e29da59ef1c2?q=80&w=2070", // Fachada
            "https://images.unsplash.com/photo-1520250497591-112f2f40a3f4?q=80&w=2070", // Piscina/Exterior
            "https://images.unsplash.com/photo-1590311825124-73ec52331a44?q=80&w=2070", // Interior/Sala
            "https://images.unsplash.com/photo-1540518614846-7eded433c457?q=80&w=2070"  // Habitación
        ),
        "metraje" to 180, "nivel" to 2, "mantenimiento" to 500,
        "amenidades" to listOf("Frente al mar", "Piscina privada", "Hamacas", "Aire acondicionado")
    ),
    // 7. Casa en Zona 14
    mapOf(
        "id" to "prop_7", "title" to "Casa en Zona 14", "location" to "Zona 14, Ciudad de Guatemala",
        "price" to 250000, "status" to "Pendientes", "type" to "Casa", "interested" to 8,
        "habitaciones" to 4, "banos" to 3, "parqueos" to 2,
        "descripcion" to "Amplia casa en sector exclusivo, finos acabados y jardín trasero ideal para mascotas.",
        "imageGalleryUrlList" to listOf(
            "https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?q=80&w=2075",
            "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?q=80&w=2070",
            "https://images.unsplash.com/photo-1600607687644-c7171b42498f?q=80&w=2070"
        ),
        "metraje" to 350, "nivel" to 2, "mantenimiento" to 1500,
        "amenidades" to listOf("Jardín amplio", "Estudio", "Seguridad 24/7", "Parqueo de visitas")
    ),
    // 8. Casa Mixco
    mapOf(
        "id" to "prop_8", "title" to "Casa Mixco", "location" to "Zona 9 de Mixco",
        "price" to 40000, "status" to "Visitas", "type" to "Casa", "interested" to 14,
        "habitaciones" to 3, "banos" to 2, "parqueos" to 2,
        "descripcion" to "Cómoda casa dentro de garita de seguridad, ambiente familiar y tranquilo.",
        "imageGalleryUrlList" to listOf(
            "https://images.unsplash.com/photo-1583608205776-bfd35f0d9f83?q=80&w=2070",
            "https://images.unsplash.com/photo-1570129477492-45c003edd2be?q=80&w=2070",
            "https://images.unsplash.com/photo-1564013799919-ab600027ffc6?q=80&w=2070"
        ),
        "metraje" to 140, "nivel" to 2, "mantenimiento" to 400,
        "amenidades" to listOf("Canchas deportivas", "Salón de usos múltiples", "Seguridad 24/7")
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
                    }
                }
            }
        }
    }
}