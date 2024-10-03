package com.example.unieventos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedSection(
    modifier: Modifier,
    onSeeAllClick: () -> Unit
) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(7) {
                FeaturedEvent()
            }
        }
    }


@Composable
fun FeaturedEvent() {
    Column(

    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(RoundedCornerShape(8))
                .background(Color.LightGray)
        ) {
        }

        Spacer(modifier = Modifier.height(5.dp))

        Column (
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "Evento",
                textAlign = TextAlign.Left,
                fontSize = 15.sp,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Artista",
                textAlign = TextAlign.Left,
                color = Color.Gray,
                style = MaterialTheme.typography.bodySmall
            )
        }

    }
}