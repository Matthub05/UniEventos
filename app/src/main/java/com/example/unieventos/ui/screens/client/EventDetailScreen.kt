package com.example.unieventos.ui.screens.client

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.unieventos.R
import com.example.unieventos.models.Event
import com.example.unieventos.ui.components.MediaSection
import com.example.unieventos.ui.components.SleekButton
import com.example.unieventos.ui.components.TransparentTopBarComponent
import com.example.unieventos.viewmodel.EventsViewModel
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EventDetailScreen(
    eventId: String,
    userId: String,
    eventsViewModel: EventsViewModel,
    onNavigateToUserHome: () -> Unit,
    onNavigateToTransaction: (String, String) -> Unit
) {

    var event by rememberSaveable { mutableStateOf(Event()) }
    LaunchedEffect (eventId) {
        event = eventsViewModel.getEventById(eventId)!!
    }

    val formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm")

    Scaffold (
        topBar = {
            TransparentTopBarComponent(
                text = "",
                onClick = { onNavigateToUserHome() }
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    ) {

        LazyColumn {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .padding()
                ) {

                    val model = ImageRequest.Builder(LocalContext.current)
                        .data(event.imageUrl)
                        .crossfade(true)
                        .build()

                    AsyncImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .size(180.dp),
                        model = model,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 1.8f)
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 270.dp, start = 20.dp),
                    ) {
                        Text(
                            text = event.title,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 35.sp
                        )
                        Spacer(modifier = Modifier.height(5.dp))
                        Text(
                            text = event.date.toInstant()
                                .atZone(ZoneId.systemDefault())
                                .toLocalDateTime().format(formatter),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            fontSize = 13.sp
                        )

                    }

                }
            }
            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 1.dp),
                ) {

                    Text(
                        text = event.description,
                        fontSize = 15.sp,
                        color = Color.Gray,
                    )

                    MediaSection(
                        modifier = Modifier,
                        event = event)

                    Spacer(modifier = Modifier.size(15.dp))

                    Row {

                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.size(4.dp))


                        Text(text = event.eventSite.name + ", " + event.eventSite.location)
                    }

                    Spacer(modifier = Modifier.size(5.dp))

                    Row {

                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.size(4.dp))


                        Text(text = stringResource(id = R.string.placeholder_artista))
                    }

                    Spacer(modifier = Modifier.size(15.dp))

                    SleekButton(
                        text = stringResource(id = R.string.btn_guardar_evento),
                        onClickAction = { onNavigateToTransaction(eventId, userId) }
                    )

                    Spacer(modifier = Modifier.size(15.dp))

                    SleekButton(
                        text = stringResource(id = R.string.btn_comprar_tiquete),
                        onClickAction = { onNavigateToTransaction(eventId, userId) }
                    )

                    Spacer(modifier = Modifier.size(15.dp))
                }
            }
       }
    }
}
