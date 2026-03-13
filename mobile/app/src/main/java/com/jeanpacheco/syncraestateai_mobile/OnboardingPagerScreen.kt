package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun OnboardingPagerScreen(navController: NavController) {
    // Aquí se va a construir el carrusel de las 3 pestañas faltantes de Onboarding.
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "¡Aquí van las 3 pestañas deslizables!")
    }
}