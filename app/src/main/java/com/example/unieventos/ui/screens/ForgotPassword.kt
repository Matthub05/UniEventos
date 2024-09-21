package com.example.unieventos.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unieventos.R

import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TopBarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onNavigateToLogin: () -> Unit
) {
    var codigo by rememberSaveable { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopBarComponent(
                text = stringResource(id = R.string.titulo_contrasenia),
                onClick = { onNavigateToLogin() },
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back")
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = R.string.label_confirmacion)
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = codigo,
                onValueChange = { codigo = it },
                supportingText = stringResource(id = R.string.err_codigo),
                label = stringResource(id = R.string.placeholder_codigo),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(id = R.string.btn_recuperar))
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = stringResource(id = R.string.label_nueva_contrasenia)
            )

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = codigo,
                onValueChange = { codigo = it },
                supportingText = stringResource(id = R.string.err_nueva_contrasenia),
                label = stringResource(id = R.string.label_contrasenia),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = stringResource(id = R.string.btn_confirmar_contrasenia))
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
                fontSize = 11.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.adv_enviar_codigo)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 25.dp),
            ) {
                Text(text = stringResource(id = R.string.btn_enviar_codigo))
            }

        }

    }


}
