package com.example.unieventos.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.unieventos.R

@Composable
fun NewLoginScreen(){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .offset(y = (-90).dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Logo()

                OutlinedTextField(
                    value = email,
                    singleLine = true,
                    label = {
                        Text(text = "Correo")
                    },
                    onValueChange = {
                        email = it
                    })

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = password,
                    singleLine = true,
                    isError = password.length > 6,
                    visualTransformation = PasswordVisualTransformation(),
                    label = {
                        Text(text = "Contraseña")
                    },
                    onValueChange = {
                        password = it
                    })

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if (email == "nicolas" && password == "123") {
                            Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Incorrecto", Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(text = "Iniciar sesión")
                }


            }

            Text(
                text = "¿Olvidó su contraseña?",
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 25.dp)
                    .clickable {
                        Toast.makeText(context, "lmfao", Toast.LENGTH_SHORT).show()
                    }
            )
        }
    }

}

@Composable
fun Logo() {
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "UniEventos Logo",
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    )
}