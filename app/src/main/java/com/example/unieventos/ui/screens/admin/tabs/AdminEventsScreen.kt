package com.example.unieventos.ui.screens.admin.tabs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unieventos.models.EventItemDestination
import com.example.unieventos.ui.components.EventItem
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel

@Composable
fun AdminEventsScreen(
    paddingValues: PaddingValues,
    eventsViewModel: EventsViewModel,
    artistViewModel: ArtistViewModel,
    onNavigateToCreateEvent: (String?) -> Unit,
) {

    val events = eventsViewModel.event.collectAsState().value
    val eventIds = events.map { it.id }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 7.dp,
                end = 7.dp,
                top = 90.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),
    ) {
        items( eventIds ) { eventId ->
            EventItem(
                eventId = eventId,
                modifier = Modifier.fillMaxWidth(),
                destination = EventItemDestination.CREATE.name,
                onNavigateToCreateEvent = onNavigateToCreateEvent,
                artistViewModel = artistViewModel,
                onNavigateToEventDetail =  { _: String, _: String -> },
                eventViewModel = eventsViewModel,
                userId = "0"
            )
        }
    }

}