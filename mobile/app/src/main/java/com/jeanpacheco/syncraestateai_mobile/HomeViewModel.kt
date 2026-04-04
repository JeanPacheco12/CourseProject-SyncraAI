package com.jeanpacheco.syncraestateai_mobile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    // El estado de las notificaciones vive aquí ahora
    var hasUnreadNotifications by mutableStateOf(true)

    var notificationsList by mutableStateOf(listOf(
        NotificationData(
            title = "Mensaje en Inglés",
            desc = "Darren Smith busca algo en Zona 10.",
            time = "10 min",
            icon = Icons.Default.Person,
            route = "client_profile/cli_8"
        ),
        NotificationData(
            title = "Nuevo Prospecto",
            desc = "Anderson Souza envió un mensaje.",
            time = "2h",
            icon = Icons.Default.Person,
            route = "client_profile/cli_1"
        ),
        NotificationData(
            title = "Cita confirmada",
            desc = "Revisión de Penthouse Cayalá.",
            time = "5h",
            icon = Icons.Default.DateRange,
            route = "property_detail/prop_4"
        )
    ))

    fun markAsRead() {
        hasUnreadNotifications = false
    }

    fun clearNotifications() {
        notificationsList = emptyList()
        hasUnreadNotifications = false
    }
}