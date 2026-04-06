package com.jeanpacheco.syncraestateai_mobile

import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import coil.compose.AsyncImage

private val IconBgGray = Color(0xFFF5F4F8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailScreen(navController: NavController, propertyId: String) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // --- ESTADOS DINÁMICOS PARA FIREBASE ---
    var propertyTitle by remember { mutableStateOf("Cargando...") }
    var propertyLocation by remember { mutableStateOf("") }
    var propertyPrice by remember { mutableStateOf(0L) }
    var propertyType by remember { mutableStateOf("") }

    var propertyBedrooms by remember { mutableStateOf("0") }
    var propertyBathrooms by remember { mutableStateOf("0") }
    var propertyParking by remember { mutableStateOf("0") }
    var propertyDescription by remember { mutableStateOf("Cargando descripción...") }
    var propertyImageUrl by remember { mutableStateOf("") }

    // --> LOS 4 CAMPOS NUEVOS <--
    var propertyMetraje by remember { mutableStateOf("0") }
    var propertyNivel by remember { mutableStateOf("0") }
    var propertyMantenimiento by remember { mutableStateOf(0L) }
    var propertyAmenidades by remember { mutableStateOf(listOf<String>()) }

    var isLoading by remember { mutableStateOf(true) }

    // Guardaremos la lista completa de links
    var galleryImages by remember { mutableStateOf<List<String>>(emptyList()) }
    // Recordaremos cuál link está seleccionado para la foto grande
    var selectedLargeImageUrl by remember { mutableStateOf("") }

    // --- DESCARGAMOS LA PROPIEDAD ESPECÍFICA ---
    LaunchedEffect(propertyId) {
        if (propertyId.isNotEmpty()) {
            val db = FirebaseFirestore.getInstance()
            db.collection("properties").document(propertyId).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        propertyTitle = document.getString("title") ?: "Sin título"
                        propertyLocation = document.getString("location") ?: "Ubicación desconocida"
                        propertyPrice = document.getLong("price") ?: 0L
                        propertyType = document.getString("type") ?: "Propiedad"

                        propertyBedrooms = document.getLong("habitaciones")?.toString() ?: "0"
                        propertyBathrooms = document.getLong("banos")?.toString() ?: "0"
                        propertyParking = document.getLong("parqueos")?.toString() ?: "0"
                        propertyDescription = document.getString("descripcion") ?: "Sin descripción disponible."

                        // --> AQUÍ ESTÁ EL CAMBIO DE LA GALERÍA <--
                        // 1. Descargamos la lista de imágenes
                        @Suppress("UNCHECKED_CAST")
                        galleryImages = document.get("imageGalleryUrlList") as? List<String> ?: emptyList()

                        // 2. Si hay fotos, ponemos la primera como la imagen grande inicial
                        if (galleryImages.isNotEmpty()) {
                            selectedLargeImageUrl = galleryImages[0]
                        }

                        // --> DESCARGAMOS LOS NUEVOS CAMPOS <--
                        propertyMetraje = document.getLong("metraje")?.toString() ?: "0"
                        propertyNivel = document.getLong("nivel")?.toString() ?: "0"
                        propertyMantenimiento = document.getLong("mantenimiento") ?: 0L

                        // Rescatamos la lista de amenidades de Firebase
                        @Suppress("UNCHECKED_CAST")
                        propertyAmenidades = document.get("amenidades") as? List<String> ?: emptyList()
                    }
                    isLoading = false
                }
                .addOnFailureListener {
                    propertyTitle = "Error al cargar"
                    isLoading = false
                }
        } else {
            isLoading = false
        }
    }

    Scaffold(
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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

                Button(
                    // ---> CAMBIAMOS ESTO <---
                    onClick = { navController.navigate("edit_property/$propertyId") },
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
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = SyncraPrimary)
                }
            } else {
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
                            onClick = {
                                val textoACompartir = "¡Mira esta increíble propiedad: $propertyTitle ubicada en $propertyLocation por Q.$propertyPrice en Syncra Estate AI!"
                                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                                    putExtra(Intent.EXTRA_TEXT, textoACompartir)
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, "Compartir propiedad")
                                context.startActivity(shareIntent)
                            },
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
                        // IMAGEN PRINCIPAL (Dinámica)
                        AsyncImage(
                            model = selectedLargeImageUrl,
                            contentDescription = "Imagen principal",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(24.dp)),
                            contentScale = ContentScale.Crop,
                            placeholder = painterResource(id = R.drawable.propiedad_agenda_1),
                            error = painterResource(id = R.drawable.propiedad_agenda_1)
                        )

                        // FILTRO OSCURO
                        Box(modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.Black.copy(alpha = 0.2f)))

                        // CONTENIDO INFERIOR (Etiqueta + Miniaturas)
                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Bottom
                        ) {
                            // ETIQUETA DEL TIPO DE PROPIEDAD
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(SyncraPrimary)
                                    .padding(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Text(text = propertyType, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Color.White)
                            }

                            // ---> NUEVA COLUMNA DE MINIATURAS DINÁMICA <---
                            Column(
                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                // Recorremos máximo las primeras 3 imágenes de la lista
                                galleryImages.take(3).forEach { imageUrl ->
                                    val isSelected = imageUrl == selectedLargeImageUrl

                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = "Miniatura",
                                        modifier = Modifier
                                            .size(45.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            // Borde amarillo si está seleccionada, blanco si no
                                            .border(
                                                width = if (isSelected) 2.dp else 1.dp,
                                                color = if (isSelected) ColorVisitaHoy else Color.White,
                                                shape = RoundedCornerShape(8.dp)
                                            )
                                            // Aquí ocurre la magia al hacer clic
                                            .clickable { selectedLargeImageUrl = imageUrl },
                                        contentScale = ContentScale.Crop,
                                        placeholder = painterResource(id = R.drawable.propiedad_agenda_1),
                                        error = painterResource(id = R.drawable.propiedad_agenda_1)
                                    )
                                }

                                // Si hay más de 3 fotos, mostramos el contador restante
                                if (galleryImages.size > 3) {
                                    Box(
                                        modifier = Modifier
                                            .size(45.dp)
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(Color.Black.copy(alpha = 0.6f))
                                            .border(1.dp, Color.White, RoundedCornerShape(8.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "+${galleryImages.size - 3}",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                    }
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
                            text = propertyTitle,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = SyncraPrimary
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocationOn, contentDescription = "Ubicación", tint = Color.Gray, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = propertyLocation, fontSize = 14.sp, color = Color.Gray)
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val precioConComas = java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(propertyPrice)
                            Text(
                                text = "Q. $precioConComas",
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
                                Text(text = "Renta/Venta", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF8BC83F))
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
                            FeatureItem(title = "Habitaciones", value = propertyBedrooms, iconResId = R.drawable.ic_cama)
                            FeatureItem(title = "Baños", value = propertyBathrooms, iconResId = R.drawable.ic_bano)
                            FeatureItem(title = "Parqueos", value = propertyParking, iconResId = R.drawable.ic_carro)

                            // --> AHORA METRAJE Y NIVEL SON DINÁMICOS <--
                            FeatureItem(title = "Metraje", value = "${propertyMetraje}m²", iconResId = R.drawable.ic_metraje)
                            FeatureItem(title = "Nivel", value = propertyNivel, iconResId = R.drawable.ic_nivel)
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
                            text = propertyDescription,
                            fontSize = 14.sp,
                            color = Color.DarkGray,
                            lineHeight = 22.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // 5. AMENIDADES (AHORA DINÁMICAS)
                        Text(
                            text = "Amenidades",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = SyncraPrimary
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            if (propertyAmenidades.isEmpty()) {
                                Text("No hay amenidades registradas.", fontSize = 14.sp, color = Color.Gray)
                            } else {
                                propertyAmenidades.forEach { amenidad ->
                                    AmenityItem(text = amenidad)
                                }
                            }
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
                                text = propertyLocation,
                                fontSize = 14.sp,
                                color = Color.DarkGray,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    val uri = Uri.parse("geo:0,0?q=${Uri.encode(propertyLocation)}")
                                    val mapIntent = Intent(Intent.ACTION_VIEW, uri)

                                    try {
                                        mapIntent.setPackage("com.google.android.apps.maps")
                                        context.startActivity(mapIntent)
                                    } catch (e: Exception) {
                                        val fallbackIntent = Intent(Intent.ACTION_VIEW, uri)
                                        context.startActivity(fallbackIntent)
                                    }
                                },
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

                        // 7. MANTENIMIENTO (AHORA DINÁMICO)
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
                                val mantenimientoConComas = java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(propertyMantenimiento)
                                Text(
                                    text = "Q. $mantenimientoConComas / mes",
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