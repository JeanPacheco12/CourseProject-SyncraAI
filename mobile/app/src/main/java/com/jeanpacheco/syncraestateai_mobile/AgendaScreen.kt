package com.jeanpacheco.syncraestateai_mobile

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
import androidx.compose.material.icons.filled.ArrowBack
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
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendaScreen(navController: NavController) {
    var selectedDayIndex by remember { mutableStateOf(3) } // Por defecto seleccionamos "Hoy" (índice 3 en nuestra lista de ejemplo)
    var agendaClients by remember { mutableStateOf<List<Map<String, String>>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Generamos una lista de días simulada (ej. Lun 12, Mar 13, etc.)
    val calendarDays = generateWeekDays()

    // Descargamos las citas de Firebase
    LaunchedEffect(selectedDayIndex) {
        isLoading = true
        val db = FirebaseFirestore.getInstance()

        // LÓGICA TEMPORAL: Solo mostramos datos si seleccionan "Hoy" .
        // En el futuro, aquí filtrarás por la fecha seleccionada.
        if (selectedDayIndex == 3) {
            db.collection("clients")
                .whereEqualTo("status", "Visita hoy")
                .get()
                .addOnSuccessListener { result ->
                    val clients = mutableListOf<Map<String, String>>()
                    for (document in result) {
                        clients.add(
                            mapOf(
                                "id" to document.id,
                                "name" to (document.getString("name") ?: "Sin nombre"),
                                "location" to (document.getString("location") ?: "Sin ubicación"),
                                "time" to (document.getString("time") ?: "Por confirmar")
                            )
                        )
                    }
                    agendaClients = clients
                    isLoading = false
                }
                .addOnFailureListener { isLoading = false }
        } else {
            // Si es otro día distinto a hoy, simulamos que no hay citas (por ahora)
            agendaClients = emptyList()
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Agenda", fontWeight = FontWeight.Bold, color = SyncraPrimary, fontSize = 20.sp) },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // 1. Calendario Semanal (Horizontal)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(calendarDays.size) { index ->
                    val day = calendarDays[index]
                    val isSelected = index == selectedDayIndex

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) SyncraPrimary else SurfaceGray)
                            .clickable { selectedDayIndex = index }
                            .padding(vertical = 16.dp, horizontal = 12.dp)
                            .width(48.dp)
                    ) {
                        Text(
                            text = day.first, // Día de la semana (Ej. "Lun")
                            fontSize = 12.sp,
                            color = if (isSelected) Color.White.copy(alpha = 0.8f) else TextGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = day.second, // Número de día (Ej. "14")
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else SyncraPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 2. Título de la fecha seleccionada
            val selectedDateText = if (selectedDayIndex == 3) "Hoy" else "${calendarDays[selectedDayIndex].first} ${calendarDays[selectedDayIndex].second}"
            Text(
                text = "Citas para $selectedDateText",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = SyncraPrimary,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3. Lista Vertical de Citas
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = SyncraPrimary)
                }
            } else if (agendaClients.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().padding(bottom = 100.dp), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🏖️", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("No tienes citas para este día", color = TextGray, fontSize = 16.sp)
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(agendaClients) { client ->
                        AgendaListItemCard(
                            clientName = client["name"] ?: "",
                            location = client["location"] ?: "",
                            time = client["time"] ?: "Por confirmar",
                            onClick = { navController.navigate("client_profile/${client["id"]}") }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AgendaListItemCard(clientName: String, location: String, time: String, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceGray),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.propiedad_agenda_1),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(16.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Cita con $clientName", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = SyncraPrimary)
                Spacer(modifier = Modifier.height(6.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painter = painterResource(id = R.drawable.ic_ubicacion), contentDescription = null, modifier = Modifier.size(14.dp), tint = TextGray)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = location, color = TextGray, fontSize = 13.sp, maxLines = 1)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(Color.White).padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(text = time, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
                }
            }
        }
    }
}

// Función auxiliar para generar los días (simulación)
fun generateWeekDays(): List<Pair<String, String>> {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -3) // Empezamos 3 días atrás
    val days = mutableListOf<Pair<String, String>>()
    val dayFormat = SimpleDateFormat("EEE", Locale("es", "ES"))
    val numFormat = SimpleDateFormat("dd", Locale.getDefault())

    for (i in 0..6) {
        val dayStr = dayFormat.format(calendar.time).replaceFirstChar { it.uppercase() }
        val numStr = numFormat.format(calendar.time)
        days.add(Pair(dayStr, numStr))
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }
    return days
}