package com.example.unieventos.models

import java.util.Date

data class Coupon(
    var id: String = "",
    val description: String = "",
    val code: String = "",
    val startDate: Date = Date(),
    val endDate: Date = Date(),
    val discount: Double = 0.0
)