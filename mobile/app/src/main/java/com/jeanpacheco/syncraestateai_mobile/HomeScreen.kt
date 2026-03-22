package com.jeanpacheco.syncraestateai_mobile

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material.icons.automirrored.filled.Send // Por si lo necesitamos luego
// Imports para que funcione Firebase
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.lazy.items
// Imports para funcionalidades del micrófono.
import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts


// ==========================================
// PALETA DE COLORES (Basada en tu Figma)
// ==========================================
val SyncraPrimary = Color(0xFF234F68)
val SyncraBackgroundShape = Color(0xFFA5B8C2)
val TextGray = Color(0xFF7A8B94)
val SurfaceGray = Color(0xFFF4F6F9)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    // --- NUEVO: ESTADO GLOBAL PARA EL BUSCADOR ---
    var globalSearchQuery by remember { mutableStateOf("") }
    // --- NUEVO: ESTADO PARA NOTIFICACIONES ---
    var showNotifications by remember { mutableStateOf(false) }

    var notificationsList by remember {
        mutableStateOf(listOf(
            NotificationData("Cita confirmada", "Amanda Cifuentes aceptó la visita para hoy.", "10 min", Icons.Default.DateRange),
            NotificationData("Nuevo Prospecto", "Anderson Souza envió un mensaje por WhatsApp.", "2h", Icons.Default.Person),
            NotificationData("Recordatorio", "Seguimiento pendiente para Gustavo Ramos.", "5h", Icons.Default.Notifications)
        ))
    }

    Scaffold(
        bottomBar = { HomeBottomNavigationBar(navController) },
        containerColor = Color.White
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {

            // ==========================================
            // SECCIÓN SUPERIOR: FONDO CURVO + HEADER + BUSCADOR
            // ==========================================
            Box(modifier = Modifier.fillMaxWidth()) {

                // 1. EL FONDO CURVO AJUSTADO A FIGMA
                Canvas(modifier = Modifier.fillMaxWidth().height(240.dp)) {
                    val path = Path().apply {
                        moveTo(0f, 0f)
                        lineTo(size.width * 0.68f, 0f)
                        quadraticTo(
                            size.width * 0.5f, size.height * 0.8f,
                            0f, size.height * 0.7f
                        )
                        close()
                    }
                    drawPath(path = path, color = SyncraBackgroundShape)
                }

                // 2. CONTENIDO SOBRE EL FONDO CURVO
                Column(modifier = Modifier.fillMaxWidth()) {
                    HeaderSection(
                        onBellClick = { showNotifications = true },
                        onProfileClick = { navController.navigate("agent_profile") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                        // AHORA LE PASAMOS EL ESTADO Y CÓMO CAMBIARLO
                        SearchBarSection(
                            searchQuery = globalSearchQuery,
                            onSearchQueryChange = { globalSearchQuery = it }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ==========================================
            // SECCIÓN INFERIOR: CONTENIDO PRINCIPAL
            // ==========================================
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                CategoriesSection()
                Spacer(modifier = Modifier.height(24.dp))
                HeroCarouselSection()
                Spacer(modifier = Modifier.height(32.dp))
                // AHORA LE PASAMOS EL NAVCONTROLLER Y EL BUSCADOR
                ActivePropertiesSection(navController = navController, searchQuery = globalSearchQuery)
                Spacer(modifier = Modifier.height(32.dp))
                ActiveProspectsSection(navController = navController)

                Spacer(modifier = Modifier.height(32.dp))
                AgendaSection()

                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // --- NUEVO: VENTANA DE NOTIFICACIONES ---
        if (showNotifications) {
            ModalBottomSheet(
                onDismissRequest = { showNotifications = false },
                containerColor = Color.White,
                dragHandle = { BottomSheetDefaults.DragHandle(color = SyncraPrimary.copy(alpha = 0.3f)) },
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 40.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Notificaciones",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = SyncraPrimary
                        )
                        // ACCIÓN: Al dar clic, vaciamos la lista
                        Text(
                            text = "Marcar como leídas",
                            fontSize = 12.sp,
                            color = Color(0xFF8DB049),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable { notificationsList = emptyList() }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // LÓGICA DE PANTALLA VACÍA O LISTA
                    if (notificationsList.isEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.CheckCircle, // <--- ESTA ES LA RUTA EXACTA
                                contentDescription = "Todo leído",
                                tint = Color.LightGray,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text("¡Estás al día, Rodrigo!", fontWeight = FontWeight.Bold, color = SyncraPrimary)
                            Text("No tienes notificaciones pendientes.", color = TextGray, fontSize = 14.sp)
                        }
                    } else {
                        notificationsList.forEach { notification ->
                            NotificationItem(
                                title = notification.title,
                                desc = notification.desc,
                                time = notification.time,
                                icon = notification.icon,
                                isNew = true
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationItem(title: String, desc: String, time: String, icon: androidx.compose.ui.graphics.vector.ImageVector, isNew: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(if (isNew) SurfaceGray else Color.Transparent)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(if (isNew) SyncraPrimary.copy(alpha = 0.1f) else SurfaceGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = SyncraPrimary, modifier = Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold, color = SyncraPrimary, fontSize = 15.sp)
            Text(text = desc, color = TextGray, fontSize = 13.sp, maxLines = 1)
        }

        Column(horizontalAlignment = Alignment.End) {
            Text(text = time, color = TextGray, fontSize = 11.sp)
            if (isNew) {
                Box(modifier = Modifier.padding(top = 4.dp).size(8.dp).clip(CircleShape).background(Color(0xFF8DB049)))
            }
        }
    }
}

// ==========================================
// SUB-COMPONENTES DESGLOSADOS
// ==========================================

@Composable
fun HeaderSection(onBellClick: () -> Unit, onProfileClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 40.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_syncra),
                contentDescription = "Logo Syncra",
                modifier = Modifier.height(56.dp),
                contentScale = ContentScale.Fit
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, Color(0xFF8DB049), CircleShape)
                        .background(Color.White, CircleShape)
                        .clickable { onBellClick() }, // ACCIÓN CAMPANA
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_campana),
                        contentDescription = "Notificaciones",
                        tint = SyncraPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))

                Image(
                    painter = painterResource(id = R.drawable.img_perfil_agente),
                    contentDescription = "Foto de Perfil",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable { onProfileClick() }, // ACCIÓN PERFIL
                    contentScale = ContentScale.Crop
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(text = "¡Hola, ", fontSize = 26.sp, color = SyncraPrimary)
            Text(text = "Rodrigo!", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
        }
        Text(
            text = "Revisa tu agenda de hoy",
            fontSize = 22.sp,
            color = SyncraPrimary,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}

@Composable
fun SearchBarSection(searchQuery: String, onSearchQueryChange: (String) -> Unit) {
    val context = LocalContext.current

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val results = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = results?.get(0) ?: ""
            onSearchQueryChange(spokenText) // <-- Le avisamos a HomeScreen lo que escuchamos
        }
    }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange, // <-- Cada vez que tecleas, avisa a HomeScreen
        placeholder = { Text("Buscar cliente o propiedad...", color = Color.Gray, fontSize = 14.sp) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar", tint = SyncraPrimary)
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Box(modifier = Modifier.height(24.dp).width(1.dp).background(Color.LightGray))
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_mic),
                    contentDescription = "Micrófono",
                    tint = Color.Gray,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            try {
                                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                    putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                    putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-GT")
                                    putExtra(RecognizerIntent.EXTRA_PROMPT, "Dime qué estás buscando...")
                                }
                                speechRecognizerLauncher.launch(intent)
                            } catch (e: Exception) {
                                Toast.makeText(context, "Tu dispositivo no soporta dictado por voz", Toast.LENGTH_SHORT).show()
                            }
                        }
                )
            }
        },
        modifier = Modifier.fillMaxWidth().height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = SyncraPrimary,
            unfocusedTextColor = SyncraPrimary,
            unfocusedContainerColor = SurfaceGray,
            focusedContainerColor = SurfaceGray,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = SyncraPrimary
        ),
        singleLine = true
    )
}

@Composable
fun CategoriesSection() {
    val categories = listOf("Todas", "Pendientes", "Visitas", "Cerradas")
    var selectedIndex by remember { mutableStateOf(0) }

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(categories) { index, category ->
            val isSelected = index == selectedIndex

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) SyncraPrimary else SurfaceGray)
                    .clickable { selectedIndex = index }
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category,
                    color = if (isSelected) Color.White else SyncraPrimary,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun HeroCarouselSection() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        item {
            HeroCard(
                title = "Resumen del\ndía",
                subtitle = "¡Tienes 3 citas programadas para hoy!",
                imageRes = R.drawable.img_carrusel_1
            )
        }
        item {
            HeroCard(
                title = "Nuevos\nProspectos",
                subtitle = "Tienes 5 mensajes sin leer.",
                imageRes = R.drawable.img_carrusel_2
            )
        }
    }
}

@Composable
fun HeroCard(title: String, subtitle: String, imageRes: Int) {
    Box(
        modifier = Modifier
            .width(280.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable { /* Acción carrusel */ }
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Fondo Carrusel",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(modifier = Modifier.fillMaxSize().background(SyncraPrimary.copy(alpha = 0.4f)))

        Column(modifier = Modifier.padding(16.dp).align(Alignment.CenterStart).padding(bottom = 16.dp)) {
            Text(text = title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 26.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, color = Color.White, fontSize = 12.sp)
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(70.dp, 50.dp)
                .clip(RoundedCornerShape(topEnd = 24.dp))
                .background(SyncraPrimary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_flecha),
                contentDescription = "Ir",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun ActivePropertiesSection(navController: NavController, searchQuery: String) {
    var propertiesList by remember { mutableStateOf<List<Property>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("properties").get().addOnSuccessListener { result ->
            val list = mutableListOf<Property>()
            for (document in result) {
                list.add(Property(
                    id = document.id,
                    title = document.getString("title") ?: "Sin título",
                    price = document.getLong("price") ?: 0L,
                    location = document.getString("location") ?: "Sin ubicación",
                    interested = document.getLong("interested")?.toInt() ?: 0
                ))
            }
            propertiesList = list
            isLoading = false
        }.addOnFailureListener { isLoading = false }
    }

    // --- AQUÍ ESTÁ LA MAGIA DEL FILTRO ---
    val filteredProperties = if (searchQuery.isBlank()) {
        propertiesList
    } else {
        propertiesList.filter { prop ->
            prop.title.contains(searchQuery, ignoreCase = true) ||
                    prop.location.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Mis propiedades activas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
            Text(
                text = "Ver todas",
                fontSize = 12.sp,
                color = SyncraPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { navController.navigate("all_properties") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Text(text = "Descargando propiedades...", color = TextGray, modifier = Modifier.padding(16.dp))
        } else if (filteredProperties.isEmpty()) {
            Text(text = "No se encontraron propiedades", color = TextGray, modifier = Modifier.padding(16.dp))
        } else {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                // AQUÍ USAMOS LA LISTA FILTRADA
                items(filteredProperties) { prop ->
                    val precioConComas = java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(prop.price)
                    PropertyCard(
                        type = prop.type,
                        title = prop.title,
                        interested = prop.interested,
                        location = prop.location,
                        price = "Q. $precioConComas",
                        imageRes = prop.imageRes,
                        onClick = { navController.navigate("property_detail/${prop.id}") }
                    )
                }
            }
        }
    }
}

@Composable
fun PropertyCard(type: String, title: String, interested: Int, location: String, price: String, imageRes: Int, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier.width(350.dp).clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.width(130.dp).height(150.dp).clip(RoundedCornerShape(16.dp))) {
                Image(painter = painterResource(id = imageRes), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                Box(modifier = Modifier.align(Alignment.BottomStart).padding(8.dp).clip(RoundedCornerShape(8.dp)).background(SyncraPrimary.copy(alpha = 0.8f)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text(text = type, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary, maxLines = 2)
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp), tint = SyncraPrimary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "$interested interesados", fontSize = 13.sp, color = SyncraPrimary, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.Top, modifier = Modifier.fillMaxWidth()) {
                    Icon(painter = painterResource(id = R.drawable.ic_ubicacion), contentDescription = null, modifier = Modifier.size(16.dp).padding(top = 2.dp), tint = SyncraPrimary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = location, fontSize = 13.sp, color = TextGray, maxLines = 2, modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = price, fontSize = 18.sp, fontWeight = FontWeight.ExtraBold, color = SyncraPrimary)
                    Text(text = "/mes", fontSize = 12.sp, color = TextGray, modifier = Modifier.padding(bottom = 2.dp, start = 2.dp))
                }
            }
        }
    }
}

@Composable
fun HomeBottomNavigationBar(navController: NavController) {
    NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            selected = true,
            onClick = { /* Home */ },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = SyncraPrimary, indicatorColor = Color.Transparent)
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            selected = false,
            onClick = { /* Navegar */ },
            colors = NavigationBarItemDefaults.colors(unselectedIconColor = SyncraPrimary)
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            selected = false,
            onClick = { navController.navigate("agent_profile") },
            colors = NavigationBarItemDefaults.colors(unselectedIconColor = SyncraPrimary)
        )
    }
}

// ==========================================
// SECCIÓN DE CLIENTES ACTIVOS
// ==========================================
@Composable
fun ActiveProspectsSection(navController: NavController) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Clientes activos", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
            Text(
                text = "Ver todos",
                fontSize = 12.sp,
                color = SyncraPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable { navController.navigate("clients") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            item { ProspectItem(name = "Amanda Cifuentes", imageRes = R.drawable.img_prospecto_1) }
            item { ProspectItem(name = "Anderson Souza", imageRes = R.drawable.img_prospecto_2) }
            item { ProspectItem(name = "Ana Reyes", imageRes = R.drawable.img_prospecto_3) }
            item { ProspectItem(name = "Ramiro Castillo", imageRes = R.drawable.img_prospecto_4) }
            item { ProspectItem(name = "Gustavo Ramos", imageRes = R.drawable.img_prospecto_5) }
        }
    }
}

@Composable
fun ProspectItem(name: String, imageRes: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(76.dp)) {
        Image(painter = painterResource(id = imageRes), contentDescription = name, modifier = Modifier.size(64.dp).clip(CircleShape), contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = name, fontSize = 12.sp, color = SyncraPrimary, fontWeight = FontWeight.Medium, textAlign = TextAlign.Center, maxLines = 2)
    }
}

@Composable
fun ExploreProspectItem() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.size(64.dp).clip(CircleShape).background(SurfaceGray).border(1.dp, Color(0xFFDDE3E9), CircleShape).clickable { }, contentAlignment = Alignment.Center) {
            Icon(Icons.Default.Search, contentDescription = null, tint = SyncraPrimary, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Explorar", fontSize = 13.sp, color = SyncraPrimary)
    }
}

@Composable
fun AgendaSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Agenda", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
            Text(text = "Ver toda", fontSize = 12.sp, color = SyncraPrimary, fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            item { AgendaCard(time = "09:00 AM", dateTag = "Hoy", clientName = "Amanda Cifuentes", location = "Zona 15", imageRes = R.drawable.propiedad_agenda_1) }
            item { AgendaCard(time = "11:30 AM", dateTag = "Hoy", clientName = "Anderson Souza", location = "Zona 10", imageRes = R.drawable.propiedad_agenda_2) }
        }
    }
}

@Composable
fun AgendaCard(time: String, dateTag: String, clientName: String, location: String, imageRes: Int) {
    Card(modifier = Modifier.width(280.dp), shape = RoundedCornerShape(24.dp), colors = CardDefaults.cardColors(containerColor = SurfaceGray)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(modifier = Modifier.fillMaxWidth().height(180.dp)) {
                Image(painter = painterResource(id = imageRes), contentDescription = null, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                Box(modifier = Modifier.align(Alignment.TopEnd).padding(12.dp).size(42.dp).clip(CircleShape).background(Color(0xFF25D366))) {
                    Icon(painter = painterResource(id = R.drawable.wsp_logo_1), contentDescription = null, tint = Color.White, modifier = Modifier.size(30.dp).align(Alignment.Center))
                }
            }
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(text = clientName, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.ic_ubicacion), contentDescription = null, modifier = Modifier.size(16.dp), tint = TextGray)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = location, fontSize = 14.sp, color = TextGray)
                }
            }
        }
    }
}

data class Property(
    val id: String = "",
    val title: String = "",
    val price: Long = 0L,
    val location: String = "",
    val type: String = "Casa",
    val interested: Int = 5,
    val imageRes: Int = R.drawable.propiedad_1
)

data class NotificationData(
    val title: String,
    val desc: String,
    val time: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)