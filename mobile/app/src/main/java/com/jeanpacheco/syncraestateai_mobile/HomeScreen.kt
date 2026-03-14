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
                // Redujimos la altura a 240.dp para quitar el espacio en blanco sobrante
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

            // Espaciado sutil para separar el buscador de los botones
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
                Spacer(modifier = Modifier.height(40.dp))
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
                modifier = Modifier.height(56.dp), // Aumentamos la altura (puedes subirlo a 64.dp si aún lo ves pequeño)
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
                        .clip(CircleShape), // Esto hace que la imagen se recorte en forma de círculo
                    contentScale = ContentScale.Crop // El Crop asegura que la foto llene todo el círculo sin aplastarse
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
        // 1. La imagen de fondo de la casa
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = "Fondo Carrusel",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop // Asegura que la imagen llene la tarjeta sin deformarse
        )

        // 2. Filtro oscuro/azulado para que el texto blanco resalte (como en tu Figma)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(SyncraPrimary.copy(alpha = 0.4f)) // 40% de opacidad de tu color primario
        )

        // 3. Textos alineados al centro-izquierda
        Column(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterStart)
                .padding(bottom = 16.dp) // Un pequeño empuje hacia arriba para que no choque con el botón
        ) {
            Text(text = title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold, lineHeight = 26.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = subtitle, color = Color.White, fontSize = 12.sp)
        }

        // 4. El botón con la flecha (se queda igualito)
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

        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            item {
                PropertyCard(
                    type = "Apartamento",
                    title = "Apartamento Neo Zona 10",
                    interested = 7,
                    location = "Zona 10, Cd. de Guatemala",
                    price = "Q. 8,500"
                )
            }
            item {
                PropertyCard(
                    type = "Casa",
                    title = "Casa Moderna Cayalá",
                    interested = 12,
                    location = "Zona 16, Cd. de Guatemala",
                    price = "Q. 15,000"
                )
            }
        }
    }
}

@Composable
fun PropertyCard(type: String, title: String, interested: Int, location: String, price: String) {
    Card(
        modifier = Modifier.width(240.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(140.dp)) {

                Box(
                    modifier = Modifier.fillMaxSize().background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Home, contentDescription = "Propiedad", tint = Color.Gray, modifier = Modifier.size(40.dp))
                }

                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .size(28.dp)
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

                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SyncraPrimary.copy(alpha = 0.8f))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = type, color = Color.White, fontSize = 10.sp)
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary, maxLines = 1)
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Person, contentDescription = "Interesados", modifier = Modifier.size(14.dp), tint = SyncraPrimary)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "$interested interesados", fontSize = 12.sp, color = SyncraPrimary, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_ubicacion),
                        contentDescription = "Ubicación",
                        modifier = Modifier.size(14.dp),
                        tint = SyncraPrimary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = location, fontSize = 12.sp, color = TextGray, maxLines = 1)
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(text = price, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
                    Text(text = "/mes", fontSize = 12.sp, color = TextGray, modifier = Modifier.padding(bottom = 2.dp))
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