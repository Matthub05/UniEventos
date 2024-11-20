package com.example.unieventos.ui.screens.client.tabs

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventItemDestination
import com.example.unieventos.models.Ticket
import com.example.unieventos.ui.components.EventItem
import com.example.unieventos.ui.components.EventSaver
import com.example.unieventos.ui.components.FavoriteArtistsSection
import com.example.unieventos.ui.components.FeaturedSection
import com.example.unieventos.ui.components.SavedSection
import com.example.unieventos.ui.components.SectionTitle
import com.example.unieventos.utils.SharedPreferenceUtils
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.UsersViewModel

@Composable
fun EventsScreen(
    userId: String,
    paddingValues: PaddingValues,
    eventsViewModel: EventsViewModel,
    artistViewModel: ArtistViewModel,
    usersViewModel: UsersViewModel,
    onNavigateToEventDetail: (String, String) -> Unit,
    onNavigateToArtistDetail: (String) -> Unit
) {
    val events by eventsViewModel.event.collectAsState()
    val eventIds = events.map { it.id }
    val artists = artistViewModel.artist.collectAsState().value

    val isArtistsLoading by artistViewModel.isLoading.collectAsState()
    val isEventsLoading by eventsViewModel.isLoading.collectAsState()
    val isLoading = isEventsLoading || isArtistsLoading

    if (isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 7.dp,
                    end = 7.dp,
                    top = 90.dp,
                    bottom = paddingValues.calculateBottomPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            item {
                SectionTitle(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.label_artistas_destacados),
                    onSeeAllClick = {
                    }
                )
            }

            item {
                FavoriteArtistsSection(
                    modifier = Modifier
                        .fillMaxWidth(),
                    artists = artists,
                    onNavigateToArtistDetail = onNavigateToArtistDetail
                )
            }

            item {
                SectionTitle(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.label_eventos_destacados),
                    onSeeAllClick = {}
                )
            }

            item {

                FeaturedSection(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onSeeAllClick = {},
                    onNavigateToEventDetail = onNavigateToEventDetail,
                    artistViewModel = artistViewModel,
                    events = events,
                    userId = userId
                )
            }

            item {
                SectionTitle(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.label_vistos_anteriormente),
                    onSeeAllClick = {}
                )
            }

            item {
                SavedSection(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onSeeAllClick = {},
                    eventsViewModel = eventsViewModel,
                    usersViewModel = usersViewModel,
                    onNavigateToEventDetail = onNavigateToEventDetail,
                    userId = userId
                )
            }

            item {
                SectionTitle(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(id = R.string.lbl_total_events),
                    onSeeAllClick = {}
                )
            }

            items(eventIds) { eventId ->
                EventItem(
                    eventId = eventId,
                    modifier = Modifier.fillMaxWidth(),
                    destination = EventItemDestination.DETAIL.name,
                    onNavigateToEventDetail = onNavigateToEventDetail,
                    artistViewModel = artistViewModel,
                    eventViewModel = eventsViewModel,
                    userId = userId
                )
            }

        }
    }
}