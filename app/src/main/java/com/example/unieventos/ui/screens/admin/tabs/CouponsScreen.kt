package com.example.unieventos.ui.screens.admin.tabs

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.unieventos.models.Coupon
import com.example.unieventos.viewmodel.CouponsViewModel

@Composable
fun CouponsScreen(
    paddingValues: PaddingValues,
    couponsViewModel: CouponsViewModel
) {

    val coupons = couponsViewModel.coupon.collectAsState().value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {

        items( coupons ) { coupon ->
            ItemCoupon(coupon = coupon)
        }

    }
}

@Composable
fun ItemCoupon(
    coupon: Coupon
) {
    Text(text = coupon.code)
    Text(text = coupon.description)
}