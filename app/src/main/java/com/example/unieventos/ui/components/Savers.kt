package com.example.unieventos.ui.components

import android.os.Bundle
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import com.example.unieventos.dto.LocationDropdownDTO
import com.example.unieventos.models.Artist
import com.example.unieventos.models.Event
import com.example.unieventos.models.EventLocation
import com.example.unieventos.models.EventSite
import com.example.unieventos.models.Role
import com.example.unieventos.models.Ticket
import com.example.unieventos.models.User
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

            putBundle("eventSite", Bundle().apply {
                putString("name", event.eventSite.name)
                putLong("capacity", event.eventSite.capacity)
                putString("location", event.eventSite.location)
            })

            putStringArray("mediaUrls", event.mediaUrls.toTypedArray())

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

val ArtistSaver = Saver<Artist, Bundle>(
    save = { artist ->
        Bundle().apply {
            putString("id", artist.id)
            putString("name", artist.name)
            putString("genre", artist.genre)
            putString("description", artist.description)
            putString("imageUrl", artist.imageUrl)
        }
    },
    restore = { bundle ->
        Artist(
            id = bundle.getString("id") ?: "",
            name = bundle.getString("name") ?: "",
            genre = bundle.getString("genre") ?: "",
            description = bundle.getString("description") ?: "",
            imageUrl = bundle.getString("imageUrl") ?: ""
        )
    }
)

val UserSaver = Saver<User, Bundle>(
    save = { user ->
        val bundle = Bundle().apply {
            putString("id", user.id)
            putString("cedula", user.cedula)
            putString("name", user.name)
            putString("direction", user.direction)
            putString("phone", user.phone)
            putString("email", user.email)
            putString("password", user.password)
            putSerializable("role", user.role)
            putStringArrayList("favoriteArtistsId", ArrayList(user.favoriteArtistsId))
        }
        bundle
    },
    restore = { bundle ->
        User(
            id = bundle.getString("id") ?: "",
            cedula = bundle.getString("cedula") ?: "",
            name = bundle.getString("name") ?: "",
            direction = bundle.getString("direction") ?: "",
            phone = bundle.getString("phone") ?: "",
            email = bundle.getString("email") ?: "",
            password = bundle.getString("password") ?: "",
            role = bundle.getSerializable("role") as? Role ?: Role.CLIENT,
            favoriteArtistsId = bundle.getStringArrayList("favoriteArtistsId") ?: listOf(),
        )
    }
)

val LocationDropdownDTOSaver = Saver<LocationDropdownDTO, Bundle>(
    save = { locationDropdownDTO ->
        Bundle().apply {
            putInt("idLocation", locationDropdownDTO.idLocation)
            putString("locationName", locationDropdownDTO.locationName)
        }
    },
    restore = { bundle ->
        LocationDropdownDTO(
            idLocation = bundle.getInt("idLocation"),
            locationName = bundle.getString("locationName") ?: ""
        )
    }
)