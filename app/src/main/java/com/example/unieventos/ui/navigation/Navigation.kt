package com.example.unieventos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unieventos.models.Role
import com.example.unieventos.ui.screens.admin.AdminHomeScreen
import com.example.unieventos.ui.screens.admin.CreateCouponScreen
import com.example.unieventos.ui.screens.admin.CreateEventScreen
import com.example.unieventos.ui.screens.client.EventDetailScreen
import com.example.unieventos.ui.screens.ForgotPasswordScreen
import com.example.unieventos.ui.screens.client.HomeScreen
import com.example.unieventos.ui.screens.NewLoginScreen
import com.example.unieventos.ui.screens.client.ProfileEditScreen
import com.example.unieventos.ui.screens.client.ArtistDetailsScreen
import com.example.unieventos.ui.screens.SignUpScreen
import com.example.unieventos.ui.screens.client.TicketTransactionScreen
import com.example.unieventos.utils.SharedPreferenceUtils
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.CouponsViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.TicketViewModel
import com.example.unieventos.viewmodel.UsersViewModel

@Composable
fun Navigation(
    artistViewModel: ArtistViewModel,
    eventsViewModel: EventsViewModel,
    ticketViewModel: TicketViewModel,
    usersViewModel: UsersViewModel,
    couponViewModel: CouponsViewModel
) {

    val navController = rememberNavController()

    var startDestination: RouteScreen = RouteScreen.Login
    val context = LocalContext.current
    val sesion = SharedPreferenceUtils.getCurrentUser(context)

    if (sesion != null) {
        startDestination = when (sesion.rol) {
            Role.ADMIN -> RouteScreen.Home
            Role.CLIENT -> RouteScreen.UserHome
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
        //startDestination = RouteScreen.CreateEventScreen
    ) {

        composable<RouteScreen.Login> {
            NewLoginScreen(
                usersViewModel = usersViewModel,
                onNavigateToHome = { role ->
                    val route = when (role) {
                        Role.ADMIN -> RouteScreen.Home
                        Role.CLIENT -> RouteScreen.UserHome
                    }

                    navController.navigate(route){
                        popUpTo(0){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToSignUp = { navController.navigate(RouteScreen.SignUp) },
                onNavigateToForgotPassword = { navController.navigate(RouteScreen.ForgotPassword) }
            )
        }

        composable<RouteScreen.UserHome> {
            HomeScreen(
                eventsViewModel = eventsViewModel,
                artistViewmodel = artistViewModel,
                onLogout = {
                    SharedPreferenceUtils.clearPreference(context)
                    navController.navigate(RouteScreen.Login){
                        popUpTo(0){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToEventDetail = { eventId, userId ->
                    navController.navigate(RouteScreen.EventDetailScreen(eventId, userId))
                },
                onNavigateToProfileEdit = {
                    navController.navigate(RouteScreen.ProfileEdit)
                                          },
                onNavigateToArtistDetail = { artistId ->
                    navController.navigate(RouteScreen.ArtistDetailScreen(artistId))
                },
                userId = sesion!!.id,
                ticketViewModel = ticketViewModel,
                usersViewModel = usersViewModel
            )
        }

        composable<RouteScreen.Home> {
            AdminHomeScreen(
                eventsViewModel = eventsViewModel,
                onLogout = {
                    SharedPreferenceUtils.clearPreference(context)
                    navController.navigate(RouteScreen.Login){
                        popUpTo(0){
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onNavigateToCreateEvent = { eventId: Int? ->
                    navController.navigate(RouteScreen.CreateEventScreen(eventId))
                },
                onNavigateToCreateCoupon = { navController.navigate(RouteScreen.CreateCouponScreen) },
                couponsViewModel = couponViewModel,
                artistViewModel = artistViewModel
            )
        }

        composable<RouteScreen.SignUp> {
            SignUpScreen(
                onNavigateToBack = {
                    navController.popBackStack()
                },
                usersViewModel
            )
        }

        composable<RouteScreen.ForgotPassword> {
            ForgotPasswordScreen(
                onNavigateToLogin = { navController.navigate(RouteScreen.Login) }
            )
        }

        composable<RouteScreen.ProfileEdit> {
            ProfileEditScreen(
                onNavigateToBack = { navController.popBackStack() },
                usersViewModel = usersViewModel,
                userId = sesion!!.id
            )
        }

        composable<RouteScreen.CreateEventScreen> {
            val eventId = it.arguments?.getInt("eventId")
            CreateEventScreen(
                eventId = if (eventId == 0) null else eventId,
                onNavigateToBack = {
                    navController.popBackStack()
                },
                eventsViewModel = eventsViewModel,
                artistViewModel = artistViewModel
            )
        }

        composable<RouteScreen.ArtistDetailScreen> {
            val artistId = it.arguments?.getInt("artistId")
            ArtistDetailsScreen(
                artistId = artistId ?: 0,
                artistViewModel = artistViewModel,
                onNavigateToUserHome = { navController.navigate(RouteScreen.UserHome){
                    popUpTo(0){
                        inclusive = true
                    }
                    launchSingleTop = true
                } }
            )
        }


        composable<RouteScreen.EventDetailScreen> {
            val eventId = it.arguments?.getInt("eventId")
            val userId = it.arguments?.getInt("userId")
            EventDetailScreen(
                eventId = eventId ?: 0,
                userId = userId ?: 0,
                eventsViewModel = eventsViewModel,
                onNavigateToTransaction = { eventId , userId->
                    navController.navigate(RouteScreen.TicketTransactionScreen(eventId, userId))
                                          },
                onNavigateToUserHome = { navController.navigate(RouteScreen.UserHome){
                    popUpTo(0){
                        inclusive = true
                    }
                    launchSingleTop = true
                }

                }
            )
        }

        composable<RouteScreen.TicketTransactionScreen> {
            val eventId = it.arguments?.getInt("eventId")
            val userId = it.arguments?.getInt("userId")
            TicketTransactionScreen(
                eventId = eventId ?: 0,
                userId = userId ?: 0,
                couponViewModel = couponViewModel,
                eventsViewModel = eventsViewModel,
                onNavigateToBack = {
                    navController.popBackStack()
                },
                usersViewModel = usersViewModel,
                ticketViewModel = ticketViewModel
            )
        }

        composable<RouteScreen.CreateCouponScreen> {
            CreateCouponScreen(
                onNavigateToBack = { navController.popBackStack() },
                couponViewModel = couponViewModel
            )
        }

    }

}