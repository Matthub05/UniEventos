package com.example.unieventos.ui.screens.client

import AutoResizedText
import android.annotation.SuppressLint
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.unieventos.R
import com.example.unieventos.models.Artist
import com.example.unieventos.models.ui.AlertType
import com.example.unieventos.ui.components.AlertMessage
import com.example.unieventos.ui.components.TransparentTopBarComponent
import com.example.unieventos.utils.RequestResult
import com.example.unieventos.utils.SharedPreferenceUtils
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.UsersViewModel
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtistDetailsScreen(
    artistId: String,
    artistViewModel: ArtistViewModel,
    usersViewModel: UsersViewModel,
    onNavigateToBack: () -> Unit
) {

    val authResult by usersViewModel.authResult.collectAsState()

    val currentUser by usersViewModel.currentUser.collectAsState()
    val userId = SharedPreferenceUtils.getCurrentUser(LocalContext.current)?.id
    var userFavoriteArtists by remember { mutableStateOf<List<String>>(emptyList()) }
    LaunchedEffect(userId) {
        if (userId != null) {
            val user = usersViewModel.getUserById(userId)
            userFavoriteArtists = user?.favoriteArtistsId ?: emptyList()
        }
    }
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            userFavoriteArtists = currentUser!!.favoriteArtistsId
        }
    }

    var artist by remember { mutableStateOf<Artist?>(null) }
    var name by rememberSaveable { mutableStateOf("") }
    var genere by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var imageUrl by rememberSaveable { mutableStateOf("") }

    println(artistId)

    LaunchedEffect(artistId) {
        if (artistId.isNotEmpty()) {
            artist = artistViewModel.getArtistById(artistId)
            artist?.let { loadedArtist ->
                name = loadedArtist.name
                genere = loadedArtist.genre
                description = loadedArtist.description
                imageUrl = loadedArtist.imageUrl
            }
        }
    }

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(end = 16.dp, bottom = 16.dp),
                onClick = {
                    if (artistId.isEmpty() || userId.isNullOrEmpty()) return@FloatingActionButton
                    usersViewModel.saveFavoriteArtist(artistId = artistId, userId = userId)
                },
                containerColor = Color.White
            ) {
                if (authResult is RequestResult.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .width(22.dp)
                            .height(22.dp),
                        color = Color.Black
                    )
                } else {
                    val imageVector = if (artistId in userFavoriteArtists) {
                        Icons.Default.Bookmark
                    } else {
                        Icons.Default.BookmarkBorder
                    }
                    Icon(imageVector = imageVector, contentDescription = null)
                }
            }
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
                        usersViewModel.resetAuthResult()
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
                        usersViewModel.resetAuthResult()
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
                        .padding(top = 270.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    AutoResizedText(
                        text = name,
                    )

                    Text(
                        text = genere,
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

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = description,
                        fontSize = 15.sp,
                        color = Color.Gray,
                    )
                }

            }
        }

    }



}