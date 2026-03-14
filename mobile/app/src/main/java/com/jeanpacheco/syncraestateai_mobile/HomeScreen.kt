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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
import com.jeanpacheco.syncraestateai_mobile.ui.theme.SyncraGreen

@Composable
fun HomeScreen(navController: NavController) {
    // Scaffold es el "marco" que sostiene la barra inferior y el contenido
    Scaffold(
        bottomBar = { HomeBottomNavigationBar() },
        containerColor = Color.White
    ) { paddingValues ->

        // Contenido principal con Scroll Vertical
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Respeta el espacio de la barra inferior
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp) // Margen lateral general
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // 1. HEADER (Saludo y Foto de perfil)
            HeaderSection()

            Spacer(modifier = Modifier.height(24.dp))

            // 2. BUSCADOR
            SearchBarSection()

            Spacer(modifier = Modifier.height(24.dp))

            // 3. CATEGORÍAS
            CategoriesSection()

            Spacer(modifier = Modifier.height(24.dp))

            // 4. PROPIEDADES
            PropertiesSection()

            Spacer(modifier = Modifier.height(24.dp))

            // 5. PROSPECTOS
            ProspectsSection()

            // Espacio extra al final para que no quede pegado
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

// --- SUB-COMPONENTES ---

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = "Hola de nuevo, Jean Pacheco", // Estático por ahora
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "Tienes 4 reuniones hoy",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        // Foto de perfil circular
        Image(
            // ¡Recuerda cambiar esto por el nombre de tu foto de perfil real! ej: R.drawable.img_perfil_agente
            painter = painterResource(id = R.drawable.img_perfil_agente),
            contentDescription = "Foto de perfil",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
    }
}

@Composable
fun SearchBarSection() {
    var searchQuery by remember { mutableStateOf("") }
    val TextFieldBgColor = Color(0xFFF5F5F5)

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Buscar prospecto, propiedad...", color = Color.Gray, fontSize = 14.sp) },
        // Lupa a la izquierda
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = Color.Gray
            )
        },
        // Ícono de filtros a la derecha (Usamos un ícono de menú de Android)
        trailingIcon = {
            Icon(
                painter = painterResource(id = android.R.drawable.ic_menu_sort_by_size),
                contentDescription = "Filtros",
                tint = Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        // Bordes súper redondos estilo píldora
        shape = CircleShape,
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = TextFieldBgColor,
            focusedContainerColor = TextFieldBgColor,
            unfocusedBorderColor = Color.Transparent,
            focusedBorderColor = SyncraGreen // Borde verde al tocarlo
        ),
        singleLine = true
    )
}

@Composable
fun CategoriesSection() {
    // Lista estática de categorías
    val categories = listOf("Todos", "Casas", "Apartamentos", "Terrenos")

    // Estado para recordar cuál está seleccionado (Por defecto el 0, que es "Todos")
    var selectedIndex by remember { mutableStateOf(0) }
    val SyncraDarkBlue = Color(0xFF1F4C6B)

    // Lista horizontal deslizable
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp) // Espacio entre botones
    ) {
        itemsIndexed(categories) { index, category ->
            val isSelected = index == selectedIndex

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(if (isSelected) SyncraDarkBlue else Color.Transparent)
                    // Borde gris sutil si no está seleccionado
                    .border(
                        width = 1.dp,
                        color = if (isSelected) Color.Transparent else Color.LightGray,
                        shape = CircleShape
                    )
                    .clickable { selectedIndex = index } // Al tocar, se actualiza el estado
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category,
                    color = if (isSelected) Color.White else Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun HomeBottomNavigationBar() {
    val SyncraDarkBlue = Color(0xFF1F4C6B)

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = true,
            onClick = { /* Ya estamos aquí */ },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = SyncraDarkBlue,
                indicatorColor = SyncraDarkBlue, // Círculo azul
                unselectedIconColor = Color.Gray
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            label = { Text("Buscar") },
            selected = false,
            onClick = { /* Navegar a buscar */ }
        )
        // El botón del centro (IA / Agregar)
        NavigationBarItem(
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favoritos") },
            label = { Text("Favoritos") },
            selected = false,
            onClick = { /* Navegar a favoritos */ }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = false,
            onClick = { /* Navegar a perfil */ }
        )
    }
}

// --- SECCIÓN: PROPIEDADES (Cerca de ti) ---
@Composable
fun PropertiesSection() {
    val SyncraDarkBlue = Color(0xFF1F4C6B)

    Column(modifier = Modifier.fillMaxWidth()) {
        // Título de la sección
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Cerca de ti", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = "Ver todo", fontSize = 14.sp, color = SyncraDarkBlue, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Carrusel de tarjetas
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Simulamos 3 tarjetas estáticas
            items(3) {
                Card(
                    modifier = Modifier
                        .width(220.dp)
                        .height(240.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                ) {
                    Column {
                        // Imagen de la propiedad
                        Image(
                            // Cambia esto por tu img_propiedad_1
                            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                            contentDescription = "Propiedad",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(140.dp)
                                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        )
                        // Info de la propiedad
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "$250,000", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SyncraDarkBlue)
                            Text(text = "Casa Moderna en Zona 14", fontSize = 14.sp, color = Color.Black, maxLines = 1)
                            Text(text = "Guatemala, Ciudad", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

// --- SECCIÓN: PROSPECTOS ---
@Composable
fun ProspectsSection() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Tus Prospectos", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // Simulamos 3 prospectos
        // Usamos un forEach en lugar de LazyColumn porque toda la pantalla ya hace scroll
        (1..3).forEach { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Foto y Datos
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        // Cambia esto por tus img_prospecto_1
                        painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                        contentDescription = "Prospecto",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.LightGray)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(text = "Cliente $index", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Interesado en Apartamento", fontSize = 12.sp, color = Color.Gray)
                    }
                }

                // Botones de acción (WhatsApp y Teléfono)
                Row {
                    Icon(
                        // Cambia esto por tu ic_whatsapp
                        painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                        contentDescription = "WhatsApp",
                        tint = Color(0xFF25D366), // Verde de WhatsApp
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White, CircleShape)
                            .padding(8.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = Icons.Default.Person, // Cambiaremos luego por un icono de teléfono
                        contentDescription = "Llamar",
                        tint = Color(0xFF1F4C6B),
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color.White, CircleShape)
                            .padding(8.dp)
                    )
                }
            }
        }
    }
}