package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventLocation
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class EventsViewModel:ViewModel() {

    val db = Firebase.firestore
    private val collectionPathName = "events"
    private val _event = MutableStateFlow( emptyList<Event>() )
    val event: StateFlow< List<Event> > = _event.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            _event.value = getEvents()
        }
    }

    private suspend fun getEvents(): List<Event> {
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
            db.collection(collectionPathName).add(event).await()
        }
    }

    fun updateEvent(newEvent: Event): Boolean {
        val event: Event = getEventById(newEvent.id) ?: return false
        _event.value -= event
        _event.value += newEvent
        return true
    }

    fun deleteEvent(event: Event) {
        _event.value -= event
    }

    fun getEventById(id: String): Event? {
        return _event.value.find { it.id == id }
    }

    fun getEventLocationsById(id: String): List<EventLocation> {
        return _event.value.find { it.id == id }!!.locations
    }

    fun searchEvents(query: String): List<Event> {
        return _event.value.filter { it.title.contains(query, ignoreCase = true) }
    }

//    private fun getEvents(): List<Event> {
//        val cal: Calendar = Calendar.getInstance()
//        cal.set(2024, 9, 2)
//        val inicio = cal.time
//
//        return listOf(
//            Event(
//                id = "1",
//                title = "Metallica Reloaded",
//                description = "This event currently has no title or description available. Please check back later for updates and more information about the event details. We appreciate your understanding and patience as we continue to finalize the event schedule.",
//                artistId = "3",
//                category = "Concierto",
//                date = inicio,
//                eventSite = EventSite(
//                    name = "Camp Nou",
//                    capacity = 105000,
//                    location = "Barcelona"
//                ),
//                imageUrl = "https://d31fr2pwly4c4s.cloudfront.net/e/a/3/1794885_9adb8046_metallica-reloaded_eflyer_th.jpg",
//                mediaUrls = listOf(
//                    "https://stpetecatalyst.com/wp-content/uploads/2023/08/beyonce-facebook.jpg",
//                    "https://cdn-p.smehost.net/sites/4788b7ad6b5448c1b90d9322361f98f3/wp-content/uploads/2024/02/IMG_5128.jpeg-1709178256-scaled.jpeg"
//                )
//            ),
//            Event(
//                id = "2",
//                title = "Communiqué",
//                description = "This event currently has no title or description available. Please check back later for updates and more information about the event details. We appreciate your understanding and patience as we continue to finalize the event schedule.",
//                artistId = "0",
//                category = "Concierto",
//                date = inicio,
//                eventSite = EventSite(
//                    name = "Vicente Calderon",
//                    capacity = 60000,
//                    location = "Madrid"
//                ),
//                imageUrl = "https://styles.redditmedia.com/t5_2txlm/styles/communityIcon_drd9ah0kodh31.jpg?format=pjpg&s=e1451732a37df08f245e2a50beeadb31e87043e0",
//                mediaUrls = listOf(
//                    "https://stpetecatalyst.com/wp-content/uploads/2023/08/beyonce-facebook.jpg",
//                    "https://cdn-p.smehost.net/sites/4788b7ad6b5448c1b90d9322361f98f3/wp-content/uploads/2024/02/IMG_5128.jpeg-1709178256-scaled.jpeg"
//                )
//            ),
//            Event(
//                id = "3",
//                title = "Trucupey",
//                description = "This event currently has no title or description available. Please check back later for updates and more information about the event details. We appreciate your understanding and patience as we continue to finalize the event schedule.",
//                artistId = "5",
//                category = "Concierto",
//                date = inicio,
//                eventSite = EventSite(
//                    name = "Giuseppe Meazza",
//                    capacity = 75817,
//                    location = "Milan"
//                ),
//                imageUrl = "https://i1.sndcdn.com/artworks-5bd9ea2c-3f3d-4e67-8a7a-e6d873d8c76b-0-t500x500.jpg",
//                mediaUrls = listOf(
//                    "https://stpetecatalyst.com/wp-content/uploads/2023/08/beyonce-facebook.jpg",
//                    "https://cdn-p.smehost.net/sites/4788b7ad6b5448c1b90d9322361f98f3/wp-content/uploads/2024/02/IMG_5128.jpeg-1709178256-scaled.jpeg"
//                )
//            ),
//            Event(
//                id = "4",
//                title = "Culture",
//                description = "This event currently has no title or description available. Please check back later for updates and more information about the event details. We appreciate your understanding and patience as we continue to finalize the event schedule.",
//                artistId = "1",
//                category = "Concierto",
//                date = inicio,
//                eventSite = EventSite(
//                    name = "Wembley",
//                    capacity = 90000,
//                    location = "Londres"
//                ),
//                imageUrl = "https://res.klook.com/image/upload/v1711094730/uvsqagdqguovch1ocz49.jpg",
//                mediaUrls = listOf(
//                    "https://stpetecatalyst.com/wp-content/uploads/2023/08/beyonce-facebook.jpg",
//                    "https://cdn-p.smehost.net/sites/4788b7ad6b5448c1b90d9322361f98f3/wp-content/uploads/2024/02/IMG_5128.jpeg-1709178256-scaled.jpeg"
//                )
//            )
//        )
//    }

}