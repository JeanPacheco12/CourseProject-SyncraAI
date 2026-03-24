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
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientProfileScreen(navController: NavController, clientId: String) { // <-- Recibe el ID
    val scrollState = rememberScrollState()

    var showPitchSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // --- ESTADOS DINÁMICOS DEL CLIENTE ---
    var clientName by remember { mutableStateOf("Cargando...") }
    var clientEmail by remember { mutableStateOf("") }
    var clientPhone by remember { mutableStateOf("") }
    var clientBudget by remember { mutableStateOf("") }
    var clientLocation by remember { mutableStateOf("") }
    var clientInterest by remember { mutableStateOf("") }
    var clientProfession by remember { mutableStateOf("") }
    var clientNationality by remember { mutableStateOf("") }
    var clientDob by remember { mutableStateOf("") }
    var clientStatus by remember { mutableStateOf("Nuevo") }
    var isLoading by remember { mutableStateOf(true) }
    var clientRequirement by remember { mutableStateOf("") } // <-- ¡AGREGA ESTA LÍNEA!

    // --- DESCARGAR DATOS DESDE FIREBASE ---
    LaunchedEffect(clientId) {
        if (clientId.isNotEmpty()) {
            val db = FirebaseFirestore.getInstance()
            db.collection("clients").document(clientId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        clientName = document.getString("name") ?: "Sin nombre"
                        clientEmail = document.getString("email") ?: "Sin correo"
                        clientPhone = document.getString("phone") ?: "Sin teléfono"
                        clientBudget = document.getString("budget") ?: "N/A"
                        clientLocation = document.getString("location") ?: "N/A"
                        clientInterest = document.getString("interest") ?: "N/A"
                        clientProfession = document.getString("profession") ?: "N/A"
                        clientNationality = document.getString("nationality") ?: "N/A"
                        clientDob = document.getString("dob") ?: "N/A"
                        clientStatus = document.getString("status") ?: "Nuevo"
                        clientRequirement = document.getString("requirement") ?: "N/A" // <-- ¡AGREGA ESTA LÍNEA TAMBIÉN!
                    }
                    isLoading = false
                }
                .addOnFailureListener { isLoading = false }
        } else {
            isLoading = false
        }
    }

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
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = SyncraPrimary)
            }
        } else {
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

                Image(
                    painter = painterResource(id = R.drawable.img_prospecto_1), // Podremos hacerlo dinámico luego
                    contentDescription = "Foto de perfil",
                    modifier = Modifier.size(110.dp).clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- TEXTOS DINÁMICOS ---
                Text(text = clientName, fontSize = 24.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = clientEmail, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = clientPhone, fontSize = 14.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoBlock(modifier = Modifier.weight(1f), label = "Presupuesto", value = clientBudget)
                    InfoBlock(modifier = Modifier.weight(1f), label = "Ubicación", value = clientLocation)
                    InfoBlock(modifier = Modifier.weight(1f), label = "Interés", value = clientInterest)
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = { /* Llamar */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366)),
                        shape = RoundedCornerShape(24.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                    ) {
                        Icon(painter = painterResource(id = R.drawable.wsp_logo_1), contentDescription = "Llamar", tint = Color.White, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Llamar", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier.clip(RoundedCornerShape(24.dp)).background(ColorVisitaHoy).padding(horizontal = 20.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = clientStatus, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Datos de perfil", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))
                ProfileDataField(label = "Profesión / Ocupación", value = clientProfession)
                Spacer(modifier = Modifier.height(12.dp))
                ProfileDataField(label = "Nacionalidad", value = clientNationality)
                Spacer(modifier = Modifier.height(12.dp))
                ProfileDataField(label = "Fecha de nacimiento", value = clientDob)

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f), thickness = 1.dp)
                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Propiedad de interés", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(16.dp))

                // 1. AHORA LE PASAMOS LA INFO DEL CLIENTE A LA TARJETA
                PropertyOfInterestCard(
                    navController = navController,
                    interest = clientInterest,
                    location = clientLocation,
                    budget = clientBudget
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { showPitchSheet = true },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "✨ Generar Smart Pitch con IA", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
                Spacer(modifier = Modifier.height(40.dp))
            }

            if (showPitchSheet) {
                ModalBottomSheet(onDismissRequest = { showPitchSheet = false }, sheetState = sheetState, containerColor = Color.White) {
                    // 2. AHORA LE PASAMOS TODAS LAS VARIABLES AL PITCH
                    SmartPitchSheetContent(
                        clientName = clientName,
                        clientLocation = clientLocation,
                        clientInterest = clientInterest,
                        clientProfession = clientProfession,
                        clientRequirement = clientRequirement
                    )
                }
            }
        }
    }
}

@Composable
fun SmartPitchSheetContent(
    clientName: String,
    clientLocation: String,
    clientInterest: String,
    clientProfession: String,
    clientRequirement: String
) {
    // --- PITCH DINÁMICO Y PERSUASIVO ---
    var pitchText by remember {
        mutableStateOf(
            "¡Hola $clientName! 👋 Espero que estés teniendo un excelente día.\n\n" +
                    "Viendo tu perfil, entiendo que como $clientProfession valoras tu tiempo y buscas inversiones inteligentes. Noté que tienes un interés fuerte en: $clientInterest por el sector de $clientLocation, buscando específicamente: $clientRequirement.\n\n" +
                    "Precisamente acaba de entrar al mercado una opción exclusiva que encaja a la perfección con lo que necesitas. Tiene un altísimo potencial y, por sus características, se moverá muy rápido.\n\n" +
                    "¿Te parece si hacemos un espacio de 5 minutos en tu agenda hoy mismo para compartirte los detalles en privado y que seas de los primeros en verla?"
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 32.dp)
            .verticalScroll(rememberScrollState()), // <-- ¡ESTA ES LA LÍNEA MÁGICA!
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val geminiGradient = Brush.linearGradient(colors = listOf(Color(0xFF4285F4), Color(0xFF9B72CB), Color(0xFFD96570)))
        val glowGradient = Brush.radialGradient(colors = listOf(Color(0xFF9B72CB).copy(alpha = 0.8f), Color.Transparent))
        Box(modifier = Modifier.size(90.dp).background(glowGradient, CircleShape), contentAlignment = Alignment.Center) {
            Box(modifier = Modifier.size(64.dp).clip(CircleShape).background(Color.White).border(2.dp, geminiGradient, CircleShape).padding(12.dp), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = R.drawable.logo_gemini), contentDescription = "Gemini AI", modifier = Modifier.fillMaxSize())
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color(0xFF234F68))) { append("Smart Pitch ") }
                withStyle(style = SpanStyle(color = Color.Black)) { append("listo ✨") }
            },
            fontSize = 22.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(24.dp))
        val darkGrayBackground = Color(0xFFE8ECEF)
        OutlinedTextField(
            value = pitchText,
            onValueChange = { pitchText = it },
            modifier = Modifier.fillMaxWidth().heightIn(min = 180.dp),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = darkGrayBackground, unfocusedContainerColor = darkGrayBackground,
                focusedBorderColor = Color.Transparent, unfocusedBorderColor = Color.Transparent,
            ),
            textStyle = LocalTextStyle.current.copy(fontSize = 15.sp, color = Color.DarkGray, lineHeight = 22.sp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { }, modifier = Modifier.size(54.dp).clip(RoundedCornerShape(12.dp)).background(darkGrayBackground)) {
                Icon(Icons.Default.Refresh, contentDescription = "Regenerar", tint = Color(0xFF234F68))
            }
            Button(
                onClick = { }, modifier = Modifier.weight(1f).height(54.dp), shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25D366))
            ) {
                Icon(painter = painterResource(id = R.drawable.wsp_logo_1), contentDescription = "WhatsApp", tint = Color.White, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Enviar por WhatsApp", fontWeight = FontWeight.Bold, color = Color.White)
            }
        }
    }
}

// ... LOS SUBCOMPONENTES InfoBlock, ProfileDataField y PropertyOfInterestCard SE MANTIENEN IGUALES ...
@Composable
fun InfoBlock(modifier: Modifier = Modifier, label: String, value: String) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF4F6F9))
            .padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = label, fontSize = 11.sp, color = Color.Gray, maxLines = 1, textAlign = TextAlign.Center, lineHeight = 12.sp)
        Spacer(modifier = Modifier.height(4.dp))
        // Aquí le subimos los maxLines a 3 para que no corte el texto
        Text(text = value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF234F68), maxLines = 3, textAlign = TextAlign.Center, lineHeight = 14.sp)
    }
}

@Composable
fun ProfileDataField(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF4F6F9))
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(text = label, fontSize = 12.sp, color = Color.Gray, fontWeight = FontWeight.Normal)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = value, fontSize = 15.sp, color = Color.Black, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun PropertyOfInterestCard(navController: NavController, interest: String, location: String, budget: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF4F6F9)),
        onClick = { navController.navigate("property_detail/1") } // <- AQUÍ arreglamos el crasheo también
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = R.drawable.propiedad_agenda_1), contentDescription = "Propiedad", modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp)), contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                // Aquí usamos las variables que le pasamos
                Text(text = interest, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF234F68), maxLines = 2)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = location, fontSize = 13.sp, color = Color.Gray, maxLines = 1)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = budget, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8BC83F))
            }
            Box(modifier = Modifier.size(16.dp).clip(CircleShape).background(Color(0xFF8BC83F)))
        }
    }
}