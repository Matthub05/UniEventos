package com.example.unieventos.ui.screens.admin

import AutoResizedText
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.unieventos.R
import com.example.unieventos.models.Artist
import com.example.unieventos.models.Event
import com.example.unieventos.models.Ticket
import com.example.unieventos.ui.components.LongTextFieldForm
import com.example.unieventos.ui.components.SleekButton
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TopBarComponent
import com.example.unieventos.ui.components.TransparentTopBarComponent
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateArtistScreen(
    artistId: String?,
    onNavigateToBack: () -> Unit,
    artistViewModel: ArtistViewModel,
) {

    val context = LocalContext.current

    val titleForm = stringResource(id = R.string.placeholder_form_artista)
    val defaultImage = "https://images.stockcake.com/public/7/1/7/7170f19b-43f2-4d74-9b02-426f5830c897_large/singer-at-spotlight-stockcake.jpg"
    var componentTitle = titleForm
    var componentImage = defaultImage

    val artists = artistViewModel.artist.collectAsState().value

    var artist by remember { mutableStateOf<Artist?>(null) }
    var name by rememberSaveable { mutableStateOf("") }
    var genere by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }

    var showConfirmationDialog by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(artistId) {
        if (!artistId.isNullOrEmpty()) {
            artist = artistViewModel.getArtistById(artistId)
            artist?.let { loadedArtist ->
                name = loadedArtist.name
                genere = loadedArtist.genre
                description = loadedArtist.description
                imageUrl = loadedArtist.imageUrl
                componentTitle = loadedArtist.name
                componentImage = loadedArtist.imageUrl
            }
        }
    }
    
    Scaffold(
        topBar = {
            TransparentTopBarComponent(
                text = "",
                onClick = { onNavigateToBack() }
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        floatingActionButton = {
            Column {

                if (!artistId.isNullOrEmpty()) {
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )
                    FloatingActionButton(
                        containerColor = Color.White,
                        modifier = Modifier
                            .padding(end = 15.dp, bottom = 13.dp),
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
                        .padding(end = 15.dp, bottom = 13.dp),
                    onClick = {
                        val newArtist = Artist(
                            name = name,
                            genre = genere,
                            description = description,
                            imageUrl = imageUrl
                        )

                        if (!artistId.isNullOrEmpty()) {
                            newArtist.id = artistId
                            artistViewModel.updateArtist(newArtist)
                        } else
                            artistViewModel.createArtist(newArtist)
                        onNavigateToBack()
                    }
                ) {
                    Icon(imageVector = Icons.Default.Save, contentDescription = null)
                }
            }

        },
    ) { innerPadding ->

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
                                componentImage
                            }
                            else imageUrl,

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
                            text = if (name.isBlank()) titleForm else name,
                        )

                        Text(
                            text = if (name.isBlank()) "" else genere,
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
                        value = name,
                        onValueChange = { name = it },
                        supportingText = stringResource(id = R.string.err_nombre),
                        label = stringResource(id = R.string.placeholder_nombre),
                        onValidate = { it.isEmpty() },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    TextFieldForm(
                        modifier = Modifier.fillMaxWidth(),
                        value = genere,
                        onValueChange = { genere = it },
                        supportingText = stringResource(id = R.string.err_descripcion),
                        label = stringResource(id = R.string.placeholder_genero),
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

                    TextFieldForm(
                        modifier = Modifier.fillMaxWidth(),
                        value = imageUrl,
                        onValueChange = { imageUrl = it },
                        supportingText = "",
                        label = stringResource(id = R.string.label_url_imagen),
                        onValidate = { it.isEmpty() },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                }
            }

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
                            text = stringResource(id = R.string.adv_eliminar_artista),
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
                            if (artistId.isNullOrEmpty()) {
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                                return@SleekButton
                            }
                            artistViewModel.deleteArtist(artistId)
                            onNavigateToBack()
                        }

                        SleekButton(text = stringResource(id = R.string.btn_cancelar)) {
                            showConfirmationDialog = false
                        }


                    }
                }
            }


        }

    }
}
