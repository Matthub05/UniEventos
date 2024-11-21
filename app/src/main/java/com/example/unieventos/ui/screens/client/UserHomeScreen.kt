package com.example.unieventos.ui.screens.client

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PeopleAlt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.unieventos.R
import com.example.unieventos.models.Coupon
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventItemDestination
import com.example.unieventos.models.User
import com.example.unieventos.models.ui.AlertType
import com.example.unieventos.models.ui.BottomNavigationItem
import com.example.unieventos.models.ui.DrawerItem
import com.example.unieventos.ui.components.AlertMessage
import com.example.unieventos.ui.components.CouponSaver
import com.example.unieventos.ui.components.EventSaver
import com.example.unieventos.ui.components.NavigationBarCustom
import com.example.unieventos.ui.components.PurchasesSearchBarTop
import com.example.unieventos.ui.components.SearchBarTop
import com.example.unieventos.ui.components.SleekButton
import com.example.unieventos.ui.components.SmallSleekButton
import com.example.unieventos.ui.components.TextFieldForm
import com.example.unieventos.ui.components.UserSaver
import com.example.unieventos.ui.components.ValidateTextFieldForm
import com.example.unieventos.ui.screens.client.navigation.UserRouteScreen
import com.example.unieventos.ui.screens.client.tabs.EventsScreen
import com.example.unieventos.ui.screens.client.tabs.PurchasesScreen
import com.example.unieventos.ui.screens.client.tabs.UserInfoScreen
import com.example.unieventos.utils.Formatters
import com.example.unieventos.utils.RequestResult
import com.example.unieventos.utils.SharedPreferenceUtils
import com.example.unieventos.viewmodel.ArtistViewModel
import com.example.unieventos.viewmodel.CouponsViewModel
import com.example.unieventos.viewmodel.EventsViewModel
import com.example.unieventos.viewmodel.TicketViewModel
import com.example.unieventos.viewmodel.UsersViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    eventsViewModel: EventsViewModel,
    artistViewmodel: ArtistViewModel,
    ticketViewModel: TicketViewModel,
    usersViewModel: UsersViewModel,
    couponsViewModel: CouponsViewModel,
    onLogout: () -> Unit,
    onNavigateToEventDetail: (String, String) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToArtistDetail: (String) -> Unit,
    onNavigateToProfileEdit: () -> Unit,
    onNavigateToFavoriteArtists: () -> Unit,
    onNavigateToShoppingHistory: () -> Unit,
) {

    val context = LocalContext.current
    val session = SharedPreferenceUtils.getCurrentUser(context)
    var user = rememberSaveable(saver = UserSaver) { User() }

    var showConfirmationDialog by rememberSaveable { mutableStateOf(false) }
    var couponObject by rememberSaveable(stateSaver = CouponSaver) {
        mutableStateOf(Coupon())
    }
    var coupon by rememberSaveable { mutableStateOf("")}
    var cartTotal by rememberSaveable { mutableStateOf(0.0) }
    var cartHeader by rememberSaveable { mutableStateOf("") }
    cartHeader = Formatters.formatPrice(cartTotal)

    var isValid by rememberSaveable { mutableStateOf(false) }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }
    val authResult by ticketViewModel.authResult.collectAsState()
    val items = listOf(
        DrawerItem(
            title = stringResource(id = R.string.nav_home),
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            onClick = {

            }
        ),
        DrawerItem(
            title = stringResource(id = R.string.label_artistas_favoritos),
            selectedIcon = Icons.Filled.People,
            unselectedIcon = Icons.Outlined.PeopleAlt,
            onClick = { onNavigateToFavoriteArtists() }
        ),
        DrawerItem(
            title = stringResource(id = R.string.lbl_compras),
            selectedIcon = Icons.Filled.ShoppingBag,
            unselectedIcon = Icons.Outlined.ShoppingBag,
            onClick = {
                 onNavigateToShoppingHistory()
            }
        ),
        DrawerItem(
            title = stringResource(id = R.string.btn_cerrar_sesion),
            selectedIcon = Icons.AutoMirrored.Filled.Logout,
            unselectedIcon = Icons.AutoMirrored.Outlined.Logout,
            onClick = { onLogout() }
        ),
        DrawerItem(
            title = stringResource(id = R.string.nav_acerca_de),
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info,
            onClick = {

            }
        )

    )

    if (session != null) {
        LaunchedEffect(session.id) {
            user = usersViewModel.getUserById(session.id)!!
            cartTotal = ticketViewModel.totalCartFirebase(session.id)
        }

    }



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
                    text = stringResource(id = R.string.app_name),
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
                            drawerItem.onClick()
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
        floatingActionButton = {
            var showFab by remember { mutableStateOf(false) }

            if (navBackStackEntry?.destination?.hasRoute(UserRouteScreen.TabPurchases::class) == true) {
                LaunchedEffect(key1 = Unit) {
                    delay(500)
                    showFab = true
                }
            } else {
                showFab = false
            }
            if (showFab){
                FloatingActionButton(
                    containerColor = Color.White,
                    onClick = {
                        showConfirmationDialog = true
                    }
                ) {
                    Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
                }
            }
        },

        topBar = {
            if (navBackStackEntry?.destination?.hasRoute(UserRouteScreen.TabInfo::class) == true) {
                return@Scaffold
            } else if (navBackStackEntry?.destination?.hasRoute(UserRouteScreen.TabEvents::class) == true) {
                SearchBarTop(
                    eventsViewModel = eventsViewModel,
                    artistViewModel = artistViewmodel,
                    destination = EventItemDestination.DETAIL.name,
                    onNavigateToEventDetail = onNavigateToEventDetail,
                    drawerState = drawerState
                )
            } else if (navBackStackEntry?.destination?.hasRoute(UserRouteScreen.TabPurchases::class) == true) {
                PurchasesSearchBarTop(
                    ticketViewModel = ticketViewModel,
                    eventsViewModel = eventsViewModel,
                    drawerState = drawerState
                )
            }
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

        Box(modifier = Modifier.fillMaxSize()) {

            if (showConfirmationDialog) {
                Dialog(onDismissRequest = {
                    showConfirmationDialog = false
                }) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(0.98f)
                            .wrapContentHeight(),
                        shape = RoundedCornerShape(6.dp),
                        color = Color(0xFF161616)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),

                        ) {

                            Text(
                                text = stringResource(id = R.string.lbl_total),
                                fontSize = 24.sp,
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onBackground,
                            )
                            Text(
                                text =  cartHeader,
                                fontSize = 40.sp,
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(3.dp))

                                ValidateTextFieldForm(
                                    value = coupon,
                                    onValueChange = {
                                        coupon = it
                                        scope.launch {
                                            isValid = couponsViewModel.validateCoupon(coupon, user)
                                            if (isValid) {
                                                couponsViewModel.getCouponByCode(coupon)
                                                    .also {
                                                        if (it != null) {
                                                            couponObject = it
                                                        }
                                                    }
                                                cartHeader =
                                                    Formatters.formatPrice((cartTotal  - (cartTotal * (couponObject.discount / 100))))
                                            } else {
                                                cartHeader = Formatters.formatPrice(cartTotal)
                                            }
                                        }




                                                    },
                                    successSupportingText =
                                    stringResource(id = R.string.txt_discount) + couponObject.discount +  stringResource(id = R.string.txt_applied),
                                    oppositeSupportingText = stringResource(id = R.string.txt_coupon),

                                    label = "Coupon",
                                    onValidate = isValid,
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                                )


                            Text(
                                text = stringResource(id = R.string.txt_disclaimer),
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray,
                                modifier = Modifier.fillMaxWidth(),
                            )
                            Spacer(modifier = Modifier.height(3.dp))

                                SleekButton(text = stringResource(id = R.string.btn_finish)) {
                                    if (session != null) {
                                        ticketViewModel.emptyCart(userId = session.id)//couponId = couponObject.code)
                                    }
                                }

                                SleekButton(text = stringResource(id = R.string.btn_cancelar)) {
                                    showConfirmationDialog = false
                                }

                            Box {
                                when (authResult) {
                                    is RequestResult.Loading -> {}
                                    is RequestResult.Failure -> {
                                        AlertMessage(
                                            type = AlertType.ERROR,
                                            message = (authResult as RequestResult.Failure).error,
                                            modifier = Modifier
                                                .width(318.dp)
                                                .align(Alignment.BottomCenter)
                                                .zIndex(1f)
                                                .padding(bottom = 20.dp)
                                        )
                                        LaunchedEffect(Unit) {
                                            delay(1000)
                                            ticketViewModel.resetAuthResult()
                                        }
                                    }
                                    is RequestResult.Success -> {
                                        AlertMessage(
                                            type = AlertType.SUCCESS,
                                            message = (authResult as RequestResult.Success).message,
                                            modifier = Modifier
                                                .width(318.dp)
                                                .align(Alignment.BottomCenter)
                                                .zIndex(2f)
                                                .padding(bottom = 20.dp)
                                        )
                                        LaunchedEffect(Unit) {
                                            delay(1000)
                                            onNavigateToHome()
                                            ticketViewModel.resetAuthResult()
                                        }
                                    }
                                    null -> {}
                                }
                            }



                        }
                    }
                }


            }
        }

        NavHostUser(
            paddingValues = paddingValues,
            navController = navController,
            eventsViewModel = eventsViewModel,
            artistViewModel = artistViewmodel,
            usersViewModel = usersViewModel,
            onLogout = onLogout,
            onNavigateToEventDetail = onNavigateToEventDetail,
            onNavigateToProfileEdit = onNavigateToProfileEdit,
            onNavigateToArtistDetail = onNavigateToArtistDetail,
            userId = session!!.id,
            ticketViewModel = ticketViewModel
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
    ticketViewModel: TicketViewModel,
    usersViewModel: UsersViewModel,
    userId: String,
    onLogout: () -> Unit,
    onNavigateToEventDetail: (String, String) -> Unit,
    onNavigateToArtistDetail: (String) -> Unit,
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
                onNavigateToArtistDetail = onNavigateToArtistDetail,
                userId = userId,
                usersViewModel = usersViewModel
            )
        }
        composable<UserRouteScreen.TabPurchases> {
            PurchasesScreen(
                paddingValues = paddingValues,
                ticketViewModel = ticketViewModel,
                eventsViewModel = eventsViewModel,
                userId = userId
            )

        }
        composable<UserRouteScreen.TabInfo> {
            UserInfoScreen(
                paddingValues = paddingValues,
                onLogout = onLogout,
                onNavigateToProfileEdit = onNavigateToProfileEdit,
                usersViewModel = usersViewModel
            )
        }
    }
    
}
