package com.example.unieventos.ui.screens.admin

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.unieventos.ui.components.DatePickerForm

import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TopBarComponent

@Composable
fun CreateCouponScreen(
    onNavigateToHome: () -> Unit
) {

    Scaffold(
        topBar = {
            TopBarComponent(
                text = stringResource(id = R.string.titulo_generacion_cupon),
                onClick = { onNavigateToHome() },
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    ) { innerPadding ->

        CreateEventForm(padding = innerPadding, onNavigateToHome = onNavigateToHome)

    }

}

@Composable
fun CreateEventForm(
    padding: PaddingValues,
    onNavigateToHome: () -> Unit
) {

    var codigo by rememberSaveable { mutableStateOf("") }
    var descripcion by rememberSaveable { mutableStateOf("") }
    var descuento by rememberSaveable { mutableIntStateOf(0) }
    var fecha by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(10.dp))

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = codigo,
            onValueChange = { codigo = it },
            supportingText = stringResource(id = R.string.err_codigo),
            label = stringResource(id = R.string.placeholder_codigo_cupon),
            onValidate = { it.isEmpty() || it.toIntOrNull() == null },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = descripcion,
            onValueChange = { descripcion = it },
            supportingText = stringResource(id = R.string.err_descripcion),
            label = stringResource(id = R.string.placeholder_descripcion),
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = descuento.toString(),
            onValueChange = { descuento = it.toIntOrNull() ?: 0},
            supportingText = stringResource(id = R.string.err_descuento),
            label = stringResource(id = R.string.placeholder_descuento_cupon),
            onValidate = { it.isEmpty() || it.toIntOrNull() == null},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        DatePickerForm(
            modifier = Modifier.fillMaxWidth(),
            value = fecha,
            onValueChange = { fecha = it },
            label = stringResource(id = R.string.placeholder_fecha_vencimiento)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            fontSize = 11.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.adv_generar_cupon)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = { onNavigateToHome() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp),
        ) {
            Text(text = stringResource(id = R.string.btn_registrar_cupon))
        }

    }
}