package com.example.unieventos.models

import android.net.http.UrlRequest.Status
import java.util.Date

data class Ticket(
    val id: String,
    val userId: String,
    val eventId: String,
    val eventLocation: EventLocation,
    val quantity: Int,
    val acquisitionDate: Date,
    val price: Double,
    var status: TicketStatus
)