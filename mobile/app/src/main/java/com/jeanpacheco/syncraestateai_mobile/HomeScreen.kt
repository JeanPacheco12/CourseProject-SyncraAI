package com.jeanpacheco.syncraestateai_mobile

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Colores de tu paleta Figma
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
            // 1. HEADER
            HeaderSection()

            Spacer(modifier = Modifier.height(16.dp))

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                // 2. BUSCADOR
                SearchBarSection()

                Spacer(modifier = Modifier.height(24.dp))

                // 3. CATEGORÍAS
                CategoriesSection()

                Spacer(modifier = Modifier.height(24.dp))

                // 4. CARRUSEL HERO
                HeroCarouselSection()

                Spacer(modifier = Modifier.height(32.dp))

                // 5. MIS PROPIEDADES ACTIVAS
                ActivePropertiesSection()

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

// --- SUB-COMPONENTES ---

@Composable
fun HeaderSection() {
    Box(modifier = Modifier.fillMaxWidth()) {

        // Mancha de fondo segura
        Box(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(220.dp)
                .clip(RoundedCornerShape(bottomEnd = 120.dp))
                .background(SyncraBackgroundShape)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 40.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // LOGO SEGURO (Solo texto por ahora para evitar crash)
                Text(text = "SYNCRA", color = SyncraPrimary, fontSize = 20.sp, fontWeight = FontWeight.Black)

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .border(1.dp, Color(0xFF8DB049), CircleShape)
                            .background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        // Icono campana tuyo (es ligero, no debería crashear)
                        Icon(
                            painter = painterResource(id = R.drawable.ic_campana),
                            contentDescription = "Notificaciones",
                            tint = SyncraPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))

                    // PERFIL SEGURO (Icono genérico en lugar de foto pesada)
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White)
                    }
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
                Divider(
                    color = Color.LightGray,
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                // Micrófono tuyo (es ligero)
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
                subtitle = "¡Tienes 3 citas programadas para hoy!"
            )
        }
        item {
            HeroCard(
                title = "Nuevos\nProspectos",
                subtitle = "Tienes 5 mensajes sin leer."
            )
        }
    }
}

@Composable
fun HeroCard(title: String, subtitle: String) {
    Box(
        modifier = Modifier
            .width(280.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(24.dp))
            // FONDO SEGURO (Color sólido en lugar de imagen)
            .background(SyncraPrimary.copy(alpha = 0.8f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
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
            // Flecha tuya (es ligera)
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

                // FONDO SEGURO (Caja gris con ícono en lugar de imagen pesada)
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Home, contentDescription = "Propiedad", tint = Color.Gray, modifier = Modifier.size(40.dp))
                }

                Box(
                    modifier = Modifier
                        .padding(12.dp)
                        .size(24.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF8DB049))
                )
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
                    // Ubicación tuya (es ligera)
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
        tonalElevation = 16.dp
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
            onClick = { /* Navegar */ },
            colors = NavigationBarItemDefaults.colors(unselectedIconColor = SyncraPrimary)
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            selected = false,
            onClick = { /* Navegar */ },
            colors = NavigationBarItemDefaults.colors(unselectedIconColor = SyncraPrimary)
        )
    }
}