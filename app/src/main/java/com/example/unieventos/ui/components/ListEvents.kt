package com.example.unieventos.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.unieventos.models.Event

@Composable
fun ListEvents(
    modifier: Modifier = Modifier,
    events: List<Event>,
    paddingValues: PaddingValues,
    destination: String,
    onNavigateToEventDetail: (String) -> Unit = {},
    onNavigateToCreateEvent: () -> Unit = {}
) {

    LazyColumn (
        modifier = modifier,
    ) {

        items( events ) { event ->
            ItemEvento(
                event = event,
                destination = destination,
                onNavigateToEventDetail = onNavigateToEventDetail,
                onNavigateToCreateEvent = onNavigateToCreateEvent
            )
        }

    }
}