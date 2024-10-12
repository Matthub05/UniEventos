package com.example.unieventos.models

import kotlinx.serialization.Serializable

data class Artist(
    val id: String,
    val name: String,
    val genre: String,
    val description: String,
    val imageUrl: String
)
