package com.example.unieventos.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unieventos.R
import com.example.unieventos.models.Artist
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventLocation
import com.example.unieventos.models.EventSite
import com.example.unieventos.ui.components.DatePickerForm

import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TopBarComponent
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CreateEventScreen(
    eventId: Int?,
    onNavigateToBack: () -> Unit,
    eventsViewModel: EventsViewModel,
    artistViewModel: ArtistViewModel,
) {

    var componentTitle = stringResource(id = R.string.title_registrar_evento)
    val scrollState = rememberScrollState()

    val artists = artistViewModel.artist.collectAsState().value

    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var idArtist by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var capacity by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }

    var showLocationDialog by rememberSaveable { mutableStateOf(false) }
    var eventLocations by rememberSaveable { mutableStateOf( listOf<EventLocation>() ) }
    var locationName by rememberSaveable { mutableStateOf("") }
    var locationPlaces by rememberSaveable { mutableStateOf("") }
    var locationPrice by rememberSaveable { mutableStateOf("") }
    var selectedLocation: EventLocation? by rememberSaveable { mutableStateOf(null) }

    if (eventId != null) {
        val event = eventsViewModel.getEventById(eventId)
        title = event?.title ?: ""
        description = event?.description ?: ""
        idArtist = (event?.artistId ?: 0).toString()
        category = event?.category ?: ""
        date = SimpleDateFormat(
            "dd/MM/yyyy",
            Locale.US
        ).format(event?.date ?: "")
        name = event?.eventSite?.name ?: ""
        capacity = event?.eventSite?.capacity?.toString() ?: ""
        location = event?.eventSite?.location ?: ""
        imageUrl = event?.imageUrl ?: ""
        eventLocations = event?.locations ?: listOf()

        componentTitle = stringResource(id = R.string.title_modificar_evento)
    }

    fun saveEvent() {
        val cal = Calendar.getInstance()
        val values = date.split("/")
        cal.set(values[2].toInt(), values[1].toInt() - 1, values[0].toInt())
        val dateParsed = cal.time

        val newEvent = Event(
            id = 0,
            title = title,
            description = description,
            artistId = idArtist.toIntOrNull() ?: 0,
            category = category,
            date = dateParsed,
            eventSite = EventSite(
                name = name,
                capacity = capacity.toLongOrNull() ?: 0,
                location = location,
            ),
            imageUrl = imageUrl,
            locations = eventLocations
        )

        if (eventId != null) {
            newEvent.id = eventId
            eventsViewModel.updateEvent(newEvent)
        } else
            eventsViewModel.createEvent(newEvent)

        onNavigateToBack()
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                text = componentTitle,
                onClick = { onNavigateToBack() },
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { saveEvent() }
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = null)
            }
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Detalles generales"
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = { title = it },
                supportingText = stringResource(id = R.string.err_titulo),
                label = stringResource(id = R.string.placeholder_titulo),
                onValidate = { it.isEmpty() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = { description = it },
                supportingText = stringResource(id = R.string.err_descripcion),
                label = stringResource(id = R.string.placeholder_descripcion),
                onValidate = { it.isEmpty() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            DropdownMenuArtists(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 14.dp),
                value = idArtist, 
                onValueChange = { idArtist = it }, 
                label = stringResource(id = R.string.placeholder_artista), 
                items = artists
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = category,
                onValueChange = { category = it },
                supportingText = stringResource(id = R.string.err_categoria),
                label = stringResource(id = R.string.placeholder_categoria),
                onValidate = { it.isEmpty() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            DatePickerForm(
                value = date,
                onValueChange = { date = it },
                label = stringResource(id = R.string.placeholder_fecha),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Recinto"
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                supportingText = stringResource(id = R.string.err_nombre),
                label = stringResource(id = R.string.placeholder_nombre),
                onValidate = { it.isEmpty() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = capacity,
                onValueChange = { capacity = it },
                supportingText = stringResource(id = R.string.err_aforo),
                label = stringResource(id = R.string.placeholder_aforo),
                onValidate = { it.isEmpty() || it.toLongOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = location,
                onValueChange = { location = it },
                supportingText = stringResource(id = R.string.err_ubicacion),
                label = stringResource(id = R.string.placeholder_ubicacion),
                onValidate = { it.isEmpty() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Text(
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Multimedia"
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = imageUrl,
                onValueChange = { imageUrl = it },
                supportingText = "",
                label = "Url de la imagen", //cambiar luego
                onValidate = { it.isEmpty() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            
            Button(onClick = { /*TODO*/ }) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Text(text = stringResource(id = R.string.btn_anadir_foto))
                }
            }
            
            Text(
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Tarifas"
            )

            Spacer(modifier = Modifier.height(10.dp))

            eventLocations.forEach { location ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(modifier = Modifier.padding(end = 4.dp), text = location.name)
                    Text(modifier = Modifier.padding(end = 4.dp), text = location.places.toString())
                    Text(text = location.price.toString())
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        selectedLocation = location
                        locationName = location.name
                        locationPlaces = location.places.toString()
                        locationPrice = location.price.toString()
                        showLocationDialog = true
                    }) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                    }
                }
            }

            Button(
                modifier = Modifier.padding(bottom = 25.dp),
                onClick = { showLocationDialog = true }
            ) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Text(text = stringResource(id = R.string.btn_anadir_localizacion))
                }
            }

        }

    }

    if (showLocationDialog) {

        AlertDialog(
            onDismissRequest = {
                selectedLocation = null
                locationName = ""
                locationPlaces = ""
                locationPrice = ""
                showLocationDialog = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedLocation = null
                        showLocationDialog = false
                        eventLocations += EventLocation(
                            id = 1,
                            name = locationName,
                            places = locationPlaces.toIntOrNull() ?: 0,
                            price = locationPrice.toDoubleOrNull() ?: 0.0,
                        )
                        locationName = ""
                        locationPlaces = ""
                        locationPrice = ""
                    },
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                        disabledContentColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {
                    Text(text = stringResource(id = R.string.btn_guardar))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        eventLocations -= selectedLocation!!
                        selectedLocation = null
                        locationName = ""
                        locationPlaces = ""
                        locationPrice = ""
                        showLocationDialog = false
                    },
                    enabled = selectedLocation != null,
                    colors = ButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
                        disabledContentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text(text = stringResource(id = R.string.btn_eliminar))
                }
            },
            title = { Text(text = stringResource(id = R.string.btn_anadir_localizacion)) },
            text = {

                Column {
                    TextFieldForm(
                        value = locationName,
                        onValueChange = { locationName = it },
                        supportingText = stringResource(id = R.string.err_nombre),
                        label = stringResource(id = R.string.placeholder_nombre),
                        onValidate = { it.isEmpty() },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                    TextFieldForm(
                        value = locationPlaces,
                        onValueChange = { locationPlaces = it },
                        supportingText = stringResource(id = R.string.err_cupos_maximos),
                        label = stringResource(id = R.string.placeholder_cupos_maximos),
                        onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    TextFieldForm(
                        value = locationPrice,
                        onValueChange = { locationPrice = it },
                        supportingText = stringResource(id = R.string.err_tarifa),
                        label = stringResource(id = R.string.placeholder_tarifa),
                        onValidate = { it.isEmpty() || it.toDoubleOrNull() == null },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

            }
        )

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuArtists(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    items: List<Artist>,
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
                    text = { Text(text = item.name) },
                    onClick = {
                        expanded = false
                        onValueChange((item.id).toString())
                    }
                )
            }
        }

    }
}