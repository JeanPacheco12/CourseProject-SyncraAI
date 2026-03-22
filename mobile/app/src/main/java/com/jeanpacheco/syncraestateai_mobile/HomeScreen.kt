package com.jeanpacheco.syncraestateai_mobile

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign
// Imports para que funcione Firebase
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.lazy.items

// ==========================================
// PALETA DE COLORES (Basada en tu Figma)
// ==========================================
val SyncraPrimary = Color(0xFF234F68)
val SyncraBackgroundShape = Color(0xFFA5B8C2)
val TextGray = Color(0xFF7A8B94)
val SurfaceGray = Color(0xFFF4F6F9)

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        bottomBar = { HomeBottomNavigationBar() },
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
                    HeaderSection()
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                        SearchBarSection()
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
                ActivePropertiesSection()
                Spacer(modifier = Modifier.height(32.dp))
                ActiveProspectsSection(navController = navController)

                Spacer(modifier = Modifier.height(32.dp))
                AgendaSection()

                Spacer(modifier = Modifier.height(24.dp)) // Reducido de 100.dp a 24.dp para un margen natural
            }
        }
    }
}

// ==========================================
// SUB-COMPONENTES DESGLOSADOS
// ==========================================

@Composable
fun HeaderSection() {
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
                        .background(Color.White, CircleShape),
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
                        .clip(CircleShape),
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
fun SearchBarSection() {
    var searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Buscar cliente o propiedad...", color = Color.Gray, fontSize = 14.sp) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Buscar", tint = SyncraPrimary)
        },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_mic),
                    contentDescription = "Micrófono",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
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
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Fondo Carrusel",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SyncraPrimary.copy(alpha = 0.4f))
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterStart)
                .padding(bottom = 16.dp)
        ) {
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
fun ActivePropertiesSection() {
    // 1. Estados para guardar la lista de casas y saber si está cargando
    var propertiesList by remember { mutableStateOf<List<Property>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // 2. EL PUENTE: Vamos a Firebase a traer los datos nomás carga la pantalla
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("properties")
            .get()
            .addOnSuccessListener { result ->
                val list = mutableListOf<Property>()
                for (document in result) {
                    list.add(
                        Property(
                            id = document.id,
                            title = document.getString("title") ?: "Sin título",
                            price = document.getLong("price") ?: 0L,
                            location = document.getString("location") ?: "Sin ubicación"
                        )
                    )
                }
                propertiesList = list // Guardamos la lista descargada
                isLoading = false // Apagamos la carga
            }
            .addOnFailureListener {
                isLoading = false
            }
    }

    // 3. LA INTERFAZ GRÁFICA
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Mis propiedades activas", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
            Text(text = "Ver todas", fontSize = 12.sp, color = SyncraPrimary, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Si está cargando, mostramos un textito, si no, mostramos el carrusel
        if (isLoading) {
            Text(text = "Descargando propiedades...", color = TextGray, modifier = Modifier.padding(16.dp))
        } else if (propertiesList.isEmpty()) {
            Text(text = "Aún no hay propiedades en el sistema.", color = TextGray, modifier = Modifier.padding(16.dp))
        } else {
            // EL PINTOR: Dibuja una PropertyCard por cada casa que bajó de Firebase
            LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                items(propertiesList) { prop ->

                    // 1. Agregamos esta línea mágica para darle formato con comas:
                    val precioConComas = java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(prop.price)

                    PropertyCard(
                        type = prop.type,
                        title = prop.title,
                        interested = prop.interested,
                        location = prop.location,
                        // 2. Usamos la nueva variable formateada aquí:
                        price = "Q. $precioConComas",
                        imageRes = prop.imageRes
                    )
                }
            }
        }
    }
}

@Composable
fun PropertyCard(type: String, title: String, interested: Int, location: String, price: String, imageRes: Int) {
    Card(
        modifier = Modifier.width(350.dp), // Lo hice un poquitito más ancho (350.dp) para dar más espacio
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // ==========================================
            // LADO IZQUIERDO: IMAGEN + ETIQUETA + BOTÓN COMPARTIR
            // ==========================================
            Box(
                modifier = Modifier
                    .width(130.dp)
                    .height(150.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Propiedad",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Etiqueta de "Casa/Apartamento" sobre la imagen (Abajo Izquierda)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SyncraPrimary.copy(alpha = 0.8f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = type, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }

                // Botón verde de Compartir (Arriba Izquierda)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8DB049)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartir",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp)) // Reduje un poco este espacio para darle más a los textos

            // ==========================================
            // LADO DERECHO: TEXTOS E ÍCONOS
            // ==========================================
            Column(
                modifier = Modifier.fillMaxWidth(), // Toma todo el espacio restante
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = SyncraPrimary,
                    maxLines = 2,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = "Interesados", modifier = Modifier.size(16.dp), tint = SyncraPrimary)
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "$interested interesados", fontSize = 13.sp, color = SyncraPrimary, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    verticalAlignment = Alignment.Top, // Cambié a Top por si ocupa dos líneas
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ubicacion),
                        contentDescription = "Ubicación",
                        modifier = Modifier
                            .size(16.dp)
                            .padding(top = 2.dp), // Ajuste fino para alinear con la primera línea del texto
                        tint = SyncraPrimary
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = location,
                        fontSize = 13.sp,
                        color = TextGray,
                        maxLines = 2, // ¡Permite 2 líneas para que no se corte!
                        lineHeight = 16.sp,
                        modifier = Modifier.weight(1f) // Esto hace que el texto ocupe todo el ancho disponible y baje a la siguiente línea si es necesario
                    )
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
fun HomeBottomNavigationBar() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            selected = true,
            onClick = { /* Ya estamos aquí */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = SyncraPrimary,
                indicatorColor = Color.Transparent,
                unselectedIconColor = Color.Gray
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            selected = false,
            onClick = { /* Navegar a buscador */ },
            colors = NavigationBarItemDefaults.colors(unselectedIconColor = SyncraPrimary)
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            selected = false,
            onClick = { /* Navegar a perfil */ },
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

            // ¡AQUÍ ESTÁ LA MAGIA! Le agregamos un .clickable
            Text(
                text = "Ver todos",
                fontSize = 12.sp,
                color = SyncraPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable {
                    navController.navigate("clients") // Viaja a la pantalla de clientes
                }
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

// ==========================================
// PROSPECT ITEM
// ==========================================
@Composable
fun ProspectItem(name: String, imageRes: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(76.dp) // Ancho fijo para que el espacio entre todos sea exacto siempre
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 12.sp, // Un poco más pequeño para que quepa bien
            color = SyncraPrimary,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center, // Centramos el texto
            maxLines = 2, // Le damos permiso de usar 2 líneas si el nombre es largo
            lineHeight = 14.sp
        )
    }
}

@Composable
fun ExploreProspectItem() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(SurfaceGray)
                .border(1.dp, Color(0xFFDDE3E9), CircleShape) // Borde sutil gris claro
                .clickable { /* Acción para buscar prospectos */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Search, contentDescription = "Explorar", tint = SyncraPrimary, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Explorar", fontSize = 13.sp, color = SyncraPrimary, fontWeight = FontWeight.Medium)
    }
}

// ==========================================
// SECCIÓN DE AGENDA
// ==========================================
@Composable
fun AgendaSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Agenda", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
            Text(text = "Ver toda", fontSize = 12.sp, color = SyncraPrimary, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Cambiamos de Column a LazyRow para que sea un carrusel horizontal
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                AgendaCard(
                    time = "09:00 - 10:00 AM",
                    dateTag = "Hoy",
                    clientName = "Amanda Cifuentes",
                    location = "Zona 15, Cd. de Guatemala",
                    imageRes = R.drawable.propiedad_agenda_1
                )
            }
            item {
                AgendaCard(
                    time = "11:30 - 12:30 PM",
                    dateTag = "Hoy",
                    clientName = "Anderson Souza",
                    location = "Zona 10, Cd. de Guatemala",
                    imageRes = R.drawable.propiedad_agenda_2
                )
            }
            item {
                AgendaCard(
                    time = "02:00 - 03:00 PM",
                    dateTag = "Mañana",
                    clientName = "Ramiro Castillo",
                    location = "Cayalá, Zona 16",
                    imageRes = R.drawable.propiedad_agenda_3
                )
            }
            item {
                AgendaCard(
                    time = "04:30 - 05:30 PM",
                    dateTag = "Mañana",
                    clientName = "Luis de León",
                    location = "Zona 10, Cd. de Guatemala",
                    imageRes = R.drawable.propiedad_agenda_4
                )
            }
        }
    }
}

// ==========================================
// AGENDA CARD
// ==========================================
@Composable
fun AgendaCard(time: String, dateTag: String, clientName: String, location: String, imageRes: Int) {
    Card(
        modifier = Modifier.width(280.dp), // Agregamos ancho fijo para que no ocupe toda la pantalla
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {

            // ==========================================
            // PARTE SUPERIOR: Imagen completa + Overlays
            // ==========================================
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp) // Imagen grande, estilo Figma
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Propiedad Agenda",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Botón de WhatsApp (Esquina Superior Derecha)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF25D366)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.wsp_logo_1),
                        contentDescription = "Contactar",
                        tint = Color.White,
                        modifier = Modifier.size(30.dp)
                    )
                }

                // Horario y Etiqueta (Esquina Inferior Izquierda)
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Fondo oscuro SyncraPrimary para la hora
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SyncraPrimary.copy(alpha = 0.9f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = time,
                            color = Color.White,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Etiqueta de "Hoy/Mañana"
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF8DB049))
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        Text(text = dateTag, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // ==========================================
            // PARTE INFERIOR: Información debajo de la imagen
            // ==========================================
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = clientName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = SyncraPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ubicacion),
                        contentDescription = "Ubicación",
                        modifier = Modifier.size(16.dp),
                        tint = TextGray
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = location, fontSize = 14.sp, color = TextGray, maxLines = 1)
                }
            }
        }
    }
}

// ==========================================
// MODELO DE DATOS PARA FIREBASE
// ==========================================
data class Property(
    val id: String = "",
    val title: String = "",
    val price: Long = 0L,
    val location: String = "",
    // Valores por defecto para lo que aún no tenemos en DB:
    val type: String = "Casa",
    val interested: Int = 5, // Número por defecto
    val imageRes: Int = R.drawable.propiedad_1 // Imagen por defecto
)
