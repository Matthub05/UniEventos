package com.example.unieventos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.unieventos.ui.components.ItemEvento

@Composable
fun HomeScreen() {

    Scaffold { paddingValues ->

        Column (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
        ) {

            ItemEvento(
                nombre = "Concierto de Switchfoot",
                ciudad = "Bogota",
            )

            ItemEvento(
                nombre = "Charla motivacional de Juliana",
                ciudad = "Armenia"
            )

            ItemEvento(
                nombre = "Stand up de Carmen",
                ciudad = "La Tebaida"
            )

        }
    }
}