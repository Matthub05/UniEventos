package com.example.unieventos.ui.screens.client.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.unieventos.R
import com.example.unieventos.models.Artist
import com.example.unieventos.models.Event
import com.example.unieventos.models.Ticket
import com.example.unieventos.models.User
import com.example.unieventos.ui.components.EventSaver
import com.example.unieventos.ui.components.TicketItem
import com.example.unieventos.ui.components.TopBarComponent
import com.example.unieventos.ui.components.UserSaver
import com.example.unieventos.utils.SharedPreferenceUtils
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.TicketViewModel
import com.example.unieventos.viewmodel.UsersViewModel

@Composable
fun ShoppingHistoryScreen(
    ticketViewModel: TicketViewModel,
    onNavigateToBack: () -> Unit,
    eventsViewModel: EventsViewModel,

) {

    val userId = SharedPreferenceUtils.getCurrentUser(LocalContext.current)?.id
    var tickets by rememberSaveable { mutableStateOf< List<Ticket> >(emptyList()) }

    LaunchedEffect(userId) {
        tickets = userId?.let { ticketViewModel.getUserHistoryById(it) }!!
    }

    Scaffold (
        topBar = {
            TopBarComponent(
                text = stringResource(id = R.string.lbl_compras),
                onClick = { onNavigateToBack() },
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(tickets) { ticket ->
                TicketItem(eventsViewModel = eventsViewModel, ticket = ticket)
            }
        }
    }

}