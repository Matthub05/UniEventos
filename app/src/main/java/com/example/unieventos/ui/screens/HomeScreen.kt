package com.example.unieventos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.unieventos.R
import com.example.unieventos.ui.components.ItemEvento

@Composable
fun HomeScreen(
    onNavigateToProfileEdit: () -> Unit,
    onNavigateToCreateEvent: () -> Unit
) {

    Scaffold { paddingValues ->

        Column (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
        ) {

            ItemEvento(
                nombre = "Concierto banda rock",
                ciudad = "Bogota",
            )

            ItemEvento(
                nombre = "Charla triste",
                ciudad = "Armenia"
            )

            ItemEvento(
                nombre = "Charla feliz",
                ciudad = "La Tebaida"
            )

            Button(onClick = { onNavigateToProfileEdit() }) {
                Text(text = stringResource(id = R.string.btn_editar_perfil))
            }

            Button(onClick = { onNavigateToCreateEvent() }) {
                Text(text = stringResource(id = R.string.btn_crear_evento))
            }
        }
    }
}