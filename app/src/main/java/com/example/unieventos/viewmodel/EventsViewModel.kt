package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import com.example.unieventos.models.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EventsViewModel:ViewModel() {

    private val _event = MutableStateFlow( emptyList<Event>() )
    val event: StateFlow< List<Event> > = _event.asStateFlow()

    init {
        _event.value = getEvents()
    }

    fun createEvent(event: Event) {
        _event.value += event
    }

    fun deleteEvent(event: Event) {
        _event.value -= event
    }

    fun getEventById(id: String): Event? {
        return _event.value.find { it.id == id }
    }

    fun searchEvents(query: String): List<Event> {
        return _event.value.filter { it.title.contains(query, ignoreCase = true) }
    }

    private fun getEvents(): List<Event> {
        return listOf(
            Event(
                id = "1",
                title = "Concierto banda rock",
                description = "Concierto",
                artist = "Banda rock",
                category = "Concierto"
            ),
            Event(
                id = "2",
                title = "Charla triste",
                description = "Charla",
                artist = "Charla",
                category = "Charla"
            ),
            Event(
                id = "3",
                title = "Charla feliz",
                description = "Charla",
                artist = "Charla",
                category = "Charla"
            ),
            Event(
                id = "4",
                title = "Concierto banda jazz",
                description = "Concierto",
                artist = "Banda jazz",
                category = "Concierto"
            ),
            Event(
                id = "5",
                title = "Concierto banda pop",
                description = "Concierto",
                artist = "Banda pop",
                category = "Concierto"
            )
        )
    }

}