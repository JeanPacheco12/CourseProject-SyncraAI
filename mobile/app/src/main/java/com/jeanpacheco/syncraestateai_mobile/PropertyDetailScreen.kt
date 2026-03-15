package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Variable de colores
private val IconBgGray = Color(0xFFF5F4F8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Scaffold(
        bottomBar = {
            // Cambiamos el Box por un Column para apilar el mensajito y el botón
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Mensaje de autorización de gerencia
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 12.dp)
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Autorizado",
                        tint = ColorVisitaHoy,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Edición autorizada por gerencia",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Botón CTA actualizado
                Button(
                    onClick = { /* Acción para ir a pantalla de edición */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(54.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SyncraPrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Editar propiedad", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .verticalScroll(scrollState)
            ) {
                // Fila superior de botones
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SurfaceGray)
                            .size(44.dp)
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar", tint = SyncraPrimary)
                    }

                    IconButton(
                        onClick = { /* Compartir */ },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(SurfaceGray)
                            .size(44.dp)
                    ) {
                        Icon(Icons.Default.Share, contentDescription = "Compartir", tint = SyncraPrimary)
                    }
                }

                // 1. HEADER: Imagen principal con galería
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.propiedad_agenda_1),
                        contentDescription = "Imagen de la propiedad",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(24.dp)),
                        contentScale = ContentScale.Crop
                    )

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color.Black.copy(alpha = 0.2f)))

                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(24.dp))
                                .background(SyncraPrimary)
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(text = "Apartamento", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
                        }

                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.propiedad_agenda_1),
                                contentDescription = "Cocina",
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Image(
                                painter = painterResource(id = R.drawable.propiedad_agenda_1),
                                contentDescription = "Baño",
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Black.copy(alpha = 0.6f))
                                    .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "+3", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                        }
                    }
                }

                // 2. CONTENIDO PRINCIPAL
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
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
                        Text(text = "Vista Hermosa, Zona 15", fontSize = 14.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

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

                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF8BC83F).copy(alpha = 0.2f))
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(text = "Renta", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8BC83F))
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = SurfaceGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 3. CARACTERÍSTICAS
                    val horizontalScrollState = rememberScrollState()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(horizontalScrollState),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        FeatureItem(title = "Habitaciones", value = "3", iconResId = R.drawable.ic_cama)
                        FeatureItem(title = "Baños", value = "2", iconResId = R.drawable.ic_bano)
                        FeatureItem(title = "Parqueos", value = "2", iconResId = R.drawable.ic_carro)
                        FeatureItem(title = "Metraje", value = "120m²", iconResId = R.drawable.ic_metraje)
                        FeatureItem(title = "Nivel", value = "4", iconResId = R.drawable.ic_nivel)
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
                        AmenityItem(text = "Parqueo de visitas")
                        AmenityItem(text = "Área de barbacoa (BBQ)")
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = SurfaceGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 6. UBICACIÓN
                    Text(
                        text = "Ubicación",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = SyncraPrimary
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(IconBgGray),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.LocationOn,
                                contentDescription = "Pin de ubicación",
                                tint = SyncraPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Bulevar Vista Hermosa II, Zona 15",
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Medium
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { /* Acción para abrir Google Maps */ },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.fondo_mapa),
                            contentDescription = "Mapa de ubicación",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Ver mapa completo",
                            fontSize = 12.sp,
                            color = SyncraPrimary,
                            fontWeight = FontWeight.SemiBold,
                            textDecoration = TextDecoration.Underline
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = SurfaceGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 7. MANTENIMIENTO
                    Text(
                        text = "Mantenimiento",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = SyncraPrimary
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceGray)
                            .padding(16.dp)
                    ) {
                        Column {
                            Text(
                                text = "Q. 800 / mes",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.DarkGray
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "*Cuota estimada que incluye seguridad, agua y extracción de basura.",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                lineHeight = 20.sp
                            )
                        }
                    }
                }
            }
        }
    )
}

// Sub-componentes
@Composable
fun FeatureItem(title: String, value: String, iconResId: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceGray)
            .padding(vertical = 16.dp, horizontal = 20.dp)
            .widthIn(min = 70.dp)
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(0xFF8BC83F).copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = title,
                tint = Color(0xFF8BC83F),
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)

        Spacer(modifier = Modifier.height(4.dp))

        Text(text = title, fontSize = 12.sp, color = Color.Gray, maxLines = 1)
    }
}

@Composable
fun AmenityItem(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF8BC83F), modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp, color = Color.DarkGray)
    }
}