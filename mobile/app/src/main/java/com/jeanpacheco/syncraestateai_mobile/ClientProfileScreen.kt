package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.draw.blur

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientProfileScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    // ESTADO PARA CONTROLAR EL BOTTOM SHEET (VENTANA EMERGENTE)
    var showPitchSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Perfil", fontWeight = FontWeight.Bold, color = SyncraPrimary, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = SyncraPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = { /* Acción de compartir */ }) {
                        Icon(Icons.Default.Share, contentDescription = "Compartir", tint = SyncraPrimary)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Foto de perfil
            Image(
                painter = painterResource(id = R.drawable.img_prospecto_1),
                contentDescription = "Foto de perfil",
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Nombre y datos
            Text(text = "Valeria Ramos", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "valeriaramos@gmail.com", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "+502 4589-9810", fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Bloques de Información
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                InfoBlock(modifier = Modifier.weight(1f), label = "Presupuesto", value = "$120k-$150k")
                InfoBlock(modifier = Modifier.weight(1f), label = "Ubicación de interés", value = "Zona 15")
                InfoBlock(modifier = Modifier.weight(1f), label = "Interés", value = "Apto.")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Botones de Acción
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /* Llamar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                    shape = RoundedCornerShape(24.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.wsp_logo_1),
                        contentDescription = "Llamar",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Llamar", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(ColorVisitaHoy)
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Visita hoy", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))

            // 5. Datos de perfil
            Text(text = "Datos de perfil", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            ProfileDataField(label = "Profesión / Ocupación", value = "Doctora")
            Spacer(modifier = Modifier.height(12.dp))
            ProfileDataField(label = "Nacionalidad", value = "Guatemalteca")
            Spacer(modifier = Modifier.height(12.dp))
            ProfileDataField(label = "Fecha de nacimiento", value = "15 junio 1990")

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
            Spacer(modifier = Modifier.height(24.dp))

            // 6. Propiedad de Interés
            Text(text = "Propiedad de interés", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
            PropertyOfInterestCard(navController = navController)

            Spacer(modifier = Modifier.height(32.dp))

            // 7. Botón Mágico: Generar Smart Pitch con IA
            Button(
                onClick = { showPitchSheet = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(text = "✨ Generar Smart Pitch con IA", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(modifier = Modifier.height(40.dp))
        }

        // ==========================================
        // BOTTOM SHEET DEL SMART PITCH
        // ==========================================
        if (showPitchSheet) {
            ModalBottomSheet(
                onDismissRequest = { showPitchSheet = false },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                SmartPitchSheetContent()
            }
        }
    }
}

// ==========================================
// SUB-COMPONENTES Y BOTTOM SHEET CONTENT
// ==========================================

@Composable
fun SmartPitchSheetContent() {
    // Estado para el texto editable
    var pitchText by remember {
        mutableStateOf("Hola Valeria 👋, he analizado tus preferencias y vi que buscas en Zona 15. Creo que el Apartamento Vista Hermosa es ideal para ti. Por tu perfil como doctora, sus vías de acceso rápido a los hospitales principales te encantarán. ¿Te parece si agendamos una visita hoy mismo para que lo conozcas?")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally // Centra todo el contenido
    ) {

        // 1. Logo de Gemini con Resplandor (Glow) Neón MEJORADO
        val geminiGradient = Brush.linearGradient(
            colors = listOf(Color(0xFF4285F4), Color(0xFF9B72CB), Color(0xFFD96570))
        )

        // Este es el gradiente radial para el resplandor de fondo
        val glowGradient = Brush.radialGradient(
            colors = listOf(
                Color(0xFF9B72CB).copy(alpha = 0.8f), // Centro con color morado/rosado al 50%
                Color.Transparent // Se difumina hacia transparente en los bordes
            )
        )

        Box(
            modifier = Modifier
                .size(90.dp) // Espacio total para el resplandor
                .background(glowGradient, CircleShape), // El fondo difuminado
            contentAlignment = Alignment.Center
        ) {
            // El círculo blanco con el logo y su borde lineal
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, geminiGradient, CircleShape)
                    .padding(12.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_gemini),
                    contentDescription = "Gemini AI",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Título Bicolor
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = SyncraPrimary)) {
                    append("Smart Pitch ")
                }
                withStyle(style = SpanStyle(color = Color.Black)) {
                    append("listo ✨")
                }
            },
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 3. Área de Texto Editable (Más oscura)
        val darkGrayBackground = Color(0xFFE8ECEF) // Un gris más oscuro para destacar

        OutlinedTextField(
            value = pitchText,
            onValueChange = { pitchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 160.dp), // Altura mínima para que parezca un bloque de notas
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = darkGrayBackground,
                unfocusedContainerColor = darkGrayBackground,
                focusedBorderColor = Color.Transparent, // Sin borde, solo el fondo
                unfocusedBorderColor = Color.Transparent,
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 15.sp, color = Color.DarkGray, lineHeight = 22.sp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // 4. Botones de acción (Regenerar y WhatsApp)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón Regenerar (Bucle)
            IconButton(
                onClick = { /* Acción regenerar mensaje */ },
                modifier = Modifier
                    .size(54.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(darkGrayBackground)
            ) {
                Icon(Icons.Default.Refresh, contentDescription = "Regenerar", tint = SyncraPrimary)
            }

            // Botón Enviar wsp
            Button(
                onClick = { /* Acción enviar a wsp */ },
                modifier = Modifier
                    .weight(1f)
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.wsp_logo_1),
                    contentDescription = "WhatsApp",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Enviar por WhatsApp", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

@Composable
fun InfoBlock(modifier: Modifier = Modifier, label: String, value: String) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceGray)
            .padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, fontSize = 11.sp, color = Color.Gray, maxLines = 2, textAlign = TextAlign.Center, lineHeight = 12.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary, maxLines = 1, textAlign = TextAlign.Center)
    }
}

@Composable
fun ProfileDataField(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceGray)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = label, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontSize = 15.sp, color = Color.Black, fontWeight = FontWeight.Medium)
    }
}

// Asegúrate de pasar el navController como parámetro
@Composable
fun PropertyOfInterestCard(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray),
        // ¡ESTO ES LO NUEVO! Le decimos que navegue al hacer clic
        onClick = {
            navController.navigate("detalle_propiedad")
        }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.propiedad_agenda_1),
                contentDescription = "Propiedad",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Apartamento Vista Hermosa", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Zona 15, Guatemala", fontSize = 13.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "$135,000", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ColorVisitaHoy)
            }

            Box(
                modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(ColorVisitaHoy)
            )
        }
    }
}