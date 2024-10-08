package com.example.unieventos.ui.screens.client.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unieventos.R

@Composable
fun UserInfoScreen(
    paddingValues: PaddingValues,
    onLogout: () -> Unit,
    onNavigateToProfileEdit: () -> Unit,
) {
        
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        Image (
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(150.dp)
                .height(150.dp),
            imageVector = Icons.Filled.AccountCircle,
            colorFilter = ColorFilter.tint(Color(255, 255, 255, 255)),
            contentDescription = null
        )

        Text(
            text = "Nombre de Usuario",
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = MaterialTheme.typography.headlineMedium.fontSize
        )
        Text(text = "informacion adicional")

        Spacer(modifier = Modifier.weight(1f))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onNavigateToProfileEdit() }
        ) {
            Text(text = stringResource(id = R.string.btn_editar_perfil))
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 25.dp),
            onClick = { onLogout() },
            colors = ButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.errorContainer,
                disabledContainerColor = MaterialTheme.colorScheme.tertiary,
                disabledContentColor = MaterialTheme.colorScheme.tertiaryContainer
            )
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.Logout, contentDescription = null)
            Text(text = stringResource(id = R.string.btn_cerrar_sesion))
        }
    }

}