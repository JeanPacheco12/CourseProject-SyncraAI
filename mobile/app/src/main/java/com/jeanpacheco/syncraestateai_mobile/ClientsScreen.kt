package com.jeanpacheco.syncraestateai_mobile

import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

// Variables de colores
val ColorVisitaHoy = Color(0xFF8BC83F)
val ColorEntrega = Color(0xFF3675D3)
val ColorFirmaHoy = Color(0xFFED9923)
val ColorSeguimiento = Color(0xFFE5CF23)
val ColorNuevo = Color(0xFFA13FC8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen(navController: NavController) {
    // --- ESTADOS ELEVADOS (State Hoisting) ---
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    var clientsList by remember { mutableStateOf<List<Client>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // --- FIREBASE FETCH ---
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("clients").get().addOnSuccessListener { result ->
            val list = mutableListOf<Client>()
            for (document in result) {
                list.add(Client(
                    id = document.id,
                    name = document.getString("name") ?: "Sin nombre",
                    requirement = document.getString("requirement") ?: "",
                    time = document.getString("time") ?: "",
                    status = document.getString("status") ?: "Nuevo",
                    imageRes = R.drawable.img_prospecto_1
                ))
            }
            clientsList = list
            isLoading = false
        }.addOnFailureListener { isLoading = false }
    }

    // --- LÓGICA DE DOBLE FILTRO (Buscador + Chips) ---
    val filteredClients = clientsList.filter { client ->
        val matchSearch = client.name.contains(searchQuery, ignoreCase = true) || client.requirement.contains(searchQuery, ignoreCase = true)
        val matchFilter = if (selectedFilter == null) true else client.status.equals(selectedFilter, ignoreCase = true)
        matchSearch && matchFilter
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Clientes Activos", fontWeight = FontWeight.Bold, color = SyncraPrimary, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = SyncraPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = "Este es el listado de tus clientes recurrentes",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // 1. Buscador con Micrófono
            ClientsSearchBar(searchQuery = searchQuery, onQueryChange = { searchQuery = it })

            Spacer(modifier = Modifier.height(20.dp))

            // 2. Filtros de Chips
            ClientsFilterSection(selectedFilter = selectedFilter, onFilterChange = { selectedFilter = it })

            Spacer(modifier = Modifier.height(20.dp))

            // 3. Lista Dinámica
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = SyncraPrimary)
                }
            } else if (filteredClients.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No se encontraron clientes", color = Color.Gray)
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(filteredClients) { client ->
                        // Asignamos el color dinámicamente según el status
                        val bgColor = when (client.status) {
                            "Firma hoy" -> ColorFirmaHoy
                            "Visita hoy" -> ColorVisitaHoy
                            "Entrega" -> ColorEntrega
                            "Seguimiento" -> ColorSeguimiento
                            else -> ColorNuevo
                        }

                        ClientListCard(
                            name = client.name,
                            status = client.status,
                            propertyInfo = client.requirement,
                            date = client.time,
                            imageRes = client.imageRes,
                            bgColor = bgColor,
                            onClick = {
                                // ¡MAGIA! Viaja al perfil pasando el ID
                                navController.navigate("client_profile/${client.id}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ClientsSearchBar(searchQuery: String, onQueryChange: (String) -> Unit) {
    val context = LocalContext.current
    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
            onQueryChange(spokenText)
        }
    }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        placeholder = { Text("Buscar cliente...", color = Color.Gray, fontSize = 14.sp) },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = SyncraPrimary) },
        trailingIcon = {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 12.dp)) {
                Box(modifier = Modifier.height(24.dp).width(1.dp).background(Color.LightGray))
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_mic),
                    contentDescription = "Mic",
                    tint = Color.Gray,
                    modifier = Modifier.size(24.dp).clickable {
                        try {
                            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-GT")
                            }
                            speechRecognizerLauncher.launch(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Micrófono no soportado", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            }
        },
        modifier = Modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedContainerColor = SurfaceGray,
            unfocusedContainerColor = SurfaceGray,
            focusedBorderColor = SyncraPrimary,
            unfocusedBorderColor = Color.Transparent,
        ),
        singleLine = true
    )
}

@Composable
fun ClientsFilterSection(selectedFilter: String?, onFilterChange: (String?) -> Unit) {
    val filters = listOf(
        Pair("Firma hoy", ColorFirmaHoy),
        Pair("Visita hoy", ColorVisitaHoy),
        Pair("Entrega", ColorEntrega),
        Pair("Seguimiento", ColorSeguimiento),
        Pair("Nuevo", ColorNuevo)
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        items(filters) { (text, color) ->
            val isSelected = text == selectedFilter

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(if (isSelected) color else SurfaceGray)
                    .clickable {
                        // Si ya estaba seleccionado, lo deselecciona. Si no, lo selecciona.
                        onFilterChange(if (isSelected) null else text)
                    }
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
fun ClientListCard(name: String, status: String, propertyInfo: String, date: String, imageRes: Int, bgColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(id = imageRes), contentDescription = name, modifier = Modifier.size(64.dp).clip(CircleShape), contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = status, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Home, contentDescription = "Propiedad", tint = Color.White, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = propertyInfo, fontSize = 12.sp, color = Color.White)
                }
            }
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier.height(64.dp)) {
                Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.2f)).clickable { }, contentAlignment = Alignment.Center) {
                    Icon(painter = painterResource(id = R.drawable.wsp_logo_1), contentDescription = "WhatsApp", tint = Color.White, modifier = Modifier.size(24.dp))
                }
                Text(text = date, fontSize = 10.sp, color = Color.White)
            }
        }
    }
}