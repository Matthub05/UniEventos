package com.example.unieventos.models

import android.net.http.UrlRequest.Status
import java.util.Date

data class Ticket(
    val id: Int,
    val userId: Int,
    val eventId: Int,
    val eventLocation: EventLocation,
    val quantity: Int,
    val acquisitionDate: Date,
    val price: Double,
    var status: TicketStatus
)