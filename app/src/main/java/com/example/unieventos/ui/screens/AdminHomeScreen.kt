package com.example.unieventos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.unieventos.R
import com.example.unieventos.models.BottomNavigationItem
import com.example.unieventos.ui.components.NavigationBarCustom
import com.example.unieventos.viewmodel.EventsViewModel

@Composable
fun AdminHomeScreen(
    eventsViewModel: EventsViewModel,
    onLogout: () -> Unit,
    onNavigateToProfileEdit: () -> Unit,
    onNavigateToCreateEvent: () -> Unit,
    onNavigateToCreateCoupon: () -> Unit
) {

    // val events = eventsViewModel.event.collectAsState()

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
                    )
                )
            )
        }
    ) { paddingValues ->

        Column {
            Button(
                onClick = { onLogout() },
                modifier = Modifier.padding(paddingValues),
                colors = ButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                    disabledContentColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                Text(text = stringResource(id = R.string.btn_cerrar_sesion))
            }

            Button(onClick = { onNavigateToProfileEdit() }) {
                Text(text = stringResource(id = R.string.btn_editar_perfil),
                modifier = Modifier.padding(paddingValues))
            }
            
            Button(onClick = { onNavigateToCreateCoupon() }) {
                Text(text = stringResource(id = R.string.btn_registrar_cupon))
            }
        }

    }
}
