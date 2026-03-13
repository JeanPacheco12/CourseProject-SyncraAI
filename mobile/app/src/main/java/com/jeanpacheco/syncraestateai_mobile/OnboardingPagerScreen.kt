package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember

// 1. Estructura de datos
data class OnboardingPage(
    val imageRes: Int,
    val titleFirstPart: String,
    val titleBoldPart: String,
    val description: String
)

@Composable
fun OnboardingPagerScreen(navController: NavController) {
    // Colores exactos del diseño original de Figma.
    val SyncraGreen = Color(0xFF8BC83F)
    val SyncraDarkBlue = Color(0xFF204D6C)
    val OmitirBgColor = Color(0xFFE5E5E5)

    // 2. páginas del carrusel en forma de lista
    val pages = listOf(
        OnboardingPage(
            imageRes = R.drawable.ob_1,
            titleFirstPart = "Gestiona tu ",
            titleBoldPart = "agenda y\npropiedades",
            description = "Organiza tus citas, clientes e inmuebles desde un solo lugar para no perder ninguna oportunidad de venta."
        ),
        OnboardingPage(
            imageRes = R.drawable.ob_2,
            titleFirstPart = "Cierra ventas usando\n",
            titleBoldPart = "Inteligencia Artificial",
            description = "Genera mensajes persuasivos al instante con nuestra integración de Gemini AI. Envía la propuesta perfecta por WhatsApp con un solo clic."
        ),
        OnboardingPage(
            imageRes = R.drawable.ob_3,
            titleFirstPart = "Lleva tus ventas al\n",
            titleBoldPart = "Siguiente nivel",
            description = "Mantén el control total de tus clientes y sincroniza tu progreso en tiempo real con la plataforma web de tu gerencia."
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {

        // CAPA 1: EL HEADER.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 24.dp, end = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            // LOGO: Tamaño fijo restaurado y alineación mantenida.
            Image(
                painter = painterResource(id = R.drawable.bg_logo),
                contentDescription = "Logo Syncra (Ir a inicio)",
                modifier = Modifier
                    .size(width = 150.dp, height = 45.dp) // Regresamos al tamaño grande.
                    .clickable (
                        // ESTAS DOS LÍNEAS QUITAN EL EFECTO VISUAL SOMBREADO AL PRESIONAR EL LOGO.
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ){
                        navController.navigate("onboarding") {
                            // Esto evita que se acumulen múltiples pantallas iguales si se apacha mucho.
                            popUpTo(0)
                        }
                    },
                alignment = Alignment.CenterStart,
                contentScale = ContentScale.Fit
            )

            // BOTÓN OMITIR.
            Surface(
                onClick = { navController.navigate("login") },
                color = OmitirBgColor,
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    text = "Omitir",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 36.dp, vertical = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // CAPA 2: EL CARRUSEL.
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { position ->
            val page = pages[position]

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {

                // SECCIÓN TEXTO ALINEADO A LA IZQUIERDA.
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.Normal)) {
                            append(page.titleFirstPart)
                        }
                        withStyle(style = SpanStyle(color = SyncraDarkBlue, fontWeight = FontWeight.ExtraBold)) {
                            append(page.titleBoldPart)
                        }
                    },
                    fontSize = 28.sp,
                    lineHeight = 36.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = page.description,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    textAlign = TextAlign.Start,
                    lineHeight = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(32.dp))

                // SECCIÓN IMAGEN + OVERLAYS (Botón y Visualizer).
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f) // Esto empuja la imagen hasta abajo.
                        .padding(bottom = 32.dp)
                        .clip(RoundedCornerShape(32.dp))
                ) {

                    // La Imagen de fondo.
                    Image(
                        painter = painterResource(id = page.imageRes),
                        contentDescription = "Imagen de onboarding",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    // Contenedor del Botón y la Línea Visualizer.
                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        // VISUALIZER.
                        Row(
                            modifier = Modifier
                                .padding(bottom = 24.dp)
                                .width(100.dp)
                                .height(4.dp)
                                .clip(RoundedCornerShape(50))
                        ) {
                            repeat(pages.size) { iteration ->
                                val color = if (pagerState.currentPage == iteration) Color.White else Color.White.copy(alpha = 0.4f)

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .background(color)
                                )
                            }
                        }

                        // Botón Verde.
                        Button(
                            onClick = {
                                if (pagerState.currentPage == pages.size - 1) {
                                    navController.navigate("login")
                                } else {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.65f)
                                .height(50.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SyncraGreen)
                        ) {
                            Text(
                                text = if (pagerState.currentPage == pages.size - 1) "Comenzar" else "Siguiente",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}