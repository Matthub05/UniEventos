package com.example.unieventos.ui.screens

import android.content.Context
import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.example.unieventos.ui.components.TextFieldForm

@Composable
fun SignUpScreen() {

    val context = LocalContext.current

    Scaffold { paddingValues ->

        SignUpForm(paddingValues, context)

    }

}

@Composable
fun SignUpForm(
    padding: PaddingValues,
    context: Context
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
            supportingText = "La cédula no es válida",
            label = "Cédula",
            onValidate = { it.isEmpty() || it.toIntOrNull() == null },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        TextFieldForm(
            value = nombre,
            onValueChange = { nombre = it },
            supportingText = "El nombre no es válido",
            label = "Nombre completo",
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        TextFieldForm(
            value = direccion,
            onValueChange = { direccion = it },
            supportingText = "La dirección no es válida",
            label = "Dirección",
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        TextFieldForm(
            value = telefono,
            onValueChange = { telefono = it },
            supportingText = "El teléfono no es válido",
            label = "Teléfono",
            onValidate = { it.isEmpty() || it.toIntOrNull() == null },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        TextFieldForm(
            value = correo,
            onValueChange = { correo = it },
            supportingText = "El correo no es válido",
            label = "Correo electrónico",
            onValidate = { !Patterns.EMAIL_ADDRESS.matcher(it).matches() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        TextFieldForm(
            value = contrasena,
            onValueChange = { contrasena = it },
            supportingText = "La contraseña no es válida",
            label = "Contraseña",
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isPassword = true
        )

        TextFieldForm(
            value = contrasenaConfirmacion,
            onValueChange = { contrasenaConfirmacion = it },
            supportingText = "La confirmación de la contraseña no coincide",
            label = "Confirmar contraseña",
            onValidate = { it != contrasena },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isPassword = true
        )

    }
}