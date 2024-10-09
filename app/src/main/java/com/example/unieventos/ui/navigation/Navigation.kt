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
import com.example.unieventos.ui.screens.SignUpScreen
import com.example.unieventos.utils.SharedPreferenceUtils
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.UsersViewModel

@Composable
fun Navigation(
    artistViewModel: ArtistViewModel,
    eventsViewModel: EventsViewModel,
    usersViewModel: UsersViewModel,
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
                onNavigateToEventDetail = { eventId ->
                    navController.navigate(RouteScreen.EventDetailScreen(eventId))
                },
                onNavigateToProfileEdit = { navController.navigate(RouteScreen.ProfileEdit)},
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
                onNavigateToCreateEvent = { navController.navigate(RouteScreen.CreateEventScreen) },
                onNavigateToCreateCoupon = { navController.navigate(RouteScreen.CreateCouponScreen) }
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
                onNavigateToHome = { navController.navigate(RouteScreen.UserHome){
                    popUpTo(0){
                        inclusive = true
                    }
                    launchSingleTop = true
                } }
            )
        }

        composable<RouteScreen.CreateEventScreen> {
            CreateEventScreen(
                onNavigateToHome = { navController.navigate(RouteScreen.Home){
                    popUpTo(0){
                        inclusive = true
                    }
                    launchSingleTop = true
                } }
            )
        }

        composable<RouteScreen.EventDetailScreen> {
            val eventId = it.arguments?.getInt("eventId")
            EventDetailScreen(
                eventId = eventId ?: 0,
                eventsViewModel = eventsViewModel,
                onNavigateToUserHome = { navController.navigate(RouteScreen.UserHome){
                    popUpTo(0){
                        inclusive = true
                    }
                    launchSingleTop = true
                } }
            )
        }

        composable<RouteScreen.CreateCouponScreen> {
            CreateCouponScreen(
                onNavigateToHome = { navController.navigate(RouteScreen.Home){
                    popUpTo(0){
                        inclusive = true
                    }
                    launchSingleTop = true
                } }
            )
        }

    }

}