package com.example.unieventos.models

import java.util.Date

data class Ticket(
    var id: String = "",
    val userId: String = "",
    val eventId: String = "",
    val eventLocation: EventLocation = EventLocation(),
    val quantity: Int = 0,
    val acquisitionDate: Date = Date(),
    var price: Double = 0.0,
    var status: TicketStatus = TicketStatus.DEFAULT
)