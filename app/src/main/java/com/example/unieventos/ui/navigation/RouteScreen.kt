package com.example.unieventos.ui.navigation

import com.example.unieventos.models.Artist
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

sealed class RouteScreen {

    @Serializable
    data object Login : RouteScreen()

    @Serializable
    data object UserHome : RouteScreen()

    @Serializable
    data object Home : RouteScreen()

    @Serializable
    data object SignUp : RouteScreen()

    @Serializable
    data object ForgotPassword : RouteScreen()

    @Serializable
    data object ProfileEdit : RouteScreen()

    @Serializable
    data class CreateEventScreen(val eventId: Int?) : RouteScreen()

    @Serializable
    data class EventDetailScreen(val eventId: Int, val userId: Int) : RouteScreen()

    @Serializable
    data class ArtistDetailScreen(val artistId: Int) : RouteScreen()

    @Serializable
    data object CreateCouponScreen : RouteScreen()

    @Serializable
    data class TicketTransactionScreen(val eventId: Int, val userId: Int) : RouteScreen()

}