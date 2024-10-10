package com.example.unieventos.models

import java.util.Date

data class Event(
    var id: Int,
    val title: String,
    val description: String,
    val artistId: Int,
    val category: String,
    val date: Date,
    val eventSite: EventSite,
    val imageUrl: String
)