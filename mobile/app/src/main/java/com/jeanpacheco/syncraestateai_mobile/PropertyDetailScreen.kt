package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class) // Agregamos esto por si acaso
@Composable
fun PropertyDetailScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    // Usamos un Scaffold para poder dejar el botón de "Agendar Visita" fijo abajo
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(24.dp)
            ) {
                Button(
                    onClick = { /* Acción para agendar visita */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SyncraPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Agendar visita", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        },
        content = { paddingValues -> // Especificamos explícitamente el 'content'
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {
                // 1. HEADER: Imagen principal con botones encima
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                ) {
                    // Imagen de la propiedad
                    Image(
                        painter = painterResource(id = R.drawable.propiedad_agenda_1), // Reemplaza con la imagen real de la propiedad
                        contentDescription = "Imagen de la propiedad",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Fila superior con botones de regresar y compartir
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp, start = 16.dp, end = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Botón Regresar
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.8f))
                                .size(40.dp)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = SyncraPrimary)
                        }

                        // Botón Compartir
                        IconButton(
                            onClick = { /* Compartir */ },
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.8f))
                                .size(40.dp)
                        ) {
                            Icon(Icons.Default.Share, contentDescription = "Compartir", tint = SyncraPrimary)
                        }
                    }
                }

                // 2. CONTENIDO PRINCIPAL
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // Título y Ubicación
                    Text(
                        text = "Apartamento Vista Hermosa",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = SyncraPrimary
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.LocationOn, contentDescription = "Ubicación", tint = Color.Gray, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Zona 15, Ciudad de Guatemala", fontSize = 14.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Precio y Etiqueta de Renta
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "$1,200 / mes",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorVisitaHoy
                        )

                        // Etiqueta Renta
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(ColorVisitaHoy.copy(alpha = 0.2f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(text = "Renta", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = ColorVisitaHoy)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = SurfaceGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 3. CARACTERÍSTICAS (Habitaciones, Baños, Parqueos)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        /* Nota: Si quieres usar tus propios iconos, cambia el 'icon' por un 'painterResource(id = R.drawable.tu_icono)'
                           y ajusta el composable FeatureItem de abajo. */
                        FeatureItem(title = "Habitaciones", value = "3")
                        FeatureItem(title = "Baños", value = "2")
                        FeatureItem(title = "Parqueos", value = "2")
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = SurfaceGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 4. DESCRIPCIÓN
                    Text(
                        text = "Descripción de la propiedad",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = SyncraPrimary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Hermoso apartamento moderno ubicado en el corazón de Zona 15. Cuenta con excelente iluminación natural, acabados de lujo, y está rodeado de naturaleza. Ideal para familias o profesionales que buscan comodidad y seguridad.",
                        fontSize = 14.sp,
                        color = Color.DarkGray,
                        lineHeight = 22.sp
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // 5. AMENIDADES
                    Text(
                        text = "Amenidades",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = SyncraPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        AmenityItem(text = "Gimnasio equipado")
                        AmenityItem(text = "Piscina climatizada")
                        AmenityItem(text = "Seguridad 24/7")
                        AmenityItem(text = "Área de coworking")
                    }

                    Spacer(modifier = Modifier.height(32.dp)) // Espacio extra al final para que no lo tape el botón flotante
                }
            }
        }
    ) // <-- Cierre del Scaffold
}

// Sub-componentes para mantener el código ordenado

@Composable
fun FeatureItem(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceGray)
            .padding(16.dp)
            .width(80.dp) // Ancho fijo para que se vean parejos
    ) {
        // Un círculo verde temporal en lugar de los iconos de camas/baños
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(ColorVisitaHoy.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = ColorVisitaHoy)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun AmenityItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = ColorVisitaHoy, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp, color = Color.DarkGray)
    }
}