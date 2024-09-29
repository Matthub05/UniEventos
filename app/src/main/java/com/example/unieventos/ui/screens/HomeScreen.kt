package com.example.unieventos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.unieventos.R
import com.example.unieventos.models.Event
import com.example.unieventos.ui.components.ItemEvento
import com.example.unieventos.viewmodel.EventsViewModel

@Composable
fun HomeScreen(
    eventsViewModel: EventsViewModel,
    onNavigateToProfileEdit: () -> Unit,
    onNavigateToCreateEvent: () -> Unit,
    onNavigateToEventDetail: (String) -> Unit,
    onNavigateToCreateCoupon: () -> Unit
) {

    val events = eventsViewModel.event.collectAsState()

    Scaffold { paddingValues ->

        Column {
            ListEvents(
                events = events.value,
                paddingValues = paddingValues,
                onNavigateToEventDetail = onNavigateToEventDetail
            )

            Button(onClick = { onNavigateToProfileEdit() }) {
                Text(text = stringResource(id = R.string.btn_editar_perfil))
            }

            Button(onClick = { onNavigateToCreateEvent() }) {
                Text(text = stringResource(id = R.string.btn_crear_evento))
            }
            
            Button(onClick = { onNavigateToCreateCoupon() }) {
                Text(text = stringResource(id = R.string.btn_registrar_cupon))
            }
        }

    }
}

@Composable
fun ListEvents(
    events: List<Event>,
    paddingValues: PaddingValues,
    onNavigateToEventDetail: (String) -> Unit
) {
    LazyColumn (
        modifier = Modifier,
        contentPadding = paddingValues
    ) {

        items( events ) { event ->
            ItemEvento(
                event = event,
                onNavigateToEventDetail = onNavigateToEventDetail
            )
        }

    }
}