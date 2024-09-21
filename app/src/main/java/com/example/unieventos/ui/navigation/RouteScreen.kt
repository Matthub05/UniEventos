package com.example.unieventos.ui.navigation

import kotlinx.serialization.Serializable

sealed class RouteScreen {

    @Serializable
    data object Login : RouteScreen()

    @Serializable
    data object Home : RouteScreen()

    @Serializable
    data object SignUp : RouteScreen()

    @Serializable
    data object ForgotPassword : RouteScreen()

}