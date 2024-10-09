package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import com.example.unieventos.models.Artist
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ArtistViewModel: ViewModel() {

    private val _artists = MutableStateFlow( emptyList<Artist>() )
    val artist: StateFlow< List<Artist> > = _artists.asStateFlow()

    init {
        _artists.value = getArtists()
    }

    fun getArtistById(id: Int): Artist? {
        return _artists.value.find { it.id == id }
    }

    fun createArtist(artist: Artist) {
        _artists.value += artist
    }

    private fun getArtists(): List<Artist> {
        return listOf(
            Artist(
                id = 0,
                name = "Dire Straits",
                genre = "Rock",
                description = "Rock band",
                imageUrl = "https://i.scdn.co/image/ab6761610000e5eb4bdaa8c5e65b64f50549c393",
            ),
            Artist(
                id = 1,
                name = "Eve",
                genre = "Rock",
                description = "Vocalist",
                imageUrl = "https://lh3.googleusercontent.com/1BFQt988LS_GupMk7K8412eq4Pa4A_vD5DD8wPqzNSqvwuFTRrXJ87XukcxTUrKAbMeE6M6MHXA_3Q=w544-h544-p-l90-rj",
            ),
            Artist(
                id = 2,
                name = "Lamp",
                genre = "Bossa Nova",
                description = "Band",
                imageUrl = "https://yt3.googleusercontent.com/0oPAhdWUw4yrMQG2EJPeXJ_ccznVtFSFD531szsjtcwpqdUfVcJDQnH-RVvn8vByjv-41A9thg=s900-c-k-c0x00ffffff-no-rj",
            ),
            Artist(
                id = 3,
                name = "Metallica",
                genre = "Metal",
                description = "Band",
                imageUrl = "https://www.ultrabrit.com/wp-content/uploads/2023/01/Metallica.jpg",
            ),
            Artist(
                id = 4,
                name = "Zuttomayo",
                genre = "Pop",
                description = "Pop band",
                imageUrl = "https://cdns-images.dzcdn.net/images/artist/61bcbf8296b1669499064406c534d39d/1900x1900-000000-80-0-0.jpg"
            ),
            Artist(
                id = 5,
                name = "Diomedes Díaz",
                genre = "Ballenato",
                description = "Cantante",
                imageUrl = "https://d2yoo3qu6vrk5d.cloudfront.net/pulzo-lite/images-resized/PP2764126-s-o.webp"
            ),
        )
    }
}