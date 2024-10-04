package com.example.unieventos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.example.unieventos.R
import com.example.unieventos.models.BottomNavigationItem
import com.example.unieventos.models.EventItemDestination
import com.example.unieventos.ui.components.ListEvents
import com.example.unieventos.ui.components.NavigationBarCustom
import com.example.unieventos.viewmodel.EventsViewModel

@Composable
fun AdminHomeScreen(
    eventsViewModel: EventsViewModel,
    onNavigateToProfileEdit: () -> Unit,
    onNavigateToCreateEvent: () -> Unit,
    onNavigateToCreateCoupon: () -> Unit
) {

    val events = eventsViewModel.event.collectAsState()

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToCreateEvent() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
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
                        title = stringResource(id = R.string.nav_cupones),
                        selectedIcon = Icons.Filled.Stars,
                        unselectedIcon = Icons.Outlined.Stars,
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
    ) { paddingValues ->

        Column {
            Text(text = "")
            ListEvents(
                events = events.value,
                paddingValues = paddingValues,
                destination = EventItemDestination.CREATE.name,
                onNavigateToCreateEvent = onNavigateToCreateEvent
            )

            Button(onClick = { onNavigateToProfileEdit() }) {
                Text(text = stringResource(id = R.string.btn_editar_perfil))
            }
            
            Button(onClick = { onNavigateToCreateCoupon() }) {
                Text(text = stringResource(id = R.string.btn_registrar_cupon))
            }
        }

    }
}
