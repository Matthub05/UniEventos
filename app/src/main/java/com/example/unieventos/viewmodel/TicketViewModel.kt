package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import com.example.unieventos.models.Coupon
import com.example.unieventos.models.Event
import com.example.unieventos.models.Ticket
import com.example.unieventos.models.TicketStatus
import com.example.unieventos.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import java.util.Date

class TicketViewModel:ViewModel() {

    private val _ticket = MutableStateFlow( emptyList<Ticket>() )
    val ticket: StateFlow< List<Ticket> > =  _ticket.asStateFlow()

    init {
        _ticket.value = getTickets()
    }

    fun addTicketCart(user: User, event: Event, locationId: String, quantity: Int) {
        val location = event.findLocationById(locationId)!!
        val ticket = Ticket(
            id = (_ticket.value.size + 1).toString()    ,
            userId = user.id,
            eventId = event.id,
            eventLocation = location,
            quantity = quantity,
            acquisitionDate = Date(),
            price = location.price * quantity,
            status = TicketStatus.PENDING
        )
        _ticket.value += ticket
    }

    fun deleteTicket(ticket: Ticket) {
        _ticket.value -= ticket
    }

    fun getTicketById(id: String): Ticket? {
        return  _ticket.value.find { it.id == id }
    }

    private fun getTickets(): List<Ticket> {
        return listOf(

        )
    }

}