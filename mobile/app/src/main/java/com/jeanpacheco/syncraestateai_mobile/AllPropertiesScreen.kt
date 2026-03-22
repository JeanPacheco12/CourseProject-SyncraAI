package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllPropertiesScreen(navController: NavController) {
    var propertiesList by remember { mutableStateOf<List<Property>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }

    // Descargamos TODAS las propiedades desde Firebase
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
                    interested = document.getLong("interested")?.toInt() ?: 0,
                    type = document.getString("type") ?: "Propiedad",
                    status = document.getString("status") ?: "Todas",
                    imageRes = R.drawable.img_carrusel_1
                ))
            }
            propertiesList = list
            isLoading = false
        }.addOnFailureListener { isLoading = false }
    }

    // Lógica para filtrar por texto
    val filteredProperties = propertiesList.filter { prop ->
        prop.title.contains(searchQuery, ignoreCase = true) ||
                prop.location.contains(searchQuery, ignoreCase = true) ||
                prop.type.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Catálogo", fontWeight = FontWeight.Bold, color = SyncraPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = SyncraPrimary)
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
        ) {
            // ==========================================
            // SECCIÓN SUPERIOR: Header y Buscador
            // ==========================================
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                Text(
                    text = "Encuentra la propiedad ideal",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = SyncraPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Explora nuestro portafolio activo",
                    fontSize = 14.sp,
                    color = TextGray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Buscador estilo Syncra
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Buscar por zona, nombre o tipo...", color = Color.Gray, fontSize = 14.sp) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = SyncraPrimary) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        // --- AQUÍ ESTÁ LA MAGIA DEL COLOR NEGRO ---
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        unfocusedContainerColor = SurfaceGray,
                        focusedContainerColor = SurfaceGray,
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = SyncraPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Contador dinámico de resultados
                if (!isLoading) {
                    Text(
                        text = "${filteredProperties.size} propiedades encontradas",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SyncraPrimary
                    )
                }
            }

            // ==========================================
            // SECCIÓN INFERIOR: Lista de propiedades
            // ==========================================
            Box(modifier = Modifier.fillMaxSize()) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = SyncraPrimary
                    )
                } else if (filteredProperties.isEmpty()) {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "No se encontraron propiedades", color = SyncraPrimary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Intenta con otra búsqueda", color = TextGray, fontSize = 14.sp)
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(filteredProperties) { prop ->
                            val precioConComas = java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(prop.price)

                            PropertyCard(
                                type = prop.type,
                                title = prop.title,
                                interested = prop.interested,
                                location = prop.location,
                                price = "Q. $precioConComas",
                                imageRes = prop.imageRes,
                                onClick = {
                                    // AHORA ENVIAMOS EL ID DE LA PROPIEDAD
                                    navController.navigate("property_detail/${prop.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}