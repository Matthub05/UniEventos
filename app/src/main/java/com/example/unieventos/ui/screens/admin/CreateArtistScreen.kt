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
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.AddPhotoAlternate
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.example.unieventos.models.ui.AlertType
import com.example.unieventos.ui.components.AlertMessage
import com.example.unieventos.ui.components.LongTextFieldForm
import com.example.unieventos.ui.components.SleekButton
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TransparentTopBarComponent
import com.example.unieventos.utils.RequestResult
import com.example.unieventos.viewmodel.ArtistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreateArtistScreen(
    artistId: String?,
    onNavigateToBack: () -> Unit,
    artistViewModel: ArtistViewModel,
) {

    val context = LocalContext.current

    val config = mapOf(
        "cloud_name" to stringResource(id = R.string.cloudinary_name),
        "api_key" to stringResource(id = R.string.cloudinary_api_key),
        "api_secret" to stringResource(id = R.string.cloudinary_api_secret)
    )

    val cloudinary = Cloudinary(config)

    val titleForm = stringResource(id = R.string.placeholder_form_artista)
    val defaultImage =
        "https://images.stockcake.com/public/7/1/7/7170f19b-43f2-4d74-9b02-426f5830c897_large/singer-at-spotlight-stockcake.jpg"
    var componentTitle = titleForm
    var componentImage = defaultImage

    val artists = artistViewModel.artist.collectAsState().value

    var artist by remember { mutableStateOf<Artist?>(null) }
    var name by rememberSaveable { mutableStateOf("") }
    var genere by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }
    var textPermisoAutorizado = stringResource(id = (R.string.text_permiso_autorizado))
    var textPermisoDenegado = stringResource(id = (R.string.text_permiso_denegado))
    var textFotoSeleccionada = stringResource(id = (R.string.text_imagen_seleccionada))

    var showConfirmationDialog by rememberSaveable { mutableStateOf(false) }
    val authResult by artistViewModel.authResult.collectAsState()
    var visible by remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()

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
                if (!artistId.isNullOrEmpty()) {
                    Spacer(
                        modifier = Modifier
                            .height(10.dp)
                    )
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



                        if (name.isEmpty() || genere.isEmpty() || description.isEmpty()) {
                            Toast.makeText(context, errCamposVacios, Toast.LENGTH_SHORT).show()
                            return@FloatingActionButton
                        }

                        if(imageUrl.isEmpty()) {
                            imageUrl = defaultImage
                        }

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
                    }
                ) {
                    if (authResult is RequestResult.Loading) {
                        CircularProgressIndicator(modifier = Modifier.width(24.dp))
                    } else {
                        Icon(imageVector = Icons.Default.Save, contentDescription = null)
                    }
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
                                    artistViewModel.resetAuthResult()

                                }



                        }
                        is  RequestResult.Success -> {
                            visible = true
                            AnimatedVisibility(visible = visible,
                                enter = slideInVertically(initialOffsetY = { it / 2 }),
                                exit = slideOutVertically(targetOffsetY = { it / 2 })) {

                                AlertMessage(
                                    type = AlertType.SUCCESS,
                                    message = (authResult as RequestResult.Success).message,
                                    modifier = Modifier
                                        .width(318.dp)
                                )
                                LaunchedEffect(Unit) {
                                    delay(1000)
                                    visible = false
                                    onNavigateToBack()
                                    artistViewModel.resetAuthResult()
                                }
                            }
                        }
                        null -> { }
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

                    Spacer(modifier = Modifier.height(160.dp))

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
                            showConfirmationDialog = false
                            artistViewModel.deleteArtist(artistId)

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
