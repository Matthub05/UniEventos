package com.example.unieventos.models

import java.util.Date

data class Coupon(
    var id: Int,
    val description: String,
    val code: String,
    val startDate: Date,
    val endDate: Date,
    val discount: Double
)