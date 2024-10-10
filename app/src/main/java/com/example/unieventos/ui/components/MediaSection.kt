package com.example.unieventos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.unieventos.models.Artist
import com.example.unieventos.models.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MediaSection(
    modifier: Modifier,
    event: Event,
) {
    Column(modifier = modifier) {
        Text(
            text = "Multimedia",
            modifier.padding(
                top = 16.dp,
                bottom = 16.dp
            ),
            fontSize = 23.sp,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items( event.mediaUrls ) { mediaUrl ->
                Picture(mediaUrl)
            }

            items( event.mediaUrls ) { mediaUrl ->
                Picture(mediaUrl)
            }

            items( event.mediaUrls ) { mediaUrl ->
                Picture(mediaUrl)
            }

        }
    }
}

@Composable
fun Picture(
    mediaUrl: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val model = ImageRequest.Builder(LocalContext.current)
            .data(mediaUrl)
            .crossfade(true)
            .build()
        
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8))
                .size(90.dp),
            model = model,
            contentDescription = null,
            contentScale = ContentScale.Crop
            )
    }
}