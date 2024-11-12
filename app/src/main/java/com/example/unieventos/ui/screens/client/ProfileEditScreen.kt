package com.example.unieventos.ui.screens.client

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.models.User
import com.example.unieventos.models.UserUpdateDTO
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.TopBarComponent
import com.example.unieventos.utils.SharedPreferenceUtils
import com.example.unieventos.viewmodel.UsersViewModel

@Composable
fun ProfileEditScreen(
    onNavigateToBack: () -> Unit,
    usersViewModel: UsersViewModel,
) {

    Scaffold (
        topBar = {
            TopBarComponent(
                text = stringResource(id = R.string.titulo_editar_cuenta),
                onClick = { onNavigateToBack() },
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        }
    ) { paddingValues ->

        ProfileEditForm(
            padding =  paddingValues,
            onNavigateToBack = onNavigateToBack,
            usersViewModel = usersViewModel,
        )

    }

}

@Composable
fun ProfileEditForm(
    padding: PaddingValues,
    onNavigateToBack: () -> Unit,
    usersViewModel: UsersViewModel,
) {

    val context = LocalContext.current
    val session = SharedPreferenceUtils.getCurrentUser(context)
    val userId = session?.id
    var user by remember { mutableStateOf<User?>(null) }

    val mensajeError = stringResource(id = R.string.err_campos_vacios)

    var name by rememberSaveable { mutableStateOf("") }
    var direction by rememberSaveable { mutableStateOf("") }
    var phone by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(userId) {
        if (userId != null) user = usersViewModel.getUserById(userId)
        user?.let { loadedUser ->
            name = loadedUser.name
            direction = loadedUser.direction
            phone = loadedUser.phone
        }
    }

    fun camposValidos(): Boolean {
        return name.isNotEmpty() && direction.isNotEmpty() && phone.isNotEmpty()
    }

    fun saveUser() {
        val updatedUser = UserUpdateDTO(
            id = user?.id,
            name = name,
            direction = direction,
            phone = phone,
        )

        usersViewModel.updateUser(updatedUser)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            supportingText = stringResource(id = R.string.label_nombre_invalido),
            label = stringResource(id = R.string.label_nombre),
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = direction,
            onValueChange = { direction = it },
            supportingText = stringResource(id = R.string.label_direccion_invalida),
            label = stringResource(id = R.string.label_direccion),
            onValidate = { it.isEmpty() },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        )

        TextFieldForm(
            modifier = Modifier.fillMaxWidth(),
            value = phone,
            onValueChange = { phone = it },
            supportingText = stringResource(id = R.string.label_telefono_invalido),
            label = stringResource(id = R.string.label_telefono),
            onValidate = { it.isEmpty() || it.toIntOrNull() == null },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onNavigateToBack() },
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
            onClick = {
                if (!camposValidos()) {
                    Toast.makeText(context, mensajeError, Toast.LENGTH_SHORT).show()
                    return@Button
                }
                saveUser()
                onNavigateToBack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp)
        ) {
            Text(text = stringResource(id = R.string.btn_guardar_cambios))
        }

    }
}