package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clientes", fontWeight = FontWeight.Bold, color = SyncraPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Vuelve atrás
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = SyncraPrimary)
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(painterResource(id = R.drawable.ic_campana), contentDescription = "Notificaciones", tint = SyncraPrimary, modifier = Modifier.size(24.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = { HomeBottomNavigationBar() }, // Reutilizamos tu barra inferior
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // 1. Buscador (Similar al del Home)
            ClientsSearchBar()

            Spacer(modifier = Modifier.height(20.dp))

            // 2. Filtros (Activos, Inactivos, Todos)
            ClientsFilterSection()

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Lista de Clientes
            // Aquí es donde al hacer clic en Amanda Cifuentes, iremos a la siguiente pantalla
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    ClientListCard(
                        name = "Amanda Cifuentes",
                        tag = "Prospecto",
                        phone = "+502 4589-9810",
                        email = "amanda.cif@gmail.com",
                        imageRes = R.drawable.img_prospecto_1,
                        onClick = {
                            // TODO: Navegar a la pantalla de Perfil/Match de Amanda
                            // navController.navigate("client_profile_amanda")
                        }
                    )
                }
                item {
                    ClientListCard(
                        name = "Anderson Souza",
                        tag = "Prospecto",
                        phone = "+502 5542-1090",
                        email = "ansouza@gmail.com",
                        imageRes = R.drawable.img_prospecto_2,
                        onClick = { /* Acción futura */ }
                    )
                }
                item {
                    ClientListCard(
                        name = "Ana Reyes",
                        tag = "Cliente",
                        phone = "+502 3341-8899",
                        email = "ana.reyes@empresa.com",
                        imageRes = R.drawable.img_prospecto_3,
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
        trailingIcon = { Icon(painterResource(id = R.drawable.ic_mic), contentDescription = "Mic", tint = Color.Gray, modifier = Modifier.size(24.dp)) },
        modifier = Modifier.fillMaxWidth().height(52.dp),
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
fun ClientsFilterSection() {
    val filters = listOf("Activos", "Inactivos", "Todos")
    var selectedIndex by remember { mutableStateOf(0) }

    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        itemsIndexed(filters) { index, filter ->
            val isSelected = index == selectedIndex
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) SyncraPrimary else SurfaceGray)
                    .clickable { selectedIndex = index }
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = filter,
                    color = if (isSelected) Color.White else TextGray,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                )
            }
        }
    }
}

@Composable
fun ClientListCard(name: String, tag: String, phone: String, email: String, imageRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }, // Hace que toda la tarjeta sea clickeable
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Foto de Perfil
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier.size(64.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Textos (Nombre, etiqueta, datos)
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    // Etiqueta (Ej: Prospecto)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFFE2E8F0)) // Gris clarito para la etiqueta
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(text = tag, fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.SemiBold)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Phone, contentDescription = "Teléfono", tint = TextGray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = phone, fontSize = 12.sp, color = TextGray)
                }

                Spacer(modifier = Modifier.height(2.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Email, contentDescription = "Email", tint = TextGray, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = email, fontSize = 12.sp, color = TextGray)
                }
            }

            // Botón de WhatsApp
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF25D366))
                    .clickable { /* Abrir WhatsApp */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.wsp_logo_1),
                    contentDescription = "WhatsApp",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}