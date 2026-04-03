package com.jeanpacheco.syncraestateai_mobile

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ayuda y Soporte", fontWeight = FontWeight.Bold, color = SyncraPrimary, fontSize = 20.sp) },
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Ícono ilustrativo central
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(SurfaceGray),
                contentAlignment = Alignment.Center
            ) {
                Text("🎧", fontSize = 48.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "¿En qué podemos ayudarte?", fontSize = 22.sp, fontWeight = FontWeight.ExtraBold, color = SyncraPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Nuestro equipo está disponible de lunes a viernes de 8 AM a 5 PM para resolver cualquier duda con tu app.",
                color = Color.Gray,
                fontSize = 14.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botones de Acción
            SupportActionCard(
                title = "Chat de Soporte",
                desc = "Respuestas rápidas por WhatsApp",
                icon = Icons.Default.Phone,
                onClick = {
                    try {
                        // Puedes cambiar el número por el de soporte real (puse código de Guate +502 por ejemplo)
                        val uri = Uri.parse("https://wa.me/50212345678?text=Hola,%20necesito%20ayuda%20con%20la%20app%20SyncraEstateAI")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "No tienes WhatsApp instalado", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SupportActionCard(
                title = "Enviar un Correo",
                desc = "soporte@syncra.com",
                icon = Icons.Default.Email,
                onClick = {
                    try {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:soporte@syncra.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Soporte SyncraEstateAI - App Móvil")
                        }
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "No hay aplicación de correo instalada", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            SupportActionCard(
                title = "Preguntas Frecuentes",
                desc = "Guías y tutoriales de la app",
                icon = Icons.Default.Info,
                onClick = {
                    try {
                        // Aquí pones la URL web donde tu amigo Rodri tenga las FAQs
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "No se pudo abrir el navegador", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}

@Composable
fun SupportActionCard(title: String, desc: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceGray)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = SyncraPrimary)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = SyncraPrimary)
            Text(text = desc, color = Color.Gray, fontSize = 12.sp)
        }
    }
}