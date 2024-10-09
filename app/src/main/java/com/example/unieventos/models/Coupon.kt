package com.example.unieventos.models

import java.util.Date

data class Coupon(
    val id: Int,
    val description: String,
    val code: String,
    val startDate: Date,
    val endDate: Date,
    val discount: Double
)