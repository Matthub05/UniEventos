package com.example.unieventos.ui.screens

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.unieventos.ui.components.TextFieldForm

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {

    var context = LocalContext.current
    var email by rememberSaveable { mutableStateOf("") }
    var emailError by rememberSaveable { mutableStateOf(false) }
    var password by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TextFieldForm(
                value = email,
                onValueChange = { email = it },
                supportingText = "El correo no es válido",
                label = "Correo electrónico",
                onValidate = { !Patterns.EMAIL_ADDRESS.matcher(it).matches() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )

            TextFieldForm(
                value = password,
                onValueChange = { password = it },
                supportingText = "La contraseña no es válida",
                label = "Contraseña",
                onValidate = { it.isEmpty() },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isPassword = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Rellene todos los campos", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    if (email == "admin" && password == "admin") {
                        Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show()
                        onLoginSuccess()
                    } else {
                        Toast.makeText(context, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = "Iniciar sesión")
                Text(text = "Iniciar sesión")
            }
        }

    }

}