package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeanpacheco.syncraestateai_mobile.ui.theme.SyncraGreen

@Composable
fun OnboardingScreen() {
    // El box permite apilar cosas una encima de la otra (como si fueran capas en Photoshop).
    Box(modifier = Modifier.fillMaxSize()) {
        // Box se divide en:
        // 1. Capa del fondo (La imagen del edificio de la pantalla de inicio).
        Image(
            painter = painterResource(id = R.drawable.bg_edificio),
            contentDescription = "Fondo de la ciudad",
            contentScale = ContentScale.Crop, // Esto hace que la imagen llene toda la pantalla sin deformarse.
            modifier = Modifier.fillMaxSize()
        )

        // 2. Capa de oscurecimiento (Para que el logo y el botón resalten como en los mock-ups de Figma).
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f)) // Un 60% de oscuridad.
        )

        // 3. Capa de Contenido (Logo arriba y botón abajo).
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween // Esto empuja el logo arriba y el botón abajo.
        ) {

            // Sección superior: El Logo.
            Image(
                painter = painterResource(id = R.drawable.logo_syncra),
                contentDescription = "Logo Syncra",
                modifier = Modifier
                    .height(180.dp) // Se puede ajustar este número si el logo se ve muy grande o pequeño.
                    .padding(top = 40.dp)
            )

            // Sección inferior: El Botón de Comenzar.
            Button(
                onClick = { /* TODO: Mañana haremos que este botón navegue al Login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SyncraGreen)
            ) {
                Text(
                    text = "Comenzar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}