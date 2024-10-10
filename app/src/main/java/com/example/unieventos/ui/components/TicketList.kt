package com.example.unieventos.ui.components

import androidx.compose.foundation.clickable
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
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventItemDestination
import com.example.unieventos.models.Ticket
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import java.time.format.DateTimeFormatter
import java.time.ZoneId

@Composable
fun TicketItem(
    ticket: Ticket,
    modifier: Modifier,
    eventsViewModel: EventsViewModel
) {
    Surface (
        modifier = modifier,
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {

        Row (
            modifier = Modifier
                .padding(10.dp)
            ) {

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = ticket.price.toString(),
                    fontSize = 17.sp,
                )
                Text(
                    text = eventsViewModel.getEventById(ticket.eventId)!!.title,
                    fontSize = 17.sp,
                )

            }

        }

    }
}