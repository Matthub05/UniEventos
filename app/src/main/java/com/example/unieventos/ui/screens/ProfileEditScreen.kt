package com.example.unieventos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TopBarComponent

@Composable
fun ProfileEditScreen(
    onNavigateToHome: () -> Unit
) {

    val context = LocalContext.current

    Scaffold (
        topBar = {
            TopBarComponent(
                text = stringResource(id = R.string.titulo_editar_cuenta),
                onClick = { onNavigateToHome() },
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    ) { paddingValues ->

        ProfileEditForm(paddingValues, onNavigateToHome)

    }

}

@Composable
fun ProfileEditForm(
    padding: PaddingValues,
    onNavigateToHome: () -> Unit
) {

    var nombre by rememberSaveable { mutableStateOf("") }
    var direccion by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = nombre,
            onValueChange = { nombre = it },
            supportingText = stringResource(id = R.string.label_nombre_invalido),
            label = stringResource(id = R.string.label_nombre),
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = direccion,
            onValueChange = { direccion = it },
            supportingText = stringResource(id = R.string.label_direccion_invalida),
            label = stringResource(id = R.string.label_direccion),
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = telefono,
            onValueChange = { telefono = it },
            supportingText = stringResource(id = R.string.label_telefono_invalido),
            label = stringResource(id = R.string.label_telefono),
            onValidate = { it.isEmpty() || it.toIntOrNull() == null },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.height(240.dp))

        Button(
            onClick = { onNavigateToHome() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.errorContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                disabledContentColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Text(text = stringResource(id = R.string.btn_cerrar_cuenta))
        }

        Button(
            onClick = { onNavigateToHome() },
            modifier = Modifier.fillMaxWidth().padding(bottom = 25.dp)
        ) {
            Text(text = stringResource(id = R.string.label_boton_registrarse))
        }

    }
}