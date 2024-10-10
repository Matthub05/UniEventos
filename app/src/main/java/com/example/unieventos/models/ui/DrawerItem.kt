package com.example.unieventos.models.ui

import androidx.compose.ui.graphics.vector.ImageVector

data class DrawerItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val onClick: () -> Unit
)
