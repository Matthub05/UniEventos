package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unieventos.models.User
import com.example.unieventos.models.UserUpdateDTO
import com.example.unieventos.utils.RequestResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UsersViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    private val collectionPathName = "users"

    private val _authResult = MutableStateFlow<RequestResult?>(null)
    val authResult: StateFlow<RequestResult?> = _authResult.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun createUser(user: User) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = kotlin.runCatching { createUserFirebase(user) }
                .fold(
                    onSuccess = { RequestResult.Success("Usuario creado") },
                    onFailure = { handleAuthError(it) }
                )
        }
    }

    private suspend fun createUserFirebase(user: User) {
        val response = auth.createUserWithEmailAndPassword(user.email, user.password).await()
        val userId = response.user?.uid ?: throw Exception("No se pudo crear el usuario")

        val userSave = user.copy(id = userId, password = "")

        db.collection(collectionPathName).document(userId).set(userSave).await()
    }

    suspend fun getUserById(id: String): User? {
        val snapshot = db.collection(collectionPathName).document(id).get().await()
        val user = snapshot.toObject(User::class.java)
        user?.id = snapshot.id
        return user
    }

    fun updateUser(newUser: UserUpdateDTO) {
        viewModelScope.launch {
            if (newUser.id == null) return@launch
            val user = getUserById(newUser.id) ?: return@launch
            val updatedUser = user.copy(
                name = newUser.name,
                direction = newUser.direction,
                phone = newUser.phone,
            )
            db.collection(collectionPathName).document(newUser.id).set(updatedUser).await()
        }
    }

    fun deleteUser(userId: String) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = runCatching { deleteUserFirebase(userId) }
                .fold(
                    onSuccess = { RequestResult.Success("eliminado") }, // TODO cambiar eso
                    onFailure = { handleAuthError(it) }
                )
        }
    }

    private suspend fun deleteUserFirebase(userId: String) {
        db.collection(collectionPathName).document(userId).delete().await()
    }

    fun loginUser(correo: String, contrasena: String) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = runCatching { loginFirebase(correo, contrasena) }
                .fold(
                    onSuccess = { RequestResult.Success("Bienvenido") },
                    onFailure = { handleAuthError(it) }
                )
        }
    }

    private suspend fun loginFirebase(email: String, password: String) {
        val response = auth.signInWithEmailAndPassword(email, password).await()
        val userId = response.user?.uid ?: throw Exception("No se pudo iniciar sesión")

        val user = getUserById(userId)
        _currentUser.value = user
    }

    fun saveFavoriteArtist(artistId: String, userId: String) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = runCatching { saveFavoriteArtistFirebase(artistId, userId) }
                .fold(
                    onSuccess = { wasAdded ->
                        val message = if (wasAdded) {
                            "Agregado a favoritos"
                        } else {
                            "Eliminado de favoritos"
                        }
                        RequestResult.Success(message)
                    },
                    onFailure = { handleDbError(it) }
                )
        }
    }

    private suspend fun saveFavoriteArtistFirebase(artistId: String, userId: String): Boolean {
        val user = getUserById(userId) ?: return false
        val favoriteArtists = user.favoriteArtistsId.toMutableList()
        val wasAdded = if (artistId !in favoriteArtists) {
            favoriteArtists.add(artistId)
            true
        } else {
            favoriteArtists.remove(artistId)
            false
        }
        val updatedUser = user.copy(favoriteArtistsId = favoriteArtists)
        db.collection(collectionPathName).document(userId).set(updatedUser).await()
        _currentUser.value = updatedUser
        return wasAdded
    }

    fun saveVisitedHistory(eventId: String, userId: String) {
        viewModelScope.launch {
            _authResult.value = RequestResult.Loading
            _authResult.value = runCatching { saveVisitedHistoryFirebase(eventId, userId) }
                .fold(
                    onSuccess = { wasAdded ->
                        RequestResult.Success("Done")
                    },
                    onFailure = { handleDbError(it) }
                )
        }
    }

    private suspend fun saveVisitedHistoryFirebase(eventId: String, userId: String) {
        val user = getUserById(userId)
            val visitedHistory = user?.visitHistory?.toMutableList()

            if (visitedHistory != null) {
                if (visitedHistory.contains(eventId)) {
                    if (visitedHistory[0] == eventId) {
                        return
                    } else {
                        visitedHistory.remove(eventId)
                    }
                }

                if (visitedHistory.size > 4) {
                    visitedHistory.removeAt(visitedHistory.size - 1)
                }

                visitedHistory.add(0, eventId)
            }

        val updatedUser = visitedHistory?.let { user.copy(visitHistory = it) }
        if (updatedUser != null) {
            db.collection(collectionPathName).document(userId).set(updatedUser).await()
        }
        _currentUser.value = updatedUser
    }


    fun resetAuthResult() {
        _authResult.value = null
    }

    private fun handleAuthError(e: Throwable): RequestResult.Failure {
        val errorMessage = when (e) {
            is FirebaseAuthException -> {
                when (e.errorCode) {
                    "ERROR_INVALID_EMAIL" -> "Correo electrónico no válido"
                    "ERROR_USER_NOT_FOUND" -> "Usuario no encontrado con ese correo"
                    "ERROR_WRONG_PASSWORD" -> "Contraseña incorrecta"
                    "ERROR_EMAIL_ALREADY_IN_USE" -> "Ya existe una cuenta con ese correo"
                    "ERROR_INVALID_CREDENTIAL" -> "Credenciales inválidas"
                    "ERROR_WEAK_PASSWORD" -> "Contraseña débil"
                    else -> "Error de autenticación: ${e.errorCode}"
                }
            }
            else -> "Error al iniciar sesión"
        }
        return RequestResult.Failure(errorMessage)
    }

    private fun handleDbError(e: Throwable): RequestResult.Failure {
        val errorMessage = when (e) {
            is FirebaseAuthException -> {
                when (e.errorCode) {
                    else -> "Error en la base de datos: ${e.errorCode}"
                }
            }
            else -> "Error"
        }
        return RequestResult.Failure(errorMessage)
    }
}