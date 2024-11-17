package com.example.unieventos.ui.screens.client

import com.example.unieventos.ui.components.ImageViewer
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.unieventos.R
import com.example.unieventos.models.Artist
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventLocation
import com.example.unieventos.models.EventSite
import com.example.unieventos.ui.components.ArtistSaver
import com.example.unieventos.ui.components.EventSaver
import com.example.unieventos.ui.components.MediaSection
import com.example.unieventos.ui.components.SleekButton
import com.example.unieventos.ui.components.TransparentTopBarComponent
import com.example.unieventos.utils.Formatters
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.UsersViewModel
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun EventDetailScreen(
    eventId: String,
    userId: String,
    eventsViewModel: EventsViewModel,
    artistViewModel: ArtistViewModel,
    usersViewModel: UsersViewModel,
    onNavigateToUserHome: () -> Unit,
    onNavigateToTransaction: (String, String) -> Unit
) {

    var showImagePreview by rememberSaveable { mutableStateOf(false) }
    var previewImage by rememberSaveable { mutableStateOf("") }

    var event by rememberSaveable(stateSaver = EventSaver) {
        mutableStateOf(Event())
    }
    var artistName by rememberSaveable { mutableStateOf("") }

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var idArtist by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf(Date()) }
    var name by rememberSaveable { mutableStateOf("") }
    var capacity by rememberSaveable { mutableStateOf("") }
    var locationName by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    var mediaUrls by rememberSaveable { mutableStateOf( listOf<String>() ) }
    var eventLocations by remember { mutableStateOf( listOf<EventLocation>() ) }

    var labelCapacity = stringResource(id = R.string.lbl_slots)

    val handlePictureClick: (String) -> Unit = { picture ->
        previewImage = picture
        showImagePreview = true
    }

    val formatterDate = SimpleDateFormat("MMMM d", Locale.getDefault())
    val formatterTime = SimpleDateFormat("h:mm a", Locale.getDefault())

    var isLoading by remember { mutableStateOf(false) }


    LaunchedEffect(eventId) {
        if (!eventId.isNullOrEmpty()) {

            usersViewModel.saveVisitedHistory(eventId, userId)

            try {
                isLoading = true
                event = eventsViewModel.getEventById(eventId)!!
                event?.let { loadedEvent ->
                    title = loadedEvent.title
                    description = loadedEvent.description
                    idArtist = loadedEvent.artistId
                    category = loadedEvent.category
                    date = loadedEvent.date
                    name = loadedEvent.eventSite.name
                    capacity =
                        Formatters.formatNumber(loadedEvent.eventSite.capacity.toString().toInt())
                    locationName = loadedEvent.eventSite.name
                    location = loadedEvent.eventSite.location
                    imageUrl = loadedEvent.imageUrl
                    mediaUrls = loadedEvent.mediaUrls
                    eventLocations = loadedEvent.locations
                    artistName =
                        artistViewModel.getArtistById(loadedEvent.artistId)?.name.toString()
                }
            }finally{
                isLoading = false

            }
        }
    }

            Scaffold(
                topBar = {
                    TransparentTopBarComponent(
                        text = "",
                        onClick = { onNavigateToUserHome() },
                        icon = {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    ) {
                    }
                },
                floatingActionButton = {
                    Column {

                        FloatingActionButton(
                            containerColor = Color.White,
                            modifier = Modifier
                                .padding(end = 15.dp, bottom = 13.dp)
                                .align(Alignment.End),
                            onClick = { onNavigateToTransaction(eventId, userId) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = null
                            )
                        }
                    }
                }

            ) {


                when {
                    isLoading -> {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }

                    else -> {
                        LazyColumn {

                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(350.dp)
                                        .padding()
                                ) {

                                    val model = ImageRequest.Builder(LocalContext.current)
                                        .data(imageUrl)
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
                                            text = title,
                                            style = MaterialTheme.typography.headlineLarge,
                                            color = Color.White,
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 35.sp
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))
                                        Text(
                                            text = artistName,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Gray,
                                            fontSize = 18.sp
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

                                    Spacer(modifier = Modifier.size(3.dp))

                                    InfoTag(
                                        icon = Icons.Filled.LocationOn,
                                        labelText = "$locationName, $location"
                                    )

                                    Spacer(modifier = Modifier.size(5.dp))

                                    InfoTag(
                                        icon = Icons.Filled.AccessTime,
                                        labelText = formatterTime.format(date)
                                    )

                                    Spacer(modifier = Modifier.size(5.dp))

                                    InfoTag(
                                        icon = Icons.Filled.DateRange,
                                        labelText = formatterDate.format(date)
                                    )

                                    Spacer(modifier = Modifier.size(5.dp))

                                    InfoTag(
                                        icon = Icons.Filled.Person,
                                        labelText = "$labelCapacity $capacity"
                                    )

                                    Spacer(modifier = Modifier.size(15.dp))

                                    Text(
                                        text = stringResource(id = R.string.text_multimedia),
                                        fontSize = 23.sp,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.size(15.dp))

                                    MediaSection(
                                        modifier = Modifier,
                                        imageUrls = mediaUrls,
                                        onClick = handlePictureClick
                                    )

                                    Spacer(modifier = Modifier.size(20.dp))

                                    Text(
                                        text = stringResource(id = R.string.label_detalles),
                                        fontSize = 23.sp,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Spacer(modifier = Modifier.size(10.dp))

                                    Text(
                                        text = description,
                                        fontSize = 15.sp,
                                        color = Color.LightGray,
                                    )

                                    Spacer(modifier = Modifier.height(100.dp))

                                }
                            }
                        }

                        if (showImagePreview) {
                            ImageViewer(
                                imageUrl = previewImage,
                                onDismiss = {
                                    showImagePreview = false
                                }
                            )

                        }
                    }
                    }

                }
            }

@Composable
fun InfoTag(
    icon: ImageVector,
    labelText: String
){
    Row {

        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.size(4.dp))


        Text(text = labelText)
    }
}



