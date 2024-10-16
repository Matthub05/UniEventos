package com.example.unieventos.models.ui

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavigationItem (
    val title: String,
    val route: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)