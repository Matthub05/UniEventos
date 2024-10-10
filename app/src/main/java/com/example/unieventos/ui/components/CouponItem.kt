package com.example.unieventos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unieventos.models.Coupon

@Composable
fun CouponItem(
    coupon: Coupon
) {

    Surface (
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(20.dp)
    ) {

        Row (
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .clickable {
                },
        ) {
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8))
                    .background(Color.Transparent)
            ) {
                Icon(
                    imageVector = Icons.Filled.Discount,
                    contentDescription = null,
                    Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = coupon.code,
                    fontSize = 17.sp,
                )
                Text(text = coupon.description,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
                Text(text = "Descuento: " + coupon.discount + " %",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

    }
}