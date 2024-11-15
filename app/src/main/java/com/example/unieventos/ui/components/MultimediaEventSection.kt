package com.example.unieventos.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultimediaEventSection(
    picturesReceived: List<String>,
    onClick: (String) -> Unit
): List<String> {

    var pictures = picturesReceived
    var pictureSelected = ""

    val handlePictureClick: (String) -> Unit = { picture ->
        onClick(picture)
    }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items( pictures ) { picture ->
               FeaturedPicture(picture,handlePictureClick)
            }
        }
    return pictures
    }

@Composable
fun FeaturedPicture (
    picture: String,
    onClick: (String) -> Unit
){
    Box(
    ) {
        val model = ImageRequest.Builder(LocalContext.current)
            .data(picture)
            .crossfade(true)
            .build()

        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(8))
                .size(120.dp),
            model = model,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = "Delete",
            tint = Color.White,
            modifier = Modifier
                .size(26.dp)
                .align(Alignment.TopEnd)
                .clickable {
                    onClick(picture)
                }
                .padding(6.dp)
        )

    }

}