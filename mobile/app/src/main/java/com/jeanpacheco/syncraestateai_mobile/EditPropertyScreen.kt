package com.jeanpacheco.syncraestateai_mobile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore // <-- IMPORTANTE PARA FIREBASE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPropertyScreen(navController: NavController, propertyId: String) {
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    // 1. Ahora empiezan vacíos (se llenarán al descargar de Firebase)
    var title by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var isSaving by remember { mutableStateOf(false) }

    // 2. DESCARGAMOS LA INFO REAL
    LaunchedEffect(propertyId) {
        if (propertyId.isNotEmpty()) {
            db.collection("properties").document(propertyId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        title = document.getString("title") ?: ""
                        price = document.getLong("price")?.toString() ?: ""
                        location = document.getString("location") ?: ""
                        description = document.getString("descripcion") ?: ""
                    }
                }
        }
    }

    // 3. FUNCIÓN PARA GUARDAR EN FIREBASE
    fun savePropertyInfo() {
        if (propertyId.isEmpty() || isSaving) return
        isSaving = true

        val updates = mapOf(
            "title" to title,
            "price" to (price.toLongOrNull() ?: 0L),
            "location" to location,
            "descripcion" to description
        )

        db.collection("properties").document(propertyId)
            .update(updates)
            .addOnSuccessListener {
                Toast.makeText(context, "¡Propiedad actualizada!", Toast.LENGTH_SHORT).show()
                navController.popBackStack()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error al guardar", Toast.LENGTH_SHORT).show()
                isSaving = false
            }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Publicación", fontWeight = FontWeight.Bold, color = SyncraPrimary, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Cancelar", tint = SyncraPrimary)
                    }
                },
                actions = {
                    // Ahora sí llama a la función de guardar
                    IconButton(onClick = { savePropertyInfo() }) {
                        Icon(Icons.Default.Check, contentDescription = "Guardar", tint = ColorVisitaHoy)
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
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Información Básica", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Gray)

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título de la propiedad") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                // Forzamos el texto a negro sin importar el tema del celular
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Precio (Q)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Ubicación exacta") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción larga") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 5,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                // Ahora sí llama a la función de guardar
                onClick = { savePropertyInfo() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ColorVisitaHoy),
                shape = RoundedCornerShape(12.dp),
                enabled = !isSaving
            ) {
                Text(
                    text = if (isSaving) "Guardando..." else "Actualizar Propiedad",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}