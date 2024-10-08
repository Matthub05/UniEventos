package com.example.unieventos.ui.screens.client.tabs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.models.EventItemDestination
import com.example.unieventos.ui.components.FavoriteArtistsSection
import com.example.unieventos.ui.components.FeaturedSection
import com.example.unieventos.ui.components.ItemEvento
import com.example.unieventos.ui.components.SavedSection
import com.example.unieventos.ui.components.SectionTitle
import com.example.unieventos.viewmodel.EventsViewModel

@Composable
fun EventsScreen(
    paddingValues: PaddingValues,
    eventsViewModel: EventsViewModel,
    onNavigateToEventDetail: (String) -> Unit,
) {

    val events = eventsViewModel.event.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 8.dp,
                end = 2.dp,
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            ),
    ) {
        item {
            SectionTitle(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(id = R.string.label_artistas_favoritos),
                onSeeAllClick = { }
            )
        }

        item {
            FavoriteArtistsSection(
                modifier = Modifier
                    .fillMaxWidth(),
                onSeeAllClick = {}
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
                onSeeAllClick = {}
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
            ItemEvento(
                event = event,
                modifier = Modifier.fillMaxWidth(),
                destination = EventItemDestination.DETAIL.name,
                onNavigateToEventDetail = onNavigateToEventDetail
            )
        }

    }
}