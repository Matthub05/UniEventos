package com.example.unieventos.ui.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.unieventos.R
import com.example.unieventos.models.Coupon
import com.example.unieventos.viewmodel.CouponsViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponsSearchBarTop(
    couponsViewModel: CouponsViewModel,
    drawerState: DrawerState
) {
    var searchText by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(false) }
    var searchTextPlaceholder by remember { mutableStateOf("UniEventos Mobile") }
    val scope = rememberCoroutineScope()

    var coupons by remember { mutableStateOf< List<Coupon> >( emptyList() )}

    val searchBarLabel = stringResource(id = R.string.label_buscar)
    LaunchedEffect(key1 = Unit) {
        delay(2000)
        searchTextPlaceholder = searchBarLabel
    }

    LaunchedEffect(searchText) {
        coupons = couponsViewModel.searchCoupons(searchText)
    }

    val searchBarColors = SearchBarDefaults.colors(
        containerColor = Color(0xFF161616), // Set the background color of the search bar
    )

    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 20.dp, top = 1.dp),
        colors = searchBarColors,
        query = searchText,
        onQueryChange = { searchText = it },
        onSearch = {  },
        active = isActive,
        onActiveChange = { isActive = it },
        placeholder = {
            Crossfade(
                targetState = searchTextPlaceholder,
                animationSpec = tween(durationMillis = 500),
                label = ""
            ) { targetSearchTextPlaceholder ->
                Text(targetSearchTextPlaceholder, color = Color.White)
            }
        },
        leadingIcon = @Composable {
            IconButton(onClick = {
                scope.launch {
                    drawerState.open()
                }
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu Icon")
            }
        },
        trailingIcon = {
            if (isActive) {
                IconButton(onClick = {
                    if (searchText.isNotEmpty()) {
                        searchText = ""
                    } else {
                        isActive = false
                    }
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Close Icon")
                }
            }
        },

    ) {
        if (coupons.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(text = stringResource(id = R.string.adv_no_resultados) + " '$searchText'")
            }
        } else {
            if (searchText.isEmpty()) {
                Text(text = stringResource(id = R.string.adv_ingrese_busqueda))
                Spacer(modifier = Modifier.height(7.dp))
            }
            CouponsResultsList(
                paddingValues = PaddingValues(),
                coupons = coupons
            )
        }
    }
}

@Composable
private fun CouponsResultsList(
    paddingValues: PaddingValues,
    coupons: List<Coupon>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 7.dp,
                end = 7.dp,
                top = 7.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        items(coupons) { coupon ->
            CouponItem(
                coupon = coupon
            )
            Spacer(modifier = Modifier.height(7.dp))
        }

    }
}
