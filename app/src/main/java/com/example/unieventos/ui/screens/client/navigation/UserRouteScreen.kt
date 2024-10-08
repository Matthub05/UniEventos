package com.example.unieventos.ui.screens.client.navigation

import kotlinx.serialization.Serializable

sealed class UserRouteScreen {

    @Serializable
    data object TabEvents : UserRouteScreen()

    @Serializable
    data object TabPurchases : UserRouteScreen()

    @Serializable
    data object TabInfo : UserRouteScreen()

}