package com.example.unieventos.ui.components

import android.os.Bundle
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventLocation
import com.example.unieventos.models.EventSite
import com.example.unieventos.models.Ticket
import java.util.Date

val EventSaver = Saver<Event, Bundle>(
    save = { event ->
        Bundle().apply {
            putString("id", event.id)
            putString("title", event.title)
            putString("description", event.description)
            putString("artistId", event.artistId)
            putString("category", event.category)
            putLong("date", event.date.time)
            putString("imageUrl", event.imageUrl)

            // Save EventSite
            putBundle("eventSite", Bundle().apply {
                putString("name", event.eventSite.name)
                putLong("capacity", event.eventSite.capacity)
                putString("location", event.eventSite.location)
            })

            // Save mediaUrls list
            putStringArray("mediaUrls", event.mediaUrls.toTypedArray())

            // Save locations list as array of Bundles
            val locationBundles = event.locations.map { location ->
                Bundle().apply {
                    putInt("id", location.id)
                    putString("name", location.name)
                    putInt("places", location.places)
                    putDouble("price", location.price)
                }
            }.toTypedArray()
            putParcelableArray("locations", locationBundles)
        }
    },
    restore = { bundle ->
        Event(
            id = bundle.getString("id") ?: "",
            title = bundle.getString("title") ?: "",
            description = bundle.getString("description") ?: "",
            artistId = bundle.getString("artistId") ?: "",
            category = bundle.getString("category") ?: "",
            date = Date(bundle.getLong("date")),
            imageUrl = bundle.getString("imageUrl") ?: "",
            eventSite = bundle.getBundle("eventSite")?.let { siteBundle ->
                EventSite(
                    name = siteBundle.getString("name") ?: "",
                    capacity = siteBundle.getLong("capacity"),
                    location = siteBundle.getString("location") ?: ""
                )
            } ?: EventSite(),
            mediaUrls = bundle.getStringArray("mediaUrls")?.toList() ?: listOf(),
            locations = (bundle.getParcelableArray("locations") as? Array<Bundle>)?.map { locationBundle ->
                EventLocation(
                    id = locationBundle.getInt("id"),
                    name = locationBundle.getString("name") ?: "",
                    places = locationBundle.getInt("places"),
                    price = locationBundle.getDouble("price")
                )
            } ?: emptyList()
        )
    }
)

val EventLocationSaver = Saver<EventLocation, Bundle>(
    save = { location ->
        Bundle().apply {
            putInt("id", location.id)
            putString("name", location.name)
            putInt("places", location.places)
            putDouble("price", location.price)
        }
    },
    restore = { bundle ->
        EventLocation(
            id = bundle.getInt("id"),
            name = bundle.getString("name") ?: "",
            places = bundle.getInt("places"),
            price = bundle.getDouble("price")
        )
    }
)

