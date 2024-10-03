package com.example.unieventos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.ui.components.FavoriteArtistsSection
import com.example.unieventos.ui.components.FeaturedSection
import com.example.unieventos.ui.components.NavigationBarCustom
import com.example.unieventos.ui.components.SearchBarTop
import com.example.unieventos.ui.components.SectionTitle

@Composable
fun HomeScreen() {
    Scaffold (
        topBar = {
            SearchBarTop(
                onSearchTextChanged = { newText ->
                    println("Search text: $newText")
                }
            )
        },
        bottomBar = {
            NavigationBarCustom()
        }
    )
    { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 8.dp,
                    end = 2.dp,
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
        ) {

                SectionTitle(
                    modifier = Modifier.fillMaxWidth(),
                    title =  stringResource(id = R.string.label_artistas_favoritos)
                ) {

                }

                FavoriteArtistsSection(
                    modifier = Modifier
                    .fillMaxWidth()
                    .width(800.dp)
                ) {

                }

                SectionTitle(
                    modifier = Modifier.fillMaxWidth(),
                    title =  stringResource(id = R.string.label_eventos_destacados)
                ) {

                }

            FeaturedSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(800.dp)// Adjust this value to extend outside the Column,
            ) { }


        }


    }

}
