package com.example.unieventos.ui.screens

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unieventos.R
import com.example.unieventos.ui.components.DatePickerForm

import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TopBarComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEventScreen(
    onNavigateToHome: () -> Unit
) {
    val scrollState = rememberScrollState()
    var codigo by rememberSaveable { mutableStateOf("") }
    var fecha by rememberSaveable { mutableStateOf("") }

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

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Detalles generales"
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

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = codigo,
                onValueChange = { codigo = it },
                supportingText = stringResource(id = R.string.err_codigo),
                label = stringResource(id = R.string.placeholder_codigo),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = codigo,
                onValueChange = { codigo = it },
                supportingText = stringResource(id = R.string.err_codigo),
                label = stringResource(id = R.string.placeholder_codigo),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            DatePickerForm(
                value = fecha,
                onValueChange = {},
                label = "fecha",
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                text = "Recinto"
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

            TextFieldForm(
                modifier = Modifier.fillMaxWidth(),
                value = codigo,
                onValueChange = { codigo = it },
                supportingText = stringResource(id = R.string.err_codigo),
                label = stringResource(id = R.string.placeholder_codigo),
                onValidate = { it.isEmpty() || it.toIntOrNull() == null },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
