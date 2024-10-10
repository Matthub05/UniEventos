package com.example.unieventos.models

import kotlinx.serialization.Serializable
import java.util.Date

data class Event(
    var id: Int,
    val title: String,
    val description: String,
    val artistId: Int,
    val category: String,
    val date: Date,
    val eventSite: EventSite,
    val imageUrl: String,
    val mediaUrls: List<String> = listOf(),
    var locations: List<EventLocation> = listOf(),
) {
    fun findLocationById(locationId: Int): EventLocation? {
        return locations.find { it.id == locationId }
    }
}