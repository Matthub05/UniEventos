package com.example.unieventos.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.unieventos.models.ui.AlertType

@Composable
fun AlertMessage(
    type: AlertType,
    message: String,
    modifier: Modifier? = Modifier
) {

    Card (
        modifier = modifier ?: Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (type) {
                AlertType.SUCCESS -> MaterialTheme.colorScheme.primaryContainer
                AlertType.ERROR -> MaterialTheme.colorScheme.errorContainer
                AlertType.WARNING, AlertType.INFO -> MaterialTheme.colorScheme.secondaryContainer
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {

        Row (
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (type) {
                    AlertType.SUCCESS -> Icons.Filled.CheckCircleOutline
                    AlertType.ERROR -> Icons.Filled.ErrorOutline
                    AlertType.WARNING -> Icons.Filled.WarningAmber
                    AlertType.INFO -> Icons.Filled.Info
                },
                contentDescription = null,
                modifier = Modifier.size(26.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = when (type) {
                    AlertType.SUCCESS -> MaterialTheme.colorScheme.onPrimaryContainer
                    AlertType.ERROR -> MaterialTheme.colorScheme.onErrorContainer
                    AlertType.WARNING, AlertType.INFO -> MaterialTheme.colorScheme.onSecondaryContainer
                }
            )
        }

    }

}