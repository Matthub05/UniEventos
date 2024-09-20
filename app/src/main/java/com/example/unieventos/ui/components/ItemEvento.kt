package com.example.unieventos.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemEvento(
    nombre: String,
    ciudad: String,
    color: Color = MaterialTheme.colorScheme.secondary
) {

    Surface (
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        color = color,
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(10.dp)
    ) {

        Row (
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image (
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp),
                imageVector = Icons.Rounded.AccountCircle,
                contentDescription = "Imagen del usuario"
            )

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(text = nombre, fontSize = 20.sp)
                Text(text = ciudad)
            }

        }

    }
}