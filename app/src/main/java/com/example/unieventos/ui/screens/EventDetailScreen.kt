package com.example.unieventos.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.unieventos.R
import com.example.unieventos.models.Event
import com.example.unieventos.ui.components.TopBarComponent
import com.example.unieventos.viewmodel.EventsViewModel

@Composable
fun EventDetailScreen(
    eventId: String,
    eventsViewModel: EventsViewModel,
    onNavigateToHome: () -> Unit
){

    val event = eventsViewModel.getEventById(eventId)
    requireNotNull(event)

    Scaffold (
        topBar = {
            TopBarComponent(
                text = event.title,
                onClick = { onNavigateToHome() },
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    ) { paddingValues ->
        EventDetailForm(paddingValues, event)
    }
}
@Composable
fun EventDetailForm(paddingValues: PaddingValues, event: Event) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = event.description,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}