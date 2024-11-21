package com.example.unieventos.ui.screens.client

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.unieventos.R
import com.example.unieventos.dto.LocationDropdownDTO
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventLocation
import com.example.unieventos.models.Ticket
import com.example.unieventos.models.TicketStatus
import com.example.unieventos.models.User
import com.example.unieventos.models.ui.AlertType
import com.example.unieventos.ui.components.AlertMessage
import com.example.unieventos.ui.components.EventSaver
import com.example.unieventos.ui.components.LocationDropdownDTOSaver
import com.example.unieventos.ui.components.SleekButton
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TransparentTopBarComponent
import com.example.unieventos.ui.components.UserSaver
import com.example.unieventos.utils.Formatters
import com.example.unieventos.utils.RequestResult
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.TicketViewModel
import com.example.unieventos.viewmodel.UsersViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TicketTransactionScreen(
    eventId: String,
    userId: String,
    usersViewModel: UsersViewModel,
    eventsViewModel: EventsViewModel,
    artistViewModel: ArtistViewModel,
    ticketViewModel: TicketViewModel,
    onNavigateToBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {

    var event by rememberSaveable(stateSaver = EventSaver) {
        mutableStateOf(Event())
    }
    var user = rememberSaveable(saver = UserSaver) { User() }
    var artistName by rememberSaveable { mutableStateOf("") }
    var title by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    val authResult by ticketViewModel.authResult.collectAsState()

    LaunchedEffect(eventId) {
        if (eventId.isNotEmpty()) {
            event = eventsViewModel.getEventById(eventId)!!
            event?.let { loadedEvent ->
                title = loadedEvent.title
                date = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(loadedEvent.date)
                imageUrl = loadedEvent.imageUrl
                artistName = artistViewModel.getArtistById(loadedEvent.artistId)?.name.toString()
            }
        }
    }

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) {
            user = usersViewModel.getUserById(userId)!!
        }
    }

    var idLocation by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold (
        floatingActionButton = {
            when(authResult) {
                is RequestResult.Loading -> {  }
                is RequestResult.Failure -> {
                    AlertMessage(
                        type = AlertType.ERROR,
                        message = (authResult as RequestResult.Failure).error,
                        modifier = Modifier.width(318.dp)
                    )
                    LaunchedEffect (Unit) {
                        delay(2000)
                        ticketViewModel.resetAuthResult()
                    }
                }
                is RequestResult.Success -> {
                    AlertMessage(
                        type = AlertType.SUCCESS,
                        message = (authResult as RequestResult.Success).message,
                        modifier = Modifier.width(318.dp)
                    )
                    LaunchedEffect (Unit) {
                        delay(2000)
                        onNavigateToHome()
                        ticketViewModel.resetAuthResult()
                    }
                }
                null -> { }
            }
        },
        topBar = {
            TransparentTopBarComponent(
                text = "",
                onClick = { onNavigateToBack() },
                icon = {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            ) {
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
                        .padding(top = 5.dp, start = 20.dp),
                ) {
                    Text(
                        text = stringResource(id = R.string.lbl_add_cart),
                        fontSize = 23.sp,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    BuyTicketForm(
                        onNavigateToBack = { /*TODO*/ },
                        ticketViewModel = ticketViewModel,
                        context = context,
                        eventId = eventId,
                        eventsViewModel = eventsViewModel,
                        userId = userId,
                        usersViewModel = usersViewModel
                    )


                }
            }
                }

        }
}

@Composable
fun BuyTicketForm(
    onNavigateToBack: () -> Unit,
    ticketViewModel: TicketViewModel,
    eventsViewModel: EventsViewModel,
    usersViewModel: UsersViewModel,
    eventId: String,
    userId: String,
    context: Context
) {

    var quantity by rememberSaveable { mutableStateOf("") }
    var idLocation by rememberSaveable(stateSaver = LocationDropdownDTOSaver) {
        mutableStateOf(LocationDropdownDTO())
    }
    var event by rememberSaveable(stateSaver = EventSaver) {
        mutableStateOf(Event())
    }

    LaunchedEffect (eventId) {
        event = eventsViewModel.getEventById(eventId)!!
    }

    var user = rememberSaveable(saver = UserSaver) { User() }
    LaunchedEffect (userId) {
        user = usersViewModel.getUserById(userId)!!
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        TextFieldForm(
            modifier = Modifier
                .width(318.dp),
            value = quantity,
            onValueChange = { quantity = it },
            supportingText = stringResource(id = R.string.err_cantidad),
            label = stringResource(id = R.string.label_cantidad),
            onValidate = { it.isEmpty() || it.toIntOrNull() == null },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        DropdownMenuLocation(
            modifier = Modifier
                .width(318.dp)
                .padding(bottom = 14.dp),
            value = idLocation.locationName,
            onValueChange = { idLocation = it },
            label = stringResource(id = R.string.placeholder_tarifa),
            items = eventsViewModel.getEventLocationsById(eventId)
        )

        Spacer(modifier = Modifier.height(10.dp))
        SleekButton(text = stringResource(id = R.string.btn_comprar), onClickAction = {
            val location = idLocation.let { event.findLocationById(it.idLocation) }!!
            ticketViewModel.createTicket(
                Ticket(
                    userId = userId,
                    eventId = eventId,
                    eventLocation = location,
                    quantity = quantity.toInt(),
                    acquisitionDate = Date(),
                    price = location.price * quantity.toInt(),
                    status = TicketStatus.PENDING
                )
            )


            onNavigateToBack()
        })

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuLocation(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (LocationDropdownDTO) -> Unit,
    label: String,
    items: List<EventLocation>,
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {

        OutlinedTextField(
            modifier = modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = value,
            onValueChange = { },
            readOnly = true,
            label = { Text(text = label) },
            singleLine = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.name + " - " + Formatters.formatPrice(item.price)) },
                    onClick = {
                        expanded = false
                        onValueChange(LocationDropdownDTO(item.id, item.name))
                    }
                )
            }
        }

    }
}