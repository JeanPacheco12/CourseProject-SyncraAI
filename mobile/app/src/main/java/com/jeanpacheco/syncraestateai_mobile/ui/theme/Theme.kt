package com.jeanpacheco.syncraestateai_mobile.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// 1. Configuramos el modo oscuro (Para que no marque error, usamos los colores Syncra)
private val DarkColorScheme = darkColorScheme(
    primary = SyncraGreen,
    secondary = BackgroundWhite,
    background = SyncraDarkBlue, // Si alguien usa modo oscuro, que el fondo sea el azul.
    surface = SyncraDarkBlue
)

// 2. Configuramos el modo claro (El que usararemos para los mockups)
private val LightColorScheme = lightColorScheme(
    primary = SyncraGreen,        // El color principal para botones
    secondary = SyncraDarkBlue,   // Para textos importantes y acentos
    background = BackgroundWhite, // El fondo de tu app
    surface = BackgroundWhite
)

@Composable
fun SyncraEstateAIMobileTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // IMPORTANTE: Lo pasamos a false para que Android no cambie los colores de Figma
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Asume que está el Type.kt intacto
        content = content
    )
}