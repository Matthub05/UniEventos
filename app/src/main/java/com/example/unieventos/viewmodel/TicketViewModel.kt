package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unieventos.models.Coupon
import com.example.unieventos.models.Event
import com.example.unieventos.models.Ticket
import com.example.unieventos.models.TicketStatus
import com.example.unieventos.models.User
import com.example.unieventos.utils.RequestResult
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.Date

class TicketViewModel:ViewModel() {

    private val db = Firebase.firestore
    private val collectionPathName = "tickets"
    private val _ticket = MutableStateFlow( emptyList<Ticket>() )
    val ticket: StateFlow< List<Ticket> > =  _ticket.asStateFlow()

    private val _authResult = MutableStateFlow<RequestResult?>(null)
    val authResult: StateFlow<RequestResult?> = _authResult.asStateFlow()

    init {
        loadTickets()
    }

    private fun loadTickets() {
        viewModelScope.launch {
            _ticket.value = getTickets()
        }
    }

    private suspend fun getTickets(): List<Ticket> {
        val snapshot = db.collection(collectionPathName).get().await()
        return snapshot.documents.mapNotNull {
            val ticket = it.toObject(Ticket::class.java)
            requireNotNull(ticket)
            ticket.id = it.id
            ticket
        }
    }

    fun createTicket(ticket: Ticket, coupon: Coupon? = null) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { createTicketFirebase(ticket) }
                .fold(
                    onSuccess = { RequestResult.Success("Ticket añadido a carrito") },
                    onFailure = { RequestResult.Failure(it.message.toString()) }
                )
        }
    }

    private suspend fun createTicketFirebase(ticket: Ticket) {
        viewModelScope.launch {
            db.collection(collectionPathName).add(ticket).await()
            _ticket.value = getTickets()
        }
    }

    fun deleteTicket(ticketId: String) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { deleteTicketFirebase(ticketId) }
                .fold(
                    onSuccess = { RequestResult.Success("Ticket eliminado") },
                    onFailure = { RequestResult.Failure(it.message.toString()) }
                )
        }
    }

    private suspend fun deleteTicketFirebase(ticketId: String) {
        viewModelScope.launch {
            db.collection(collectionPathName).document(ticketId).delete().await()
            _ticket.value = getTickets()
        }
    }

    suspend fun getTicketById(id: String): Event? {
        val snapshot = db.collection(collectionPathName).document(id).get().await()
        val event = snapshot.toObject(Event::class.java)
        event?.id = snapshot.id
        return event
    }

    suspend fun getUserTicketById(userId: String): List<Ticket>{
        val querySnapshot = db.collection(collectionPathName)
            .whereEqualTo("userId", userId)
            .get()
            .await()

        return querySnapshot.documents.mapNotNull { document ->
            document.toObject(Ticket::class.java)?.apply {
                id = document.id
            }
        }
    }

    fun resetAuthResult() {
        _authResult.value = null
    }

}