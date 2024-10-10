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
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.example.unieventos.models.EventSite
import com.example.unieventos.ui.components.DatePickerForm

import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TopBarComponent
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import java.util.Calendar

@Composable
fun CreateEventScreen(
    onNavigateToHome: () -> Unit,
    eventsViewModel: EventsViewModel,
    artistViewModel: ArtistViewModel,
) {

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

    fun saveEvent() {
        val cal = Calendar.getInstance()
        var values = date.split("/")
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
            imageUrl = imageUrl
        )
        eventsViewModel.createEvent(newEvent)
        onNavigateToHome()
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                text = stringResource(id = R.string.title_registrar_evento),
                onClick = { onNavigateToHome() },
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

            Button(onClick = { /*TODO*/ }) {
                Row (verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Text(text = stringResource(id = R.string.btn_anadir_localizacion))
                }
            }

        }

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