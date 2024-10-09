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

    fun getEventById(id: Int): Event? {
        return _event.value.find { it.id == id }
    }

    fun searchEvents(query: String): List<Event> {
        return _event.value.filter { it.title.contains(query, ignoreCase = true) }
    }

    private fun getEvents(): List<Event> {
        return listOf(
            Event(
                id = 1,
                title = "Metallica Reloaded",
                description = "Concierto",
                artistId = 3,
                category = "Concierto",
                imageUrl = "https://d31fr2pwly4c4s.cloudfront.net/e/a/3/1794885_9adb8046_metallica-reloaded_eflyer_th.jpg"
            ),
            Event(
                id = 2,
                title = "Communiqué",
                description = "Concierto",
                artistId = 0,
                category = "Concierto",
                imageUrl = "https://styles.redditmedia.com/t5_2txlm/styles/communityIcon_drd9ah0kodh31.jpg?format=pjpg&s=e1451732a37df08f245e2a50beeadb31e87043e0"
            ),
            Event(
                id = 3,
                title = "Trucupey",
                description = "Concierto",
                artistId = 5,
                category = "Concierto",
                imageUrl = "https://i1.sndcdn.com/artworks-5bd9ea2c-3f3d-4e67-8a7a-e6d873d8c76b-0-t500x500.jpg"
            ),
            Event(
                id = 4,
                title = "Culture",
                description = "Concierto",
                artistId = 1,
                category = "Concierto",
                imageUrl = "https://res.klook.com/image/upload/v1711094730/uvsqagdqguovch1ocz49.jpg"
            )
        )
    }

}