package com.example.unieventos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.unieventos.models.Event
import com.example.unieventos.models.User
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.UsersViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedSection(
    userId: String,
    usersViewModel: UsersViewModel,
    eventsViewModel: EventsViewModel,
    modifier: Modifier,
    onSeeAllClick: () -> Unit,
    onNavigateToEventDetail: (String, String) -> Unit,
) {

    var user by rememberSaveable(stateSaver = UserSaver) {
        mutableStateOf(User())
    }
    var eventIds by rememberSaveable { mutableStateOf( listOf<String>() ) }

    LaunchedEffect(userId) {
        if (!userId.isNullOrEmpty()) {
            user = usersViewModel.getUserById(userId)!!
            user?.let { loadedUser ->
                eventIds = loadedUser.visitHistory
            }
        }
    }

    LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        items( eventIds ) { eventId ->
                SavedEvent(
                    eventId = eventId,
                    eventsViewModel = eventsViewModel,
                    onNavigateToEventDetail = onNavigateToEventDetail,
                    userId = userId)
            }
        }
    }


@Composable
fun SavedEvent(
    eventId: String,
    eventsViewModel: EventsViewModel,
    onNavigateToEventDetail: (String, String) -> Unit,
    userId: String
) {

    var event by rememberSaveable(stateSaver = EventSaver) {
        mutableStateOf(Event())
    }

    var mediaUrl by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }


    LaunchedEffect(eventId) {
        if (!eventId.isNullOrEmpty()) {
            event = eventsViewModel.getEventById(eventId)!!
            event?.let { loadedEvent ->
                mediaUrl = loadedEvent.imageUrl
                title = loadedEvent.title
            }
        }
    }

    Column(
        modifier = Modifier
            .clickable (
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ){
                onNavigateToEventDetail(event.id, userId)
            }
    ) {
        val model = ImageRequest.Builder(LocalContext.current)
            .data(mediaUrl)
            .crossfade(true)
            .build()

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8))
                .size(90.dp),
            model = model,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(5.dp))

        Column (
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Left,
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}