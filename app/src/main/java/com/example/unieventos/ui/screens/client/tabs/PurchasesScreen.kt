package com.example.unieventos.ui.screens.client.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PurchasesScreen(
    paddingValues: PaddingValues
) {
    Box (
        modifier = Modifier.padding(paddingValues).fillMaxSize()
    ) {
        Text(text = "Compras ?¿?¿")
    }
}