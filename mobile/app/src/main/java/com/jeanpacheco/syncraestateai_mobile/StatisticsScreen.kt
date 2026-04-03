package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    var totalClients by remember { mutableIntStateOf(0) }
    var closedDeals by remember { mutableIntStateOf(0) }
    var activeProperties by remember { mutableIntStateOf(0) }
    var isLoading by remember { mutableStateOf(true) }

    // Descargamos y contamos la data real de Firebase
    LaunchedEffect(Unit) {
        val db = FirebaseFirestore.getInstance()

        // 1. Contar propiedades activas
        db.collection("properties").get().addOnSuccessListener { props ->
            activeProperties = props.size()

            // 2. Contar clientes y tratos cerrados
            db.collection("clients").get().addOnSuccessListener { clients ->
                totalClients = clients.size()
                var cerrados = 0
                for (doc in clients) {
                    // Si en el futuro agregas el estatus "Cerrado" a un cliente
                    if (doc.getString("status") == "Cerrada" || doc.getString("status") == "Cerrado") {
                        cerrados++
                    }
                }
                closedDeals = cerrados
                isLoading = false
            }.addOnFailureListener { isLoading = false }
        }.addOnFailureListener { isLoading = false }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Estadísticas", fontWeight = FontWeight.Bold, color = SyncraPrimary, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = SyncraPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = SyncraPrimary)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Tarjeta principal (Comisiones - Visual / Proyección por ahora)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = SyncraPrimary)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = "Comisiones generadas (Mes)", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        // Usamos un número dinámico simulado basado en cierres para motivar
                        val comisiones = closedDeals * 15000 + 35000
                        val comisionesFormato = java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(comisiones)
                        Text(text = "Q. $comisionesFormato", color = Color.White, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Resumen de Actividad", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
                Spacer(modifier = Modifier.height(16.dp))

                // Grid de estadísticas (2 columnas)
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Propiedades Activas",
                        value = activeProperties.toString(),
                        icon = Icons.Default.Home,
                        iconColor = Color(0xFF8DB049) // Verde Syncra
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Total de Prospectos",
                        value = totalClients.toString(),
                        icon = Icons.Default.Person,
                        iconColor = Color(0xFF4285F4) // Azul
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Cierres Exitosos",
                        value = closedDeals.toString(),
                        icon = Icons.Default.Star,
                        iconColor = Color(0xFFFFB300) // Amarillo/Dorado
                    )
                    StatCard(
                        modifier = Modifier.weight(1f),
                        title = "Citas Completadas",
                        value = "12", // Esto lo podemos conectar luego al historial de agenda
                        icon = Icons.Default.DateRange,
                        iconColor = Color(0xFF9B72CB) // Morado
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, title: String, value: String, icon: ImageVector, iconColor: Color) {
    Card(
        modifier = modifier.height(140.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray)
    ) {
        Column(
            modifier = Modifier.padding(16.dp).fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(iconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
            }
            Column {
                Text(text = value, fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = SyncraPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = title, fontSize = 12.sp, color = Color.Gray, lineHeight = 14.sp)
            }
        }
    }
}