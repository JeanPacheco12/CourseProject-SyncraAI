package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.jeanpacheco.syncraestateai_mobile.ui.theme.SyncraGreen
import kotlinx.coroutines.launch

// 1. Una estructura de datos para guardar la info de cada pestaña.
data class OnboardingPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

@Composable
fun OnboardingPagerScreen(navController: NavController) {
    // 2. La lista con los textos reales de Figma.
    val pages = listOf(
        OnboardingPage(
            imageRes = R.drawable.ob_1, // Placeholder: Luego lo cambias por la imagen real
            title = "Gestiona tu agenda y\npropiedades",
            description = "Organiza tus citas, clientes e inmuebles desde un solo lugar para no perder ninguna oportunidad de venta."
        ),
        OnboardingPage(
            imageRes = R.drawable.ob_2,
            title = "Cierra ventas usando\nInteligencia Artificial",
            description = "Genera mensajes persuasivos al instante con nuestra integración de Gemini AI. Envía la propuesta perfecta por WhatsApp con un solo clic."
        ),
        OnboardingPage(
            imageRes = R.drawable.ob_3,
            title = "Lleva tus ventas al\nSiguiente nivel",
            description = "Mantén el control total de tus clientes y sincroniza tu progreso en tiempo real con la plataforma web de tu gerencia."
        )
    )

    // 3. Controladores del carrusel.
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope() // Sirve para animar el cambio de pestaña al pulsar el botón.

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {

        Column(modifier = Modifier.fillMaxSize()) {

            // 4. El Carrusel que se puede deslizar.
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { position ->
                val page = pages[position]

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    // Imagen superior con las esquinas de abajo redondeadas (como en Figma).
                    Image(
                        painter = painterResource(id = page.imageRes),
                        contentDescription = "Imagen ilustrativa",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.6f) // La imagen ocupa el 60% de la altura.
                            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Título.
                    Text(
                        text = page.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF102A3E), // El azul oscuro de Syncra.
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Descripción.
                    Text(
                        text = page.description,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                }
            }

            // 5. Los puntitos indicadores (Dots).
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pages.size) { iteration ->
                    // Si es la página actual, el punto es Verde y más ancho. Si no, es Gris y pequeño.
                    val color = if (pagerState.currentPage == iteration) SyncraGreen else Color.LightGray
                    val width = if (pagerState.currentPage == iteration) 24.dp else 10.dp

                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(width = width, height = 10.dp)
                            .clip(RoundedCornerShape(5.dp))
                            .background(color)
                    )
                }
            }

            // 6. Botón dinámico inferior.
            Button(
                onClick = {
                    if (pagerState.currentPage == pages.size - 1) {
                        // Si estamos en la última pestaña, vamos al Login.
                        navController.navigate("login")
                    } else {
                        // Si no, avanzamos a la siguiente pestaña.
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 24.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SyncraGreen)
            ) {
                // El texto del botón cambia automáticamente.
                Text(
                    text = if (pagerState.currentPage == pages.size - 1) "Comenzar" else "Siguiente",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }

        // 7. Botón flotante de "Omitir" arriba a la derecha.
        TextButton(
            onClick = { navController.navigate("login") }, // Salta directo al login.
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 48.dp, end = 16.dp)
        ) {
            Text(text = "Omitir", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}