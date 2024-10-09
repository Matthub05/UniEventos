package com.example.unieventos.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    text: String,
    onClick: () -> Unit,
    icon: @Composable () -> Unit
) {
    LargeTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
        ),
        title = {
            Text(
                text = text,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },

        navigationIcon = {
            IconButton(onClick = onClick) {
                icon()
            }
        },
    )
}