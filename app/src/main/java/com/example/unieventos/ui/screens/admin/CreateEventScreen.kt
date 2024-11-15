package com.example.unieventos.ui.screens.admin

import AutoResizedText
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import com.example.unieventos.R
import com.example.unieventos.models.Artist
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventLocation
import com.example.unieventos.models.EventSite
import com.example.unieventos.models.ui.AlertType
import com.example.unieventos.ui.components.AlertMessage
import com.example.unieventos.ui.components.DatePickerForm
import com.example.unieventos.ui.components.LongTextFieldForm
import com.example.unieventos.ui.components.MultimediaEventSection
import com.example.unieventos.ui.components.SleekButton
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TransparentTopBarComponent
import com.example.unieventos.utils.Formatters
import com.example.unieventos.utils.RequestResult
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun CreateEventScreen(
    eventId: String?,
    onNavigateToBack: () -> Unit,
    eventsViewModel: EventsViewModel,
    artistViewModel: ArtistViewModel,
) {

    val config = mapOf(
        "cloud_name" to stringResource(id = R.string.cloudinary_name),
        "api_key" to stringResource(id = R.string.cloudinary_api_key),
        "api_secret" to stringResource(id = R.string.cloudinary_api_secret)
    )

    val cloudinary = Cloudinary(config)

    Log.e("e", config.toString())
    
    val context = LocalContext.current

    val titleForm = stringResource(id = R.string.title_nuevo_evento)
    val defaultImage = "https://images.stockcake.com/public/7/1/7/7170f19b-43f2-4d74-9b02-426f5830c897_large/singer-at-spotlight-stockcake.jpg"
    var componentTitle = titleForm
    val scrollState = rememberScrollState()

    val artists = artistViewModel.artist.collectAsState().value

    var event by remember { mutableStateOf<Event?>(null) }
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var idArtist by rememberSaveable { mutableStateOf(arrayOf("", "")) }
    var category by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var capacity by rememberSaveable { mutableStateOf("") }
    var location by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    var mediaUrls by remember { mutableStateOf( listOf<String>() ) }
    var eventLocations by remember { mutableStateOf( listOf<EventLocation>() ) }


    var showLocationDialog by rememberSaveable { mutableStateOf(false) }
    var locationName by rememberSaveable { mutableStateOf("") }
    var locationPlaces by rememberSaveable { mutableStateOf("") }
    var locationPrice by rememberSaveable { mutableStateOf("") }
    var selectedLocation: EventLocation? by rememberSaveable { mutableStateOf(null) }

    var showConfirmationDialog by rememberSaveable { mutableStateOf(false) }
    val authResult by eventsViewModel.authResult.collectAsState()

    var textPermisoAutorizado = stringResource(id = (R.string.text_permiso_autorizado))
    var textPermisoDenegado = stringResource(id = (R.string.text_permiso_denegado))
    var textFotoSeleccionada = stringResource(id = (R.string.text_imagen_seleccionada))
    val scope = rememberCoroutineScope()

    val fileLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent ()) { uri: Uri? ->
        uri?.let {

            scope.launch (Dispatchers.IO) {
                val inputStream = context.contentResolver.openInputStream(uri)
                inputStream?.use { stream ->
                    val result = cloudinary.uploader().upload(stream, ObjectUtils.emptyMap())
                    Log.e("URI", result.toString())
                    mediaUrls += result["secure_url"].toString()
                }

            }

        }
    }

    val fileLauncherPrincipal = rememberLauncherForActivityResult(ActivityResultContracts.GetContent ()) { uri: Uri? ->
        uri?.let {

            scope.launch (Dispatchers.IO) {
                val inputStream = context.contentResolver.openInputStream(uri)
                inputStream?.use { stream ->
                    val result = cloudinary.uploader().upload(stream, ObjectUtils.emptyMap())
                    Log.e("URI", result.toString())
                    imageUrl = result["secure_url"].toString()
                }

            }

        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            Toast.makeText(context, textPermisoAutorizado, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, textPermisoDenegado, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(eventId) {
        if (!eventId.isNullOrEmpty()) {
            event = eventsViewModel.getEventById(eventId)
            event?.let { loadedEvent ->
                title = loadedEvent.title
                description = loadedEvent.description
                idArtist[0] = loadedEvent.artistId
                category = loadedEvent.category
                date = SimpleDateFormat("dd/MM/yyyy", Locale.US).format(loadedEvent.date)
                name = loadedEvent.eventSite.name
                capacity = loadedEvent.eventSite.capacity.toString()
                location = loadedEvent.eventSite.location
                imageUrl = loadedEvent.imageUrl
                mediaUrls = loadedEvent.mediaUrls
                eventLocations = loadedEvent.locations
                componentTitle = loadedEvent.title
            }
        }
    }

    val errDateMessage = stringResource(id = R.string.err_fecha)
    val errNoLocation = stringResource(id = R.string.err_no_localizacion)
    val errNoMedia = stringResource(id = R.string.err_media)
    val errCamposVacios = stringResource(id = R.string.err_campos_vacios)

    Scaffold(
        topBar = {

                TransparentTopBarComponent(
                    text = "",
                    onClick = { onNavigateToBack() },
                    icon = {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Outlined.AddPhotoAlternate,
                            contentDescription = "Picture",
                         )
                    },
                    onTrailingIconClick = {
                        val permissionCheckResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.READ_MEDIA_IMAGES
                            )
                        } else {
                            ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        }

                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            fileLauncherPrincipal.launch("image/*")
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                            } else {
                                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }
                )
        },
        floatingActionButton = {
            Column {

                if (!eventId.isNullOrEmpty()) {
                    Spacer(modifier = Modifier
                        .height(10.dp)
                        .align(Alignment.End))

                    FloatingActionButton(
                        containerColor = Color.White,
                        modifier = Modifier
                            .padding(end = 15.dp, bottom = 13.dp)
                            .align(Alignment.End),
                        onClick = {

                            showConfirmationDialog = true
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                }

                FloatingActionButton(
                    containerColor = Color.White,
                    modifier = Modifier
                        .padding(end = 15.dp, bottom = 13.dp)
                        .align(Alignment.End),
                    onClick = {


                        if (name.isEmpty() || idArtist.isEmpty() || category.isEmpty() || title.isEmpty() || capacity.isEmpty() || location.isEmpty()) {
                            Toast.makeText(context, errCamposVacios, Toast.LENGTH_SHORT).show()
                            return@FloatingActionButton
                        }

                        if (eventLocations.isEmpty()) {
                            Toast.makeText(context, errNoLocation, Toast.LENGTH_SHORT).show()
                            return@FloatingActionButton
                        }

                        if (mediaUrls.size < 2) {
                            Toast.makeText(context, errNoMedia, Toast.LENGTH_SHORT).show()
                            return@FloatingActionButton
                        }

                        if(imageUrl.isEmpty()) {
                            imageUrl = defaultImage
                        }

                        val cal = Calendar.getInstance()
                        val values = date.split("/")
                        if (values.size != 3) {
                            Toast.makeText(context, errDateMessage, Toast.LENGTH_SHORT).show()
                            return@FloatingActionButton
                        }
                        cal.set(values[2].toInt(), values[1].toInt() - 1, values[0].toInt())
                        val dateParsed = cal.time

                        val newEvent = Event(
                            title = title,
                            description = description,
                            artistId = idArtist[0],
                            category = category,
                            date = dateParsed,
                            eventSite = EventSite(
                                name = name,
                                capacity = capacity.toLongOrNull() ?: 0,
                                location = location,
                            ),
                            imageUrl = imageUrl,
                            mediaUrls = mediaUrls,
                            locations = eventLocations
                        )

                        if (!eventId.isNullOrEmpty()) {
                            newEvent.id = eventId
                            eventsViewModel.updateEvent(newEvent)
                        } else
                            eventsViewModel.createEvent(newEvent)
                    }
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = null)
                }

                when (authResult) {
                    is RequestResult.Loading -> {  }
                    is RequestResult.Failure -> {

                        AlertMessage(
                            type = AlertType.ERROR,
                            message = (authResult as RequestResult.Failure).error,
                            modifier = Modifier
                                .width(318.dp)
                                .padding(bottom = 20.dp)
                        )
                        LaunchedEffect(Unit) {
                            delay(1000)
                            eventsViewModel.resetAuthResult()

                        }
                    }
                    is  RequestResult.Success -> {
                            AlertMessage(
                                type = AlertType.SUCCESS,
                                message = (authResult as RequestResult.Success).message,
                                modifier = Modifier
                                    .width(318.dp)
                            )
                            LaunchedEffect(Unit) {
                                delay(1000)
                                onNavigateToBack()
                                eventsViewModel.resetAuthResult()
                            }

                    }
                    null -> { }
                }

            }

        },
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
                        .data(
                            if (imageUrl.isBlank() || imageUrl == "Image URL") {
                                defaultImage
                            } else imageUrl,

                            )
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
                            .padding(top = 270.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        AutoResizedText(
                            text = title.ifBlank { titleForm },
                        )

                        Text(
                            text = name.ifBlank { "" },
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            fontSize = 17.sp
                        )
                    }

                }
            }

            item {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 25.dp, end = 25.dp, top = 18.dp),
                ) {

                    Text(
                        text = stringResource(id = R.string.label_detalles),
                        fontSize = 23.sp,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    TextFieldForm(
                        modifier = Modifier.fillMaxWidth(),
                        value = title,
                        onValueChange = { title = it },
                        supportingText = stringResource(id = R.string.err_titulo),
                        label = stringResource(id = R.string.placeholder_titulo),
                        onValidate = { it.isEmpty() },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    LongTextFieldForm(
                        modifier = Modifier.fillMaxWidth(),
                        value = description,
                        onValueChange = { description = it },
                        supportingText = stringResource(id = R.string.err_descripcion),
                        label = stringResource(id = R.string.placeholder_descripcion),
                        onValidate = { it.isEmpty() },
                        maxLines = 8,
                        minLines = 5
                    )

                    DropdownMenuArtists(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 14.dp),
                        value = idArtist[1],
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

                    Spacer(modifier = Modifier.height(31.dp))

                    Text(
                        text = stringResource(id = R.string.text_recinto),
                        fontSize = 23.sp,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

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

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = stringResource(id = R.string.text_multimedia),
                        fontSize = 23.sp,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    val handlePictureClick: (String) -> Unit = { picture ->
                        Log.d("Picture Clicked", "Clicked on picture: $picture")
                        mediaUrls -= picture
                    }

                    if (mediaUrls.isEmpty()){
                        MultimediaEventSection(
                            picturesReceived = listOf(
                                "https://www.arete.eu/media/images/media-placeholder.png",
                            ),
                            onClick =  handlePictureClick
                        )

                    } else {
                         MultimediaEventSection(
                             picturesReceived = mediaUrls,
                             onClick = handlePictureClick
                         )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    SleekButton(text = stringResource(id = R.string.btn_multimedia)) {
                        val permissionCheckResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.READ_MEDIA_IMAGES
                            )
                        } else {
                            ContextCompat.checkSelfPermission(
                                context,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE
                            )
                        }

                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            fileLauncher.launch("image/*")
                        } else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                permissionLauncher.launch(android.Manifest.permission.READ_MEDIA_IMAGES)
                            } else {
                                permissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = stringResource(id = R.string.placeholder_tarifas),
                        fontSize = 23.sp,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    eventLocations.forEach { location ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(modifier = Modifier.padding(end = 4.dp), text = location.name)
                            Text(modifier = Modifier.padding(end = 4.dp), text = "- " +
                                    stringResource(id = R.string.text_cupos)+
                                    ": ")
                            Text(
                                modifier = Modifier.padding(end = 4.dp),
                                text = Formatters.formatNumber(location.places)
                            )
                            Spacer(modifier = Modifier.weight(1f))

                            Text(text = Formatters.formatPrice(location.price))
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

                    Spacer(modifier = Modifier.height(8.dp))

                    SleekButton(text = stringResource(id = R.string.btn_anadir_localizacion)) {
                        showLocationDialog = true
                    }
                    Spacer(modifier = Modifier.height(170.dp))
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
                        var newId: Int
                        if (selectedLocation != null) {
                            newId = selectedLocation!!.id
                            eventLocations -= selectedLocation!!
                        } else {
                            newId = if (eventLocations.isNotEmpty()) {
                                eventLocations[0].id + 1
                            } else {
                                0
                            }
                        }
                        eventLocations += EventLocation(
                            id = newId,
                            name = locationName,
                            places = locationPlaces.toIntOrNull() ?: 0,
                            price = locationPrice.toDoubleOrNull() ?: 0.0,
                        )

                        selectedLocation = null
                        showLocationDialog = false
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

    val errorMessage = stringResource(id = R.string.adv_error)
    if (showConfirmationDialog) {
        Dialog(onDismissRequest = {
            showConfirmationDialog = false
        }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.98f)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(6.dp),
                color = Color(0xFF161616)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(id = R.string.adv_eliminar_evento),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )


                    Text(
                        text = stringResource(id = R.string.adv_accion_no_se_puede_deshacer),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    SleekButton(text = stringResource(id = R.string.btn_eliminar)) {
                        if (eventId.isNullOrEmpty()) {
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            return@SleekButton
                        }
                        showConfirmationDialog = false
                        eventsViewModel.deleteEvent(eventId)

                    }

                    SleekButton(text = stringResource(id = R.string.btn_cancelar)) {
                        showConfirmationDialog = false
                    }


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
    onValueChange: (Array<String>) -> Unit,
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
                        onValueChange(arrayOf(item.id, item.name))
                    }
                )
            }
        }

    }
}