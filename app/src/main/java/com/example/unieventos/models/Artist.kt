package com.example.unieventos.models

import kotlinx.serialization.Serializable

data class Artist(
    val id: Int,
    val name: String,
    val genre: String,
    val description: String,
    val imageUrl: String
)
