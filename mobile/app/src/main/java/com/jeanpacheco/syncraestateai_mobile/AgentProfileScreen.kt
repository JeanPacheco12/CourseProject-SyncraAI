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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgentProfileScreen(navController: NavController) {
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
                Image(
                    painter = painterResource(id = R.drawable.img_perfil_agente),
                    contentDescription = "Foto Agente",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(SurfaceGray),
                    contentScale = ContentScale.Crop
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

            Text("Rodrigo Arévalo", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = SyncraPrimary)
            Text("Agente Senior Inmobiliario", color = TextGray, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(32.dp))

            // TARJETA DE ESTADÍSTICAS (Muy Figma style)
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
                StatItem("4.9", "Rating")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // MENÚ DE OPCIONES
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                ProfileOptionItem("Mis Estadísticas", Icons.Default.Info)
                ProfileOptionItem("Configuración de IA", Icons.Default.Star)
                ProfileOptionItem("Ayuda y Soporte", Icons.Default.Email)

                Spacer(modifier = Modifier.height(20.dp))

                // BOTÓN CERRAR SESIÓN
                Button(
                    onClick = { navController.navigate("login") { popUpTo("home_screen") { inclusive = true } } },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFEBEB)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.ExitToApp, contentDescription = null, tint = Color.Red)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cerrar Sesión", color = Color.Red, fontWeight = FontWeight.Bold)
                }
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
fun ProfileOptionItem(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceGray)
            .clickable {
                Toast.makeText(context, "Sección de $text próximamente", Toast.LENGTH_SHORT).show()
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = SyncraPrimary, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontWeight = FontWeight.SemiBold, color = SyncraPrimary, modifier = Modifier.weight(1f))
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, tint = TextGray)
    }
}