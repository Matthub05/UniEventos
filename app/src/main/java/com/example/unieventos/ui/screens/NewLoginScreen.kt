package com.example.unieventos.ui.screens

import android.util.Patterns
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unieventos.R
import com.example.unieventos.models.ui.AlertType
import com.example.unieventos.ui.components.AlertMessage
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.utils.RequestResult
import com.example.unieventos.viewmodel.UsersViewModel
import kotlinx.coroutines.delay

@Composable
fun NewLoginScreen(
    usersViewModel: UsersViewModel,
    onNavigateToHome: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
){

    val authResult by usersViewModel.authResult.collectAsState()

    Scaffold (
        floatingActionButton = {
            when(authResult) {
                is RequestResult.Loading -> {  }
                is RequestResult.Failure -> {
                    AlertMessage(
                        type = AlertType.ERROR,
                        message = (authResult as RequestResult.Failure).error,
                        modifier = Modifier.width(318.dp)
                    )
                    LaunchedEffect (Unit) {
                        delay(2000)
                        usersViewModel.resetAuthResult()
                    }
                }
                is RequestResult.Success -> {
                    AlertMessage(
                        type = AlertType.SUCCESS,
                        message = (authResult as RequestResult.Success).message,
                        modifier = Modifier.width(318.dp)
                    )
                    LaunchedEffect (Unit) {
                        delay(2000)
                        onNavigateToHome()
                        usersViewModel.resetAuthResult()
                    }
                }
                null -> { }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        ) {

            LoginForm(
                padding = padding,
                usersViewModel = usersViewModel,
                onNavigateToHome = onNavigateToHome,
                onNavigateToSignUp = onNavigateToSignUp
            )

            Text(
                text = stringResource(id = R.string.label_recuperar_contrasenia),
                color = Color.Gray,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 25.dp)
                    .clickable {
                        onNavigateToForgotPassword()
                    }
            )
        }

    }

}

@Composable
fun LoginForm(
    padding: PaddingValues,
    usersViewModel: UsersViewModel,
    authResult: RequestResult? = null,
    onNavigateToHome: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(start = 20.dp)
            .offset(y = (-140).dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 40.sp,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextFieldForm(
            value = email,
            onValueChange = { email = it },
            supportingText = stringResource(id = R.string.label_correo_invalido),
            label = stringResource(id = R.string.label_correo),
            onValidate = { !Patterns.EMAIL_ADDRESS.matcher(it).matches() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier
                .width(318.dp)
        )

        TextFieldForm(
            value = password,
            onValueChange = { password = it },
            supportingText = stringResource(id = R.string.label_contrasenia_invalida),
            label = stringResource(id = R.string.label_contrasenia),
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isPassword = true,
            modifier = Modifier
                .width(318.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            modifier = Modifier
                .width(318.dp)
                .height(50.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = { usersViewModel.loginUser(email, password)
                keyboardController?.hide()}
        ) {
            Text(text = stringResource(id = R.string.label_boton_login))
        }

        if (authResult is RequestResult.Loading) {
            LinearProgressIndicator(modifier = Modifier.width(318.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .width(318.dp)
                .height(50.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = { onNavigateToSignUp() }
        ) {
            Text(text = stringResource(id = R.string.label_boton_registrarse))
        }

    }
}
