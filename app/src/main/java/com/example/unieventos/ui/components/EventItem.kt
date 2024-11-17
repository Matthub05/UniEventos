package com.example.unieventos.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.unieventos.R
import com.example.unieventos.models.Artist
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventItemDestination
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import java.time.format.DateTimeFormatter
import java.time.ZoneId

@Composable
fun EventItem(
    modifier: Modifier,
    eventId: String,
    userId: String,
    destination: String,
    artistViewModel: ArtistViewModel,
    eventViewModel: EventsViewModel,
    onNavigateToEventDetail: (String, String) -> Unit,
    onNavigateToCreateEvent: (String) -> Unit = {}
) {

    var event by rememberSaveable(stateSaver = EventSaver) {
        mutableStateOf(Event())
    }
    var artist by remember { mutableStateOf<Artist?>(null) }
    var artistName by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(eventId) {

        event = eventViewModel.getEventById(eventId)!!
        event?.let { loadedEvent ->
            event = loadedEvent
        }
        if (!event.artistId.isNullOrEmpty()) {
            artist = artistViewModel.getArtistById(event.artistId)
            artist?.let { loadedArtist ->
                artistName = loadedArtist.name
            }
        }
    }

    if ( onNavigateToEventDetail == onNavigateToCreateEvent
        || EventItemDestination.entries.toTypedArray().none { it.name == destination } ) {
        throw Exception(stringResource(id = R.string.err_navegacion_invalida))
    }

    Surface (
        modifier = modifier,
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {

        Row (
            modifier = Modifier
                .padding(10.dp)
                .clickable  (
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ){
                    if (destination == EventItemDestination.DETAIL.name) {
                        onNavigateToEventDetail(event.id, userId)
                    } else if (destination == EventItemDestination.CREATE.name) {
                        onNavigateToCreateEvent(event.id)
                    }
                },

        ) {

            val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm")
            val model = ImageRequest.Builder(LocalContext.current)
                .data(event.imageUrl)
                .crossfade(true)
                .build()

            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(8))
                    .size(120.dp),
                model = model,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = event.title,
                    fontSize = 17.sp,
                )
                Text(text = artistName ?: "",
                    fontSize = 13.sp,
                    color = Color.Gray)

                Spacer(modifier = Modifier.height(30.dp))

                Text(text = event.eventSite.name + ", " + event.eventSite.location,
                    fontSize = 11.sp,
                    color = Color.Gray)

                    Text(text = event.date.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime().format(formatter),
                        fontSize = 11.sp,
                        color = Color.Gray)

            }

        }

    }
}