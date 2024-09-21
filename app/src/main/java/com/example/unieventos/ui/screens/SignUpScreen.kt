package com.example.unieventos.ui.screens

import android.content.Context
import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TopBarComponent

@Composable
fun SignUpScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToHome: () -> Unit
) {

    val context = LocalContext.current

    Scaffold (
        topBar = {
            TopBarComponent(
                text = stringResource(id = R.string.titulo_registrarse),
                onClick = { onNavigateToLogin() },
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    ) { paddingValues ->

        SignUpForm(paddingValues, context, onNavigateToHome)

    }

}

@Composable
fun SignUpForm(
    padding: PaddingValues,
    context: Context,
    onNavigateToHome: () -> Unit
) {

    var cedula by rememberSaveable { mutableStateOf("") }
    var nombre by rememberSaveable { mutableStateOf("") }
    var direccion by rememberSaveable { mutableStateOf("") }
    var telefono by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var contrasena by rememberSaveable { mutableStateOf("") }
    var contrasenaConfirmacion by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        TextFieldForm(
            value = cedula,
            onValueChange = { cedula = it },
            supportingText = stringResource(id = R.string.label_cedula_invalida),
            label = stringResource(id = R.string.label_cedula),
            onValidate = { it.isEmpty() || it.toIntOrNull() == null },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        TextFieldForm(
            value = nombre,
            onValueChange = { nombre = it },
            supportingText = stringResource(id = R.string.label_nombre_invalido),
            label = stringResource(id = R.string.label_nombre),
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        TextFieldForm(
            value = direccion,
            onValueChange = { direccion = it },
            supportingText = stringResource(id = R.string.label_direccion_invalida),
            label = stringResource(id = R.string.label_direccion),
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        TextFieldForm(
            value = telefono,
            onValueChange = { telefono = it },
            supportingText = stringResource(id = R.string.label_telefono_invalido),
            label = stringResource(id = R.string.label_telefono),
            onValidate = { it.isEmpty() || it.toIntOrNull() == null },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        TextFieldForm(
            value = correo,
            onValueChange = { correo = it },
            supportingText = stringResource(id = R.string.label_correo_invalido),
            label = stringResource(id = R.string.label_correo),
            onValidate = { !Patterns.EMAIL_ADDRESS.matcher(it).matches() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        TextFieldForm(
            value = contrasena,
            onValueChange = { contrasena = it },
            supportingText = stringResource(id = R.string.label_contrasenia_invalida),
            label = stringResource(id = R.string.label_contrasenia),
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isPassword = true
        )

        TextFieldForm(
            value = contrasenaConfirmacion,
            onValueChange = { contrasenaConfirmacion = it },
            supportingText = stringResource(id = R.string.label_confirmar_contrasenia_invalida),
            label = stringResource(id = R.string.label_confirmar_contrasenia),
            onValidate = { it != contrasena },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isPassword = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { onNavigateToHome() }) {
            Text(text = stringResource(id = R.string.label_boton_registrarse))
        }
    }
}