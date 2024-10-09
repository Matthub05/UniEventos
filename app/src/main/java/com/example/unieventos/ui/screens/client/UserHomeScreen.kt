package com.example.unieventos.ui.screens.client

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unieventos.R
import com.example.unieventos.models.Artist
import com.example.unieventos.models.BottomNavigationItem
import com.example.unieventos.ui.components.NavigationBarCustom
import com.example.unieventos.ui.components.SearchBarTop
import com.example.unieventos.ui.screens.client.navigation.UserRouteScreen
import com.example.unieventos.ui.screens.client.tabs.EventsScreen
import com.example.unieventos.ui.screens.client.tabs.PurchasesScreen
import com.example.unieventos.ui.screens.client.tabs.UserInfoScreen
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel

@Composable
fun HomeScreen(
    eventsViewModel: EventsViewModel,
    artistViewmodel: ArtistViewModel,
    onLogout: () -> Unit,
    onNavigateToEventDetail: (Int) -> Unit,
    onNavigateToProfileEdit: () -> Unit
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    Scaffold (
        topBar = {
           if (navBackStackEntry?.destination?.hasRoute(UserRouteScreen.TabInfo::class) == true)
             return@Scaffold

            SearchBarTop(
                onSearchTextChanged = { newText ->
                    println("Search text: $newText")
               }
            )
        },
        bottomBar = {
            NavigationBarCustom(
                navController = navController,
                items = listOf(
                    BottomNavigationItem(
                        title = stringResource(id = R.string.nav_Eventos),
                        route = UserRouteScreen.TabEvents,
                        selectedIcon = Icons.Filled.Star,
                        unselectedIcon = Icons.Outlined.StarOutline,
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = stringResource(id = R.string.nav_compras),
                        route = UserRouteScreen.TabPurchases,
                        selectedIcon = Icons.Filled.ShoppingCart,
                        unselectedIcon = Icons.Outlined.ShoppingCart,
                        hasNews = false,
                    ),
                    BottomNavigationItem(
                        title = stringResource(id = R.string.nav_perfil),
                        route = UserRouteScreen.TabInfo,
                        selectedIcon = Icons.Filled.AccountCircle,
                        unselectedIcon = Icons.Outlined.AccountCircle,
                        hasNews = false,
                    ),
                )
            )
        }
    )
    { paddingValues ->

        NavHostUser(
            paddingValues = paddingValues,
            navController = navController,
            eventsViewModel = eventsViewModel,
            artistViewModel = artistViewmodel,
            onLogout = onLogout,
            onNavigateToEventDetail = onNavigateToEventDetail,
            onNavigateToProfileEdit = onNavigateToProfileEdit
        )

    }

}

@Composable
fun NavHostUser(
    paddingValues: PaddingValues,
    navController: NavHostController,
    eventsViewModel: EventsViewModel,
    artistViewModel: ArtistViewModel,
    onLogout: () -> Unit,
    onNavigateToEventDetail: (Int) -> Unit,
    onNavigateToProfileEdit: () -> Unit
) {
    
    NavHost(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        navController = navController,
        startDestination = UserRouteScreen.TabEvents
    ) {
        composable<UserRouteScreen.TabEvents> {
            EventsScreen(
                paddingValues = paddingValues,
                eventsViewModel = eventsViewModel,
                artistViewModel = artistViewModel,
                onNavigateToEventDetail = onNavigateToEventDetail
            )
        }
        composable<UserRouteScreen.TabPurchases> {
            PurchasesScreen(
                paddingValues = paddingValues
            )

        }
        composable<UserRouteScreen.TabInfo> {
            UserInfoScreen(
                paddingValues = paddingValues,
                onLogout = onLogout,
                onNavigateToProfileEdit = onNavigateToProfileEdit
            )
        }
    }
    
}
