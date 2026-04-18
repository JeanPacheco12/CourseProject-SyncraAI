package com.jeanpacheco.syncraestateai_mobile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
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
fun AIConfigScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // 1. Instanciamos nuestra clase de preferencias
    val aiPrefs = remember { AIPrefs(context) }

    // 2. Inicializamos las variables LEYENDO la memoria del teléfono (SharedPreferences)
    var selectedTone by remember { mutableStateOf(aiPrefs.getTone()) }
    var selectedLength by remember { mutableStateOf(aiPrefs.getLength()) }
    var autoGenerate by remember { mutableStateOf(aiPrefs.getAutoGenerate()) }
    var translateAuto by remember { mutableStateOf(aiPrefs.getTranslate()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración de IA", fontWeight = FontWeight.Bold, color = SyncraPrimary, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = SyncraPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Personaliza cómo interactúa la IA con tus prospectos.", color = Color.Gray, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(24.dp))

            // 1. SECCIÓN DE TONO DE VOZ
            Text(text = "Tono de Comunicación", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                SelectableOption(modifier = Modifier.weight(1f), text = "Amigable", icon = Icons.Default.Face, isSelected = selectedTone == "Amigable") { selectedTone = "Amigable" }
                SelectableOption(modifier = Modifier.weight(1f), text = "Formal", icon = Icons.Default.AccountBox, isSelected = selectedTone == "Formal") { selectedTone = "Formal" }
                SelectableOption(modifier = Modifier.weight(1f), text = "Persuasivo", icon = Icons.Default.Star, isSelected = selectedTone == "Persuasivo") { selectedTone = "Persuasivo" }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 2. SECCIÓN DE LONGITUD
            Text(text = "Longitud del Smart Pitch", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
            Spacer(modifier = Modifier.height(12.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                RadioOptionRow(
                    text = "Corto (Directo y conciso)",
                    isSelected = selectedLength == "Corto"
                ) { selectedLength = "Corto" }

                RadioOptionRow(
                    text = "Detallado (Con info de la propiedad)",
                    isSelected = selectedLength == "Detallado"
                ) { selectedLength = "Detallado" }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 3. SECCIÓN DE AUTOMATIZACIÓN (Switches)
            Text(text = "Automatización", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
            Spacer(modifier = Modifier.height(12.dp))

            SwitchRow(
                title = "Auto-generar sugerencias",
                desc = "Generar Smart Pitch automáticamente al abrir el perfil de un prospecto.",
                checked = autoGenerate,
                onCheckedChange = { autoGenerate = it }
            )

            Spacer(modifier = Modifier.height(12.dp))

            SwitchRow(
                title = "Traducción simultánea",
                desc = "Detectar si el cliente habla otro idioma y adaptar el mensaje sugerido.",
                checked = translateAuto,
                onCheckedChange = { translateAuto = it }
            )

            Spacer(modifier = Modifier.height(40.dp))

            // 3. BOTÓN GUARDAR MODIFICADO
            Button(
                onClick = {
                    // GUARDAMOS LAS PREFERENCIAS EN LA MEMORIA ANTES DE CERRAR LA PANTALLA
                    aiPrefs.saveSettings(
                        tone = selectedTone,
                        length = selectedLength,
                        autoGenerate = autoGenerate,
                        translate = translateAuto
                    )

                    Toast.makeText(context, "Configuración guardada exitosamente", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SyncraPrimary)
            ) {
                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Guardar Preferencias", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// Componentes Auxiliares para que el código quede limpio

@Composable
fun SelectableOption(modifier: Modifier = Modifier, text: String, icon: ImageVector, isSelected: Boolean, onClick: () -> Unit) {
    val bgColor = if (isSelected) SyncraPrimary else SurfaceGray
    val contentColor = if (isSelected) Color.White else SyncraPrimary

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(bgColor)
            .clickable { onClick() }
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = null, tint = contentColor, modifier = Modifier.size(28.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, color = contentColor, fontSize = 12.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun RadioOptionRow(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, if (isSelected) SyncraPrimary else Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = SyncraPrimary)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp, color = SyncraPrimary, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun SwitchRow(title: String, desc: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceGray)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold, color = SyncraPrimary, fontSize = 14.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = desc, color = Color.Gray, fontSize = 12.sp, lineHeight = 16.sp)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Color(0xFF8DB049))
        )
    }
}