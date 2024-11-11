package com.example.unieventos.ui.components

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.unieventos.models.Artist
import com.example.unieventos.models.Event
import com.example.unieventos.viewmodel.ArtistViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedSection(
    modifier: Modifier,
    onSeeAllClick: () -> Unit,
    events: List<Event>,
    artistViewModel: ArtistViewModel,
) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items( events ) { event ->
               FeaturedEvent(event, artistViewModel)
            }
        }
    }

@Composable
fun FeaturedEvent(
    event: Event,
    artistViewModel: ArtistViewModel,
) {

    var artist by remember { mutableStateOf<Artist?>(null) }
    var artistName by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(event.artistId) {
        if (!event.artistId.isNullOrEmpty()) {
            artist = artistViewModel.getArtistById(event.artistId)
            artist?.let { loadedArtist ->
                artistName = loadedArtist.name
            }
        }
    }

    Column(

    ) {
        val model = ImageRequest.Builder(LocalContext.current)
            .data(event.imageUrl)
            .crossfade(true)
            .build()

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8))
                .size(180.dp),
            model = model,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(5.dp))

        Column (
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = event.title,
                textAlign = TextAlign.Left,
                fontSize = 15.sp,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = artistName,
                textAlign = TextAlign.Left,
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}