package com.example.unieventos.ui.screens.admin

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material.icons.outlined.Stars
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unieventos.R
import com.example.unieventos.models.BottomNavigationItem
import com.example.unieventos.ui.components.NavigationBarCustom
import com.example.unieventos.ui.screens.admin.navigation.AdminRouteScreen
import com.example.unieventos.ui.screens.admin.tabs.AdminEventsScreen
import com.example.unieventos.ui.screens.admin.tabs.CouponsScreen
import com.example.unieventos.viewmodel.EventsViewModel

@Composable
fun AdminHomeScreen(
    eventsViewModel: EventsViewModel,
    onLogout: () -> Unit,
    onNavigateToCreateEvent: () -> Unit,
    onNavigateToCreateCoupon: () -> Unit
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold (
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (navBackStackEntry?.destination?.hasRoute(AdminRouteScreen.TabEvents::class) == true)
                        onNavigateToCreateEvent()
                    else
                        onNavigateToCreateCoupon()
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = {
            NavigationBarCustom(
                navController = navController,
                items = listOf(
                    BottomNavigationItem(
                        title = stringResource(id = R.string.nav_Eventos),
                        route = AdminRouteScreen.TabEvents,
                        selectedIcon = Icons.Filled.Star,
                        unselectedIcon = Icons.Outlined.StarOutline,
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = stringResource(id = R.string.nav_cupones),
                        route = AdminRouteScreen.TabCoupons,
                        selectedIcon = Icons.Filled.Stars,
                        unselectedIcon = Icons.Outlined.Stars,
                        hasNews = false,
                    )
                )
            )
        }
    ) { paddingValues ->

        NavHostAdmin(
            paddingValues = paddingValues,
            navController = navController,
            onLogout = onLogout,
        )

    }
}

@Composable
fun NavHostAdmin(
    paddingValues: PaddingValues,
    navController: NavHostController,
    onLogout: () -> Unit,
) {

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = AdminRouteScreen.TabEvents
    ) {

        composable<AdminRouteScreen.TabEvents> {
            AdminEventsScreen(
                paddingValues = paddingValues,
                onLogout = onLogout,
            )
        }
        composable<AdminRouteScreen.TabCoupons> {
            CouponsScreen(
                paddingValues = paddingValues,
            )

        }

    }

}