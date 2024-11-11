package com.example.unieventos.ui.screens.admin.tabs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unieventos.models.Coupon
import com.example.unieventos.ui.components.ArtistItem
import com.example.unieventos.ui.components.CouponItem
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.CouponsViewModel

@Composable
fun ArtistsScreen(
    paddingValues: PaddingValues,
    artistViewModel: ArtistViewModel,
    onNavigateToCreateArtist: (String?) -> Unit,
) {

    val artists = artistViewModel.artist.collectAsState().value

    LazyColumn(
        modifier = Modifier
        .fillMaxWidth()
        .padding(
            start = 7.dp,
            end = 7.dp,
            top = 90.dp,
            bottom = paddingValues.calculateBottomPadding()
        ),
    ) {
        items( artists ) { artist ->
            ArtistItem(
                onNavigateToCreateArtist,
                artist
            )
        }
    }
}