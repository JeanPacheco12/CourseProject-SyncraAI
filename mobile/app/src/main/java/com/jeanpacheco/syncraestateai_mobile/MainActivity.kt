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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

                        // Ruta 6: Pantalla del perfil del cliente.
                        composable("client_profile") {
                            ClientProfileScreen(navController = navController)
                        }

                        // Ruta 7: Pantalla para detalles de propiedad.
                        composable("detalle_propiedad") {
                            PropertyDetailScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}