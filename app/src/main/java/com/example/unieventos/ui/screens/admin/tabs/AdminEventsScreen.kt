package com.example.unieventos.ui.screens.admin.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.models.EventItemDestination
import com.example.unieventos.ui.components.CouponItem
import com.example.unieventos.ui.components.EventItem
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel

@Composable
fun AdminEventsScreen(
    paddingValues: PaddingValues,
    eventsViewModel: EventsViewModel,
    artistViewModel: ArtistViewModel,
    onNavigateToEventDetail: (Int) -> Unit,
    onLogout: () -> Unit
) {

    val events = eventsViewModel.event.collectAsState().value

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
        items( events ) { event ->
            EventItem(
                event = event,
                modifier = Modifier.fillMaxWidth(),
                destination = EventItemDestination.DETAIL.name,
                onNavigateToEventDetail = onNavigateToEventDetail,
                artistViewModel = artistViewModel
            )
        }
    }

}