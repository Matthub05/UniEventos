package com.example.unieventos.ui.screens.admin.navigation

import kotlinx.serialization.Serializable

sealed class AdminRouteScreen {

    @Serializable
    data object TabEvents : AdminRouteScreen()

    @Serializable
    data object TabArtists : AdminRouteScreen()

    @Serializable
    data object TabCoupons : AdminRouteScreen()
}