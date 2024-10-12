package com.example.unieventos.ui.screens

import android.content.Context
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unieventos.R
import com.example.unieventos.models.Role
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.utils.SharedPreferenceUtils
import com.example.unieventos.viewmodel.UsersViewModel

@Composable
fun NewLoginScreen(
    usersViewModel: UsersViewModel,
    onNavigateToHome: (Role) -> Unit,
    onNavigateToSignUp: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
){

    val context = LocalContext.current

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxWidth()
        ) {

            LoginForm(
                padding = padding,
                context = context,
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
    context: Context,
    usersViewModel: UsersViewModel,
    onNavigateToHome: (Role) -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val credencialesIncorrectasMessage = stringResource(id = R.string.info_credenciales_incorrectas)

    Column(
        modifier = Modifier
            .padding(padding)
            .padding(start = 20.dp)
            .offset(y = (-140).dp)
            .fillMaxSize(),
        //horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Unieventos",
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
            onClick = {
                val user = usersViewModel.loginUser(email, password)

                if (user == null) {
                    Toast.makeText(context, credencialesIncorrectasMessage, Toast.LENGTH_SHORT).show()
                    return@Button
                }

                SharedPreferenceUtils.savePreference(context, user.id, user.role)
                Log.e("Error", "Preferences saved: "+ user.id + " " + user.role)
                onNavigateToHome(user.role)
            }
        ) {
            Text(text = stringResource(id = R.string.label_boton_login))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            modifier = Modifier
                .width(318.dp)
                .height(50.dp),
            shape = RoundedCornerShape(4.dp),
            onClick = {
                onNavigateToSignUp()
            }
        ) {
            Text(text = stringResource(id = R.string.label_boton_registrarse))
        }
    }
}
