package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unieventos.models.Artist
import com.example.unieventos.models.User
import com.example.unieventos.utils.RequestResult
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ArtistViewModel: ViewModel() {

    private val db = Firebase.firestore
    private val collectionPathName = "artists"
    private val _artist = MutableStateFlow( emptyList<Artist>() )
    val artist: StateFlow< List<Artist> > = _artist.asStateFlow()

    private val _authResult = MutableStateFlow<RequestResult?>(null)
    val authResult: StateFlow<RequestResult?> = _authResult.asStateFlow()


    init {
        loadArtists()
    }

    private fun loadArtists() {
        viewModelScope.launch {
            _artist.value = getArtists()
        }
    }

    private suspend fun getArtists(): List<Artist> {
        val snapshot = db.collection(collectionPathName).get().await()
        return snapshot.documents.mapNotNull {
            val artist = it.toObject(Artist::class.java)
            requireNotNull(artist)
            artist.id = it.id
            artist
        }
    }

    fun createArtist(artist: Artist) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { createArtistFirebase(artist) }
                .fold(
                    onSuccess = { RequestResult.Success("Artista Creado") },
                    onFailure = { RequestResult.Failure(it.message.toString()) }
                )
        }
    }

    private suspend fun createArtistFirebase(artist: Artist) {
        viewModelScope.launch {
            db.collection(collectionPathName).add(artist).await()
            _artist.value = getArtists()
        }
    }

    fun updateArtist(newArtist: Artist) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { updateArtistFirebase(newArtist) }
                .fold(
                    onSuccess = { RequestResult.Success("Artista Actualizado") },
                    onFailure = { RequestResult.Failure(it.message.toString()) }
                )
        }
    }

    private suspend fun updateArtistFirebase(newArtist: Artist) {
        viewModelScope.launch {
            db.collection(collectionPathName).document(newArtist.id).set(newArtist).await()
            _artist.value = getArtists()
        }
    }

    fun deleteArtist(artistId: String) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { deleteArtistFirebase(artistId) }
                .fold(
                    onSuccess = { RequestResult.Success("Artista Eliminado") },
                    onFailure = { RequestResult.Failure(it.message.toString()) }
                )
        }
    }

    private suspend fun deleteArtistFirebase(artistId: String) {
        viewModelScope.launch {
            db.collection(collectionPathName).document(artistId).delete().await()
            _artist.value = getArtists()
        }
    }

    suspend fun getArtistById(id: String): Artist? {
        val snapshot = db.collection(collectionPathName).document(id).get().await()
        val artist = snapshot.toObject(Artist::class.java)
        artist?.id = snapshot.id
        return artist
    }

    fun searchArtists(query: String): List<Artist> {
        return _artist.value.filter { it.name.contains(query, ignoreCase = true) }
    }

    suspend fun getFavoriteArtists(userId: String): List<Artist> {
        val snapshot = db.collection("users").document(userId).get().await()
        val user:User = snapshot.toObject(User::class.java) ?: return emptyList()
        val favoriteArtists = artist.value.filter { it.id in user.favoriteArtistsId }
        return favoriteArtists
    }

    fun resetAuthResult() {
        _authResult.value = null
    }

}