package com.example.unieventos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.models.BottomNavigationItem
import com.example.unieventos.models.EventItemDestination
import com.example.unieventos.ui.components.FavoriteArtistsSection
import com.example.unieventos.ui.components.FeaturedSection
import com.example.unieventos.ui.components.ListEvents
import com.example.unieventos.ui.components.NavigationBarCustom
import com.example.unieventos.ui.components.SearchBarTop
import com.example.unieventos.ui.components.SectionTitle
import com.example.unieventos.viewmodel.EventsViewModel

@Composable
fun HomeScreen(
    eventsViewModel: EventsViewModel,
    onNavigateToEventDetail: (String) -> Unit
) {

    val events = eventsViewModel.event.collectAsState()

    Scaffold (
        topBar = {
            SearchBarTop(
                onSearchTextChanged = { newText ->
                    println("Search text: $newText")
                }
            )
        },
        bottomBar = {
            NavigationBarCustom(
                items = listOf(
                    BottomNavigationItem(
                        title = stringResource(id = R.string.nav_Eventos),
                        selectedIcon = Icons.Filled.Star,
                        unselectedIcon = Icons.Outlined.StarOutline,
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = stringResource(id = R.string.nav_compras),
                        selectedIcon = Icons.Filled.ShoppingCart,
                        unselectedIcon = Icons.Outlined.ShoppingCart,
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = stringResource(id = R.string.nav_perfil),
                        selectedIcon = Icons.Filled.AccountCircle,
                        unselectedIcon = Icons.Outlined.AccountCircle,
                        hasNews = false,
                    ),
                )
            )
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 8.dp,
                    end = 2.dp,
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
        ) {

            SectionTitle(
                modifier = Modifier.fillMaxWidth(),
                title =  stringResource(id = R.string.label_artistas_favoritos)
            ) {

            }

            FavoriteArtistsSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(800.dp)
            ) {

            }

            SectionTitle(
                modifier = Modifier.fillMaxWidth(),
                title =  stringResource(id = R.string.label_eventos_destacados)
            ) {

            }

            FeaturedSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(800.dp)// Adjust this value to extend outside the Column,
            ) { }

            SectionTitle(
                modifier = Modifier.fillMaxWidth(),
                title =  stringResource(id = R.string.label_vistos_anteriormente)
            ) {

            }

            ListEvents(
                modifier = Modifier.fillMaxWidth(),
                events = events.value,
                paddingValues = paddingValues,
                destination = EventItemDestination.DETAIL.name,
                onNavigateToEventDetail = onNavigateToEventDetail
            )
        }


    }

}
