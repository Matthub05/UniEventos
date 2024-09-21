package com.example.unieventos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.unieventos.ui.screens.ForgotPasswordScreen
import com.example.unieventos.ui.screens.HomeScreen
import com.example.unieventos.ui.screens.NewLoginScreen
import com.example.unieventos.ui.screens.ProfileEditScreen
import com.example.unieventos.ui.screens.SignUpScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = RouteScreen.Login
    ) {

        composable<RouteScreen.Login> {
            NewLoginScreen(
                onNavigateToHome = { navController.navigate(RouteScreen.Home) },
                onNavigateToSignUp = { navController.navigate(RouteScreen.SignUp) },
                onNavigateToForgotPassword = { navController.navigate(RouteScreen.ForgotPassword) }
            )
        }

        composable<RouteScreen.Home> {
            HomeScreen(
                onNavigateToProfileEdit = { navController.navigate(RouteScreen.ProfileEdit) }
            )
        }

        composable<RouteScreen.SignUp> {
            SignUpScreen(
                onNavigateToLogin = { navController.navigate(RouteScreen.Login) },
                onNavigateToHome = { navController.navigate(RouteScreen.Home) }
            )
        }

        composable<RouteScreen.ForgotPassword> {
            ForgotPasswordScreen(
                onNavigateToLogin = { navController.navigate(RouteScreen.Login) }
            )
        }

        composable<RouteScreen.ProfileEdit> {
            ProfileEditScreen(
                onNavigateToHome = { navController.navigate(RouteScreen.Home) }
            )
        }

    }

}