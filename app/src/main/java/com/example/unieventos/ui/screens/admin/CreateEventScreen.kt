package com.example.unieventos.ui.screens.admin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unieventos.R
import com.example.unieventos.ui.components.DatePickerForm

import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TopBarComponent

@Composable
fun CreateEventScreen(
    onNavigateToHome: () -> Unit
) {
    val scrollState = rememberScrollState()
    var titulo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var artista by rememberSaveable { mutableStateOf("") }
    var categoria by rememberSaveable { mutableStateOf("") }
    var fecha by rememberSaveable { mutableStateOf("") }
    var nombre by rememberSaveable { mutableStateOf("") }
    var aforo by rememberSaveable { mutableStateOf("") }
    var ubicacion by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBarComponent(
                text = stringResource(id = R.string.title_registrar_evento),
                onClick = { onNavigateToHome() },
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Detalles generales"
            )

            Spacer(modifier = Modifier.height(10.dp))

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = titulo,
                onValueChange = { titulo = it },
                supportingText = "",
                label = stringResource(id = R.string.placeholder_titulo),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = descripcion,
                onValueChange = { descripcion = it },
                supportingText = "",
                label = stringResource(id = R.string.placeholder_descripcion),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = artista,
                onValueChange = { artista = it },
                supportingText = "",
                label = stringResource(id = R.string.placeholder_artista),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = categoria,
                onValueChange = { categoria = it },
                supportingText = "",
                label = stringResource(id = R.string.placeholder_categoria),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            DatePickerForm(
                value = fecha,
                onValueChange = {},
                label = "fecha",
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Recinto"
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = nombre,
                onValueChange = { nombre = it },
                supportingText = "",
                label = stringResource(id = R.string.placeholder_nombre_recinto),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = aforo,
                onValueChange = { aforo = it },
                supportingText = "",
                label = stringResource(id = R.string.placeholder_aforo),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = ubicacion,
                onValueChange = { ubicacion = it },
                supportingText = "",
                label = stringResource(id = R.string.placeholder_ubicacion),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Text(
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Multimedia"
            )

            Text(
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Tarifas"
            )



        }
        FloatingActionButton(
            onClick = { /* Handle click */ },
            modifier = Modifier
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }

    }


}
