package com.example.unieventos.ui.screens.client.tabs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventItemDestination
import com.example.unieventos.ui.components.EventItem
import com.example.unieventos.ui.components.FavoriteArtistsSection
import com.example.unieventos.ui.components.FeaturedSection
import com.example.unieventos.ui.components.SavedSection
import com.example.unieventos.ui.components.SearchBarTop
import com.example.unieventos.ui.components.SectionTitle
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import kotlinx.coroutines.launch

@Composable
fun EventsScreen(
    paddingValues: PaddingValues,
    eventsViewModel: EventsViewModel,
    artistViewModel: ArtistViewModel,
    onNavigateToEventDetail: (Int) -> Unit,
    onNavigateToArtistDetail: (Int) -> Unit
) {

    val events = eventsViewModel.event.collectAsState().value
    val artists = artistViewModel.artist.collectAsState().value


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
                artistViewModel = artistViewModel,
                events = events,
            )
        }

        item {
            SectionTitle(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.label_guardados),
                onSeeAllClick = {}
            )
        }

        item {
            SavedSection(
                modifier = Modifier
                    .fillMaxWidth(),
                onSeeAllClick = {}
            )
        }

        item {
            SectionTitle(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.label_vistos_anteriormente),
                onSeeAllClick = {}
            )
        }

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