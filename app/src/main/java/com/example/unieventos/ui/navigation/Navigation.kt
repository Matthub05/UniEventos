package com.example.unieventos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unieventos.ui.screens.AdminHomeScreen
import com.example.unieventos.ui.screens.CreateCouponScreen
import com.example.unieventos.ui.screens.CreateEventScreen
import com.example.unieventos.ui.screens.EventDetailScreen
import com.example.unieventos.ui.screens.ForgotPasswordScreen
import com.example.unieventos.ui.screens.HomeScreen
import com.example.unieventos.ui.screens.NewLoginScreen
import com.example.unieventos.ui.screens.ProfileEditScreen
import com.example.unieventos.ui.screens.SignUpScreen
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.UsersViewModel

@Composable
fun Navigation(
    eventsViewModel: EventsViewModel,
    usersViewModel: UsersViewModel,
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteScreen.Login
    ) {

        composable<RouteScreen.Login> {
            NewLoginScreen(
                usersViewModel = usersViewModel,
                onNavigateToAdminHome = { navController.navigate(RouteScreen.Home){
                    popUpTo(0){
                        inclusive = true
                    }
                    launchSingleTop = true
                } },
                onNavigateToUserHome = { navController.navigate(RouteScreen.UserHome) {
                    popUpTo(0) {
                        inclusive = true
                    }
                    launchSingleTop = true
                } },
                onNavigateToSignUp = { navController.navigate(RouteScreen.SignUp) },
                onNavigateToForgotPassword = { navController.navigate(RouteScreen.ForgotPassword) }
            )
        }

        composable<RouteScreen.UserHome> {
            HomeScreen(
                eventsViewModel = eventsViewModel,
                onNavigateToEventDetail = { eventId ->
                    navController.navigate(RouteScreen.EventDetailScreen(eventId))
                },
            )
        }

        composable<RouteScreen.Home> {
            AdminHomeScreen(
                eventsViewModel = eventsViewModel,
                onNavigateToProfileEdit = { navController.navigate(RouteScreen.ProfileEdit)},
                onNavigateToCreateEvent = { navController.navigate(RouteScreen.CreateEventScreen) },
                onNavigateToCreateCoupon = { navController.navigate(RouteScreen.CreateCouponScreen) }
            )
        }

        composable<RouteScreen.SignUp> {
            SignUpScreen(
                onNavigateToLogin = { navController.navigate(RouteScreen.Login) },
                onNavigateToHome = { navController.navigate(RouteScreen.Home){
                    popUpTo(0){
                        inclusive = true
                    }
                    launchSingleTop = true
                } }
            )
        }

        composable<RouteScreen.ForgotPassword> {
            ForgotPasswordScreen(
                onNavigateToLogin = { navController.navigate(RouteScreen.Login) }
            )
        }

        composable<RouteScreen.ProfileEdit> {
            ProfileEditScreen(
                onNavigateToHome = { navController.navigate(RouteScreen.Home){
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
            val eventId = it.arguments?.getString("eventId")
            EventDetailScreen(
                eventId = eventId ?: "",
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