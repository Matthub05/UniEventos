package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventLocation
import com.example.unieventos.utils.RequestResult
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EventsViewModel:ViewModel() {

    private val db = Firebase.firestore
    private val collectionPathName = "events"
    private val _event = MutableStateFlow( emptyList<Event>() )
    val event: StateFlow< List<Event> > = _event.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _authResult = MutableStateFlow<RequestResult?>(null)
    val authResult: StateFlow<RequestResult?> = _authResult.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _isLoading.value = true
            _event.value = getEvents()
            _isLoading.value = false
        }
    }

    suspend fun getEvents(): List<Event> {
        val snapshot = db.collection(collectionPathName).get().await()
        return snapshot.documents.mapNotNull {
            val event = it.toObject(Event::class.java)
            requireNotNull(event)
            event.id = it.id
            event
        }
    }

    fun createEvent(event: Event) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { createEventFirebase(event) }
                .fold(
                    onSuccess = { RequestResult.Success("Evento Creado") },
                    onFailure = { RequestResult.Failure(it.message.toString()) }
                )
        }
    }

    private suspend fun createEventFirebase(event: Event) {
        viewModelScope.launch {
            db.collection(collectionPathName).add(event).await()
            _event.value = getEvents()
        }
    }

    fun updateEvent(newEvent: Event) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { updateEventFirebase(newEvent) }
                .fold(
                    onSuccess = { RequestResult.Success("Evento Actualizado") },
                    onFailure = { RequestResult.Failure(it.message.toString()) }
                )
        }
    }

    private suspend fun updateEventFirebase(newEvent: Event) {
        viewModelScope.launch {
            db.collection(collectionPathName).document(newEvent.id).set(newEvent).await()
            _event.value = getEvents()
        }
    }

    fun deleteEvent(eventId: String) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { deleteEventFirebase(eventId) }
                .fold(
                    onSuccess = { RequestResult.Success("Evento Eliminado") },
                    onFailure = { RequestResult.Failure(it.message.toString()) }
                )
        }
    }

    private suspend fun deleteEventFirebase(eventId: String) {
        viewModelScope.launch {
            db.collection(collectionPathName).document(eventId).delete().await()
            _event.value = getEvents()
        }
    }

    suspend fun getEventById(id: String): Event? {
        val snapshot = db.collection(collectionPathName).document(id).get().await()
        val event = snapshot.toObject(Event::class.java)
        event?.id = snapshot.id
        return event
    }

    fun getEventLocationsById(id: String): List<EventLocation> {
        return _event.value.find { it.id == id }!!.locations
    }

    fun searchEvents(query: String): List<Event> {
        return _event.value.filter {
            it.title.contains(query, ignoreCase = true)
                    || it.category.contains(query, ignoreCase = true)
                    || it.eventSite.location.contains(query, ignoreCase = true)
        }
    }

    fun resetAuthResult() {
        _authResult.value = null
    }

}