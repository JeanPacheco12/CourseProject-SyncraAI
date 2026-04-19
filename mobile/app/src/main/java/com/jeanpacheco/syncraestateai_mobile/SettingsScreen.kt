package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    // Variables de estado (Switches de mentira para la demostración)
    val context = LocalContext.current
    val appPrefs = remember { AppPrefs(context) } // Instanciamos la memoria

    // Leemos el valor real guardado, ya no es de "mentira"
    var isDarkMode by remember { mutableStateOf(appPrefs.isDarkMode()) }
    var notifyReminders by remember { mutableStateOf(true) }
    var notifyMarketing by remember { mutableStateOf(false) }
    var biometricsEnabled by remember { mutableStateOf(true) }

    // Colores basados en tu UI actual
    val syncraPrimary = Color(0xFF234F68)
    val surfaceGray = Color(0xFFF4F6F9)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Configuración", fontWeight = FontWeight.Bold, color = syncraPrimary, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = syncraPrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. SECCIÓN: APARIENCIA
            Text(text = "Apariencia", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = syncraPrimary)
            Spacer(modifier = Modifier.height(12.dp))
            SettingsSwitchRow(
                icon = Icons.Default.Star, // O el que hayas dejado para la lunita
                title = "Modo Oscuro",
                subtitle = "Cambiar el tema visual de la app",
                isChecked = isDarkMode,
                onCheckedChange = { nuevoEstado ->
                    isDarkMode = nuevoEstado // Cambia el switch en la pantalla
                    appPrefs.setDarkMode(nuevoEstado) // Lo guarda en la memoria del teléfono
                },
                bgColor = surfaceGray,
                iconColor = syncraPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 2. SECCIÓN: NOTIFICACIONES (Enfocadas a un CRM)
            Text(text = "Notificaciones", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = syncraPrimary)
            Spacer(modifier = Modifier.height(12.dp))
            SettingsSwitchRow(
                icon = Icons.Default.DateRange,
                title = "Recordatorios de Citas",
                subtitle = "Avisos de visitas y firmas de contratos",
                isChecked = notifyReminders,
                onCheckedChange = { notifyReminders = it },
                bgColor = surfaceGray,
                iconColor = syncraPrimary
            )
            Spacer(modifier = Modifier.height(8.dp))
            SettingsSwitchRow(
                icon = Icons.Default.MailOutline,
                title = "Resumen Semanal",
                subtitle = "Recibir estadísticas de rendimiento por correo",
                isChecked = notifyMarketing,
                onCheckedChange = { notifyMarketing = it },
                bgColor = surfaceGray,
                iconColor = syncraPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 3. SECCIÓN: SEGURIDAD
            Text(text = "Seguridad y Acceso", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = syncraPrimary)
            Spacer(modifier = Modifier.height(12.dp))
            SettingsSwitchRow(
                icon = Icons.Default.Fingerprint,
                title = "Acceso Biométrico",
                subtitle = "Usar huella o Face ID para entrar",
                isChecked = biometricsEnabled,
                onCheckedChange = { biometricsEnabled = it },
                bgColor = surfaceGray,
                iconColor = syncraPrimary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 4. SECCIÓN: ACERCA DE
            Text(text = "Acerca de Syncra", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = syncraPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            SettingsClickableRow(
                icon = Icons.Default.Build,
                title = "Versión de la App",
                subtitle = "v1.0.0 (Build 5)",
                showArrow = false, // No muestra flecha porque no es clickeable
                onClick = { /* Nada */ },
                bgColor = surfaceGray,
                iconColor = syncraPrimary
            )

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// --- COMPONENTES REUTILIZABLES PARA MANTENER EL CÓDIGO LIMPIO ---

@Composable
fun SettingsSwitchRow(
    icon: ImageVector, title: String, subtitle: String,
    isChecked: Boolean, onCheckedChange: (Boolean) -> Unit,
    bgColor: Color, iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = iconColor)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = subtitle, fontSize = 12.sp, color = Color.Gray, lineHeight = 16.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = Color(0xFF8BC83F) // Tu verde Syncra
            )
        )
    }
}

@Composable
fun SettingsClickableRow(
    icon: ImageVector, title: String, subtitle: String? = null,
    showArrow: Boolean = true, onClick: () -> Unit,
    bgColor: Color, iconColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable(enabled = showArrow) { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = iconColor)
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subtitle, fontSize = 12.sp, color = Color.Gray)
            }
        }
        if (showArrow) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Ir", tint = Color.Gray)
        }
    }
}