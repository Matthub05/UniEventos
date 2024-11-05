package com.example.unieventos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.StickyNote2
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.unieventos.R
import com.example.unieventos.models.Event
import com.example.unieventos.models.Ticket
import com.example.unieventos.viewmodel.EventsViewModel

@Composable
fun TicketItem(
    ticket: Ticket,
    eventsViewModel: EventsViewModel
) {

    var event by rememberSaveable { mutableStateOf(Event()) }
    LaunchedEffect (ticket.eventId) {
        event = eventsViewModel.getEventById(ticket.eventId)!!
    }

    var showLocationDialog by remember { mutableStateOf(false) }

    Surface (
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(20.dp)
    ) {

        Row (
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clickable {
                    showLocationDialog = true
                },
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8))
                    .background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.StickyNote2,
                    contentDescription = null,
                    Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = event.title,
                    fontSize = 17.sp,
                )
                Text(text = (ticket.quantity).toString() + " " + stringResource(id = R.string.text_unidades),
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Text(text = stringResource(id = R.string.text_total) + ": " + ticket.price + " $",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

    }

    if (showLocationDialog) {
        DetailsDialog(
            ticket = ticket,
            eventsViewModel,
            onDismiss = { showLocationDialog = false }
        )
    }
}

@Composable
fun DetailsDialog(
    ticket: Ticket,
    eventsViewModel: EventsViewModel,
    onDismiss: () -> Unit
) {

    var event by rememberSaveable { mutableStateOf(Event()) }
    LaunchedEffect (ticket.eventId) {
        event = eventsViewModel.getEventById(ticket.eventId)!!
    }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f) // Adjust width as needed
                .wrapContentHeight(),
            shape = RoundedCornerShape(6.dp),
            color = Color(0xFF161616)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start // Align contents to the start
            ) {
                Text(
                    text = stringResource(id = R.string.btn_detalles_compra),
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            color = Color.Black,
                            shape = RoundedCornerShape(6.dp) // Less rounded corners
                        ) // Set the background color and shape here
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        event.let {
                            Image(
                                painter = rememberAsyncImagePainter(model = it.imageUrl),
                                contentDescription = it.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp)
                                    .padding(bottom = 8.dp)
                            )
                            Text(
                                text = it.title,
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }

                        Text(
                            text = stringResource(id = R.string.label_cantidad) + ": ${ticket.quantity}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = stringResource(id = R.string.text_precio) + ": $${ticket.price}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Normal),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }}
                SleekButton(text = stringResource(id = R.string.btn_ok)) {
                    onDismiss()
                }
            }
        }
    }

}