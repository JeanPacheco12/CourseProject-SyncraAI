package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size // Importamos size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale // Importamos scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jeanpacheco.syncraestateai_mobile.ui.theme.SyncraGreen
import androidx.navigation.NavController

@Composable
fun OnboardingScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Capa del fondo
        Image(
            painter = painterResource(id = R.drawable.bg_edificio),
            contentDescription = "Fondo de la ciudad",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Capa de oscurecimiento (Con el filtro azul estilo Figma)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF2E5B7A).copy(alpha = 0.4f), // Arriba: Más claro y transparente para que se pueda ver el fondo.
                            Color(0xFF102A3E)                     // Abajo: Azul oscuro y 100% sólido para el efecto visto en Figma.
                        )
                    )
                )
        )

        // 3. Capa de Contenido
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                // El truco que apliqué: Quitué el fillMaxWidth responsivo porque la imagen tenia mucho aire y empujaba el botón hacia abajo al escalarla.
                // Usé una altura fija grande y un escalado manual firme.
                Image(
                    painter = painterResource(id = R.drawable.logo_syncra),
                    contentDescription = "Logo Syncra",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(top = 90.dp) // top = 90 para bajar el logo y que quede similar al de Figma.
                        .height(300.dp) // Altura grande.
                        .fillMaxWidth() // Que intente ocupar el ancho restante.
                        .scale(0.80f) // Escalar la imagen con una escala prudente para la pantalla principal de Onboarding.
                )
            }

            // Sección inferior: El Botón y texto.
            Button(
                onClick = {
                    // Cambiamos el destino: ahora vamos al carrusel
                    navController.navigate("onboarding_pager")
                },
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

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Plataforma para Agentes",
                color = Color.White,
                fontSize = 14.sp
            )
        }
    }
}