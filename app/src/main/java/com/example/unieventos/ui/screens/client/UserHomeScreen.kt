package com.example.unieventos.ui.screens.client

import android.widget.Space
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unieventos.R
import com.example.unieventos.models.ui.BottomNavigationItem
import com.example.unieventos.models.ui.DrawerItem
import com.example.unieventos.ui.components.NavigationBarCustom
import com.example.unieventos.ui.components.SearchBarTop
import com.example.unieventos.ui.screens.client.navigation.UserRouteScreen
import com.example.unieventos.ui.screens.client.tabs.EventsScreen
import com.example.unieventos.ui.screens.client.tabs.PurchasesScreen
import com.example.unieventos.ui.screens.client.tabs.UserInfoScreen
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    eventsViewModel: EventsViewModel,
    artistViewmodel: ArtistViewModel,
    onLogout: () -> Unit,
    onNavigateToEventDetail: (Int) -> Unit,
    onNavigateToArtistDetail: (Int) -> Unit,
    onNavigateToProfileEdit: () -> Unit
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val items = listOf(
        DrawerItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        DrawerItem(
            title = "Cuenta",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle
        ),
        DrawerItem(
            title = "Ajustes",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings
        ),
        DrawerItem(
            title = "Acerca de",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info
        )

    )

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet (
                modifier = Modifier
                    .width(300.dp),
                drawerContentColor = Color.White,
                drawerContainerColor = Color(0xFF161616),

                ){

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "UniEventos",
                    fontSize = 25.sp,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 20.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                items.forEachIndexed { index, drawerItem ->  
                    NavigationDrawerItem(
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding),
                        label = {
                        Text(text = drawerItem.title)
                    },
                        selected = index == selectedItemIndex,
                        onClick = {
                            selectedItemIndex = index
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if(index == selectedItemIndex) {
                                    drawerItem.selectedIcon
                                } else drawerItem.unselectedIcon,
                                contentDescription = drawerItem.title
                            )
                        }

                    )
                }
            }
        },
        drawerState = drawerState,
    ) {

    Scaffold (
        topBar = {
           if (navBackStackEntry?.destination?.hasRoute(UserRouteScreen.TabInfo::class) == true)
             return@Scaffold

            SearchBarTop(
                onSearchTextChanged = { newText ->
                    println("Search text: $newText")
               },
                drawerState
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
            onNavigateToProfileEdit = onNavigateToProfileEdit,
            onNavigateToArtistDetail = onNavigateToArtistDetail
        )

        }

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
    onNavigateToArtistDetail: (Int) -> Unit,
    onNavigateToProfileEdit: () -> Unit,
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
                onNavigateToEventDetail = onNavigateToEventDetail,
                onNavigateToArtistDetail = onNavigateToArtistDetail
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
