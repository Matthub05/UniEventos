package com.example.unieventos.models

import kotlinx.serialization.Serializable

data class EventLocation(
    val id: Int,
    val name: String,
    val places: Int,
    val price: Double,
)