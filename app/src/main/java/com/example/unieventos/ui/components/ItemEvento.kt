package com.example.unieventos.ui.components

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unieventos.R
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventItemDestination

@Composable
fun ItemEvento(
    event: Event,
    color: Color = MaterialTheme.colorScheme.secondary,
    destination: String,
    onNavigateToEventDetail: (String) -> Unit = {},
    onNavigateToCreateEvent: () -> Unit = {}
) {

    if ( onNavigateToEventDetail == onNavigateToCreateEvent
        || EventItemDestination.entries.toTypedArray().none { it.name == destination } ) {
        throw Exception(stringResource(id = R.string.err_navegacion_invalida))
    }

    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        color = color,
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
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image (
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp),
                imageVector = Icons.Rounded.Image,
                contentDescription = "Imagen del usuario"
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(text = event.title, fontSize = 17.sp)
                Text(text = event.artist, fontSize = 13.sp)

                Spacer(modifier = Modifier.height(10.dp))

                Row (verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Rounded.AddCircleOutline, contentDescription = null)
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Fecha evento", fontSize = 11.sp) // Aun no se ha creado la fecha para eventos lol
                }
            }

        }

    }
}