package com.jeanpacheco.syncraestateai_mobile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect // NUEVO
import androidx.compose.runtime.getValue     // NUEVO
import androidx.compose.runtime.mutableStateOf // NUEVO
import androidx.compose.runtime.remember     // NUEVO
import androidx.compose.runtime.setValue     // NUEVO
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
import androidx.compose.foundation.clickable
import androidx.compose.ui.platform.LocalContext

// Imports de Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore // NUEVO: Para conectarnos a la base de datos

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentProfileScreen(navController: NavController) {

    // --- 1. ESTADO PARA GUARDAR EL NOMBRE (Empieza diciendo "Cargando...") ---
    var agentName by remember { mutableStateOf("Cargando...") }

    // ---> 1. AGREGA ESTA LÍNEA NUEVA AQUÍ <---
    var agentProfileImageUrl by remember { mutableStateOf("") }

    // --- 2. LÓGICA DE CONEXIÓN A FIRESTORE ---
    // LaunchedEffect(Unit) hace que esto se ejecute UNA SOLA VEZ cuando se abre la pantalla.
    LaunchedEffect(Unit) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val db = FirebaseFirestore.getInstance()

            // Buscamos en la colección "users" el documento donde el "uid" coincida con el usuario logueado
            db.collection("users")
                .whereEqualTo("uid", currentUser.uid)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        // Si encontramos al usuario, extraemos sus datos
                        val doc = documents.documents[0]
                        val nombre = doc.getString("nombre") ?: ""
                        val apellido = doc.getString("apellido") ?: ""

                        // Actualizamos la variable de estado con el nombre completo
                        agentName = "$nombre $apellido"

                        // ---> 2. AGREGA ESTA LÍNEA NUEVA AQUÍ <---
                        agentProfileImageUrl = doc.getString("profileImageUrl") ?: ""

                        // Actualizamos la variable de estado con el nombre completo
                        agentName = "$nombre $apellido"
                    } else {
                        // Si por alguna razón el usuario está logueado pero no tiene datos en Firestore
                        agentName = "Agente Sin Nombre"
                    }
                }
                .addOnFailureListener { e ->
                    // Esto mostrará el error real en la pantalla en lugar del nombre
                    agentName = "Error: ${e.message}"
                }
        } else {
            agentName = "Invitado"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Perfil", fontWeight = FontWeight.Bold, color = SyncraPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás", tint = SyncraPrimary)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // FOTO DE PERFIL CON MARCO VERDE
            Box(contentAlignment = Alignment.BottomEnd) {
                // ---> 3. MAGIA DE COIL AQUÍ <---
                coil.compose.AsyncImage(
                    model = agentProfileImageUrl,
                    contentDescription = "Foto Agente",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(SurfaceGray),
                    contentScale = ContentScale.Crop,
                    // Dejamos tu imagen de agente como placeholder por si falla el internet
                    placeholder = painterResource(id = R.drawable.img_perfil_agente),
                    error = painterResource(id = R.drawable.img_perfil_agente)
                )
                // Indicador de "Online" o Verificado
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(3.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFF8DB049)))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- 3. APLICAMOS LA VARIABLE DINÁMICA AQUÍ ---
            Text(text = agentName, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = SyncraPrimary)
            Text("Agente Senior Inmobiliario", color = TextGray, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(32.dp))

            // TARJETA DE ESTADÍSTICAS
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(SurfaceGray)
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem("12", "Ventas")
                Box(modifier = Modifier.width(1.dp).height(40.dp).background(Color.LightGray))
                StatItem("45", "Visitas")
                Box(modifier = Modifier.width(1.dp).height(40.dp).background(Color.LightGray))
                StatItem("4.9/5", "Rating")
            }

            Spacer(modifier = Modifier.height(32.dp))

            val context = LocalContext.current

            // MENÚ DE OPCIONES
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                ProfileOptionItem(
                    text = "Mis Estadísticas",
                    icon = Icons.Default.Info,
                    onClick = { navController.navigate("statistics") }
                )

                ProfileOptionItem(
                    text = "Configuración de IA",
                    icon = Icons.Default.Settings,
                    onClick = { navController.navigate("ai_config") }
                )

                ProfileOptionItem(
                    text = "Ayuda y Soporte",
                    icon = Icons.Default.Email,
                    onClick = { navController.navigate("support") }
                )

                // BOTÓN: CERRAR SESIÓN
                ProfileOptionItem(
                    text = "Cerrar sesión",
                    icon = Icons.Default.ExitToApp,
                    textColor = Color(0xFFD32F2F),
                    iconTint = Color(0xFFD32F2F),
                    onClick = {
                        FirebaseAuth.getInstance().signOut()

                        navController.navigate("onboarding") {
                            popUpTo(0) { inclusive = true }
                        }
                        Toast.makeText(context, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
        Text(text = label, fontSize = 12.sp, color = TextGray)
    }
}

@Composable
fun ProfileOptionItem(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    textColor: Color = SyncraPrimary,
    iconTint: Color = SyncraPrimary,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceGray)
            .clickable { onClick() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontWeight = FontWeight.SemiBold, color = textColor, modifier = Modifier.weight(1f))
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = TextGray)
    }
}