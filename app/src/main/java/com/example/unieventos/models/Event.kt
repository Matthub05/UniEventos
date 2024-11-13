package com.example.unieventos.models

import java.util.Date

data class Event(
    var id: String = "",
    val title: String = "",
    val description: String = "",
    val artistId: String = "",
    val category: String = "",
    val date: Date = Date(),
    val eventSite: EventSite = EventSite(),
    val imageUrl: String = "",
    val mediaUrls: List<String> = listOf(),
    var locations: List<EventLocation> = listOf(),
) {
    fun findLocationById(locationId: Int): EventLocation? {
        return locations.find { it.id == locationId }
    }
}