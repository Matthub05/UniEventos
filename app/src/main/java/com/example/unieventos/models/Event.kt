package com.example.unieventos.models

data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val artistId: Int,
    val category: String,
    val imageUrl: String
)