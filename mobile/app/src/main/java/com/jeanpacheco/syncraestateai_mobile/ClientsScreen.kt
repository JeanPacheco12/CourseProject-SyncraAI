package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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

// Definición de colores según los estados del diseño
val ColorFirmaHoy = Color(0xFF8B5CF6) // Morado
val ColorVisitaHoy = Color(0xFFF97316) // Naranja
val ColorEntrega = Color(0xFFEF4444) // Rojo
val ColorSeguimiento = Color(0xFF10B981) // Verde Teal
val ColorNuevo = Color(0xFF8DB049) // Verde Claro

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clientes", fontWeight = FontWeight.Bold, color = SyncraPrimary, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = SyncraPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
        // bottomBar = { HomeBottomNavigationBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // 1. Buscador
            ClientsSearchBar()

            Spacer(modifier = Modifier.height(20.dp))

            // 2. Filtros
            ClientsFilterSection()

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Lista de Clientes
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    ClientListCard(
                        name = "Amanda Cifuentes",
                        status = "Firma hoy",
                        email = "amanda.cif@gmail.com",
                        date = "12 oct 2024",
                        imageRes = R.drawable.img_prospecto_1,
                        bgColor = ColorFirmaHoy,
                        onClick = {
                            // TODO: Navegar al perfil de Amanda
                        }
                    )
                }
                item {
                    ClientListCard(
                        name = "Anderson Souza",
                        status = "Visita hoy",
                        email = "ansouza@gmail.com",
                        date = "10 oct 2024",
                        imageRes = R.drawable.img_prospecto_2,
                        bgColor = ColorVisitaHoy,
                        onClick = { /* Acción futura */ }
                    )
                }
                item {
                    ClientListCard(
                        name = "Ana Reyes",
                        status = "Entrega",
                        email = "ana.reyes@empresa.com",
                        date = "08 oct 2024",
                        imageRes = R.drawable.img_prospecto_3,
                        bgColor = ColorEntrega,
                        onClick = { /* Acción futura */ }
                    )
                }
                item {
                    ClientListCard(
                        name = "Carlos Pérez",
                        status = "Seguimiento",
                        email = "carlos.perez@gmail.com",
                        date = "05 oct 2024",
                        imageRes = R.drawable.img_prospecto_2, // Reemplazar con otra imagen si tienes
                        bgColor = ColorSeguimiento,
                        onClick = { /* Acción futura */ }
                    )
                }
            }
        }
    }
}

// ==========================================
// SUB-COMPONENTES
// ==========================================

@Composable
fun ClientsSearchBar() {
    var searchQuery by remember { mutableStateOf("") }
    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = { Text("Buscar cliente...", color = Color.Gray, fontSize = 14.sp) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = SyncraPrimary) },
        trailingIcon = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(end = 12.dp)
            ) {
                // Línea divisoria vertical
                Box(
                    modifier = Modifier
                        .height(24.dp)
                        .width(1.dp)
                        .background(Color.LightGray)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_mic),
                    contentDescription = "Mic",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        modifier = Modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = SurfaceGray,
            unfocusedContainerColor = SurfaceGray,
            disabledContainerColor = SurfaceGray,
            focusedBorderColor = SyncraPrimary,
            unfocusedBorderColor = Color.Transparent,
        ),
        singleLine = true
    )
}

@Composable
fun ClientsFilterSection() {
    // Definimos los filtros y sus colores correspondientes según el diseño
    val filters = listOf(
        Pair("Firma hoy", ColorFirmaHoy),
        Pair("Visita hoy", ColorVisitaHoy),
        Pair("Entrega", ColorEntrega),
        Pair("Seguimiento", ColorSeguimiento),
        Pair("Nuevo", ColorNuevo)
    )

    // Dejamos un estado nulo para cuando no hay nada seleccionado, o 0 si quieres uno por defecto
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        itemsIndexed(filters) { index, filterPair ->
            val isSelected = index == selectedIndex
            val (text, color) = filterPair

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) color else SurfaceGray)
                    .clickable { selectedIndex = index }
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = text,
                    color = if (isSelected) Color.White else TextGray,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun ClientListCard(
    name: String,
    status: String,
    email: String,
    date: String,
    imageRes: Int,
    bgColor: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto de Perfil
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Textos (Nombre, Status y Correo)
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = status, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Email, contentDescription = "Email", tint = Color.White, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = email, fontSize = 12.sp, color = Color.White)
                }
            }

            // Columna Derecha: WhatsApp Arriba, Fecha Abajo
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.height(64.dp)
            ) {
                // Botón de WhatsApp
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)), // Fondo sutil para que resalte sobre el color
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.wsp_logo_1),
                        contentDescription = "WhatsApp",
                        tint = Color.White, // WhatsApp en blanco
                        modifier = Modifier.size(18.dp)
                    )
                }

                // Fecha
                Text(text = date, fontSize = 10.sp, color = Color.White)
            }
        }
    }
}