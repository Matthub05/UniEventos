package com.example.unieventos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unieventos.R
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventItemDestination

@Composable
fun ItemEvento(
    modifier: Modifier,
    event: Event,
    destination: String,
    onNavigateToEventDetail: (String) -> Unit = {},
    onNavigateToCreateEvent: () -> Unit = {}
) {

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
                .clickable {
                    if (destination == EventItemDestination.DETAIL.name) {
                        onNavigateToEventDetail(event.id)
                    } else if (destination == EventItemDestination.CREATE.name) {
                        onNavigateToCreateEvent()
                    }
                },

        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8))
                    .background(Color.LightGray)
            ) {
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    text = event.title,
                    fontSize = 17.sp,
                )
                Text(text = event.artist,
                    fontSize = 13.sp,
                    color = Color.Gray)

                Spacer(modifier = Modifier.height(45.dp))

                Row (verticalAlignment = Alignment.CenterVertically) {
                    IconButton( onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Rounded.AddCircleOutline, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.width(5.dp))

                    Text(text = "Fecha evento",
                        fontSize = 11.sp,
                        color = Color.Gray) // Aun no se ha creado la fecha para eventos lol
                }
            }

        }

    }
}