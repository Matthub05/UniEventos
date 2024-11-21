package com.example.unieventos.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.unieventos.models.Artist

@Composable
fun ArtistItem(
    onNavigateToCreateArtist: (String) -> Unit = {},
    artist: Artist,
    isResultItem: Boolean = false
) {

    var imageModifier = Modifier.clip(CircleShape).size(90.dp)
    var blankSpace = 23.dp
    if (isResultItem) {
        imageModifier = Modifier.clip(RoundedCornerShape(10.dp)).size(70.dp)
        blankSpace = 12.dp
    }

    Surface (
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(20.dp)
    ) {

        Row (
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clickable (
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ){
                    onNavigateToCreateArtist(artist.id)
                },
        ) {
            val model = ImageRequest.Builder(LocalContext.current)
                .data(artist.imageUrl)
                .crossfade(true)
            if (!isResultItem) {
                model.transformations(CircleCropTransformation())
            }

            AsyncImage(
                modifier = imageModifier,
                model = model.build(),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Spacer(modifier = Modifier.height(blankSpace))
                Text(
                    text = artist.name,
                    fontSize = 17.sp,
                )
                Text(text = artist.genre,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

    }
}