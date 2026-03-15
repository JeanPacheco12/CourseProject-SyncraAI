package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
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
                // 1. HEADER: Imagen principal con botones y galería
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp) // Aumentamos un poco la altura para que respire más
                ) {
                    // Imagen de la propiedad
                    Image(
                        painter = painterResource(id = R.drawable.propiedad_agenda_1), // Reemplaza con la imagen real de la propiedad
                        contentDescription = "Imagen de la propiedad",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Overlay oscuro tenue para asegurar contraste de iconos (opcional)
                    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.2f)))

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

                    // Elementos inferiores sobre la imagen (Tipo y Galería)
                    Row(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        // Cuadro Tipo de Propiedad (Casa, Apto, etc.)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(24.dp))
                                .background(SyncraPrimary) // Color 234F68
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Text(text = "Apartamento", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
                        }

                        // Galería vertical a la derecha
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Miniatura 1
                            Image(
                                painter = painterResource(id = R.drawable.propiedad_agenda_1), // Idealmente otra imagen (ej: cocina)
                                contentDescription = "Cocina",
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            // Miniatura 2
                            Image(
                                painter = painterResource(id = R.drawable.propiedad_agenda_1), // Idealmente otra imagen (ej: baño)
                                contentDescription = "Baño",
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                            // Miniatura +3
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

                        // Etiqueta Renta Agrandada (Color 8BC83F es casi igual a ColorVisitaHoy)
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp)) // Bordes más redondeados
                                .background(Color(0xFF8BC83F).copy(alpha = 0.2f))
                                .padding(horizontal = 16.dp, vertical = 8.dp) // Padding aumentado
                        ) {
                            Text(text = "Renta", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8BC83F)) // Texto más grande y bold
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    HorizontalDivider(color = SurfaceGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(24.dp))

                    // 3. CARACTERÍSTICAS (Carrusel Horizontal)
                    val horizontalScrollState = rememberScrollState()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(horizontalScrollState), // Habilitamos el scroll horizontal
                        horizontalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre tarjetas
                    ) {
                        /* AQUÍ ESTÁN TUS ICONOS:
                           En un proyecto real, reemplazarías 'R.drawable.ic_cama' por tus propios iconos
                           exportados de Figma. Como no los tengo, dejo un espacio o uso iconos por defecto.
                        */
                        FeatureItem(title = "Habitaciones", value = "3", isIcon = false)
                        FeatureItem(title = "Baños", value = "2", isIcon = false)
                        FeatureItem(title = "Parqueos", value = "2", isIcon = false)
                        FeatureItem(title = "Metraje", value = "120m²", isIcon = false) // Ejemplo de tarjeta extra
                        FeatureItem(title = "Nivel", value = "4", isIcon = false)     // Ejemplo de tarjeta extra
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
                        AmenityItem(text = "Parqueo de visitas") // ¡Amenidad extra!
                        AmenityItem(text = "Área de barbacoa (BBQ)") // ¡Amenidad extra!
                    }

                    Spacer(modifier = Modifier.height(32.dp)) // Espacio extra al final para que no lo tape el botón flotante
                }
            }
        }
    ) // <-- Cierre del Scaffold
}

// Sub-componentes para mantener el código ordenado

@Composable
fun FeatureItem(title: String, value: String, isIcon: Boolean = false, iconResId: Int? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceGray)
            .padding(16.dp)
            .widthIn(min = 80.dp) // Ancho mínimo para que se adapte si el texto es largo
    ) {
        // Círculo contenedor
        Box(
            modifier = Modifier
                .size(40.dp) // Un poco más grande para los iconos
                .clip(CircleShape)
                .background(Color(0xFF8BC83F).copy(alpha = 0.2f)), // Usando el verde solicitado
            contentAlignment = Alignment.Center
        ) {
            if (isIcon && iconResId != null) {
                // Si tienes el ícono importado, se usaría esto:
                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = title,
                    tint = Color(0xFF8BC83F),
                    modifier = Modifier.size(24.dp)
                )
            } else {
                // Si no hay ícono, mostramos el valor en texto
                Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8BC83F))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, fontSize = 12.sp, color = Color.Gray, maxLines = 1)

        // Si usamos iconos, mostramos el valor debajo del titulo
        if (isIcon) {
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SyncraPrimary)
        }
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