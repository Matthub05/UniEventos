package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.unieventos.models.User
import com.example.unieventos.models.Role
import com.example.unieventos.models.UserUpdateDTO
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UsersViewModel: ViewModel() {

    private val db = Firebase.firestore
    private val collectionPathName = "users"
    private val _users = MutableStateFlow( emptyList<User>() )
    val users: StateFlow< List<User> > = _users.asStateFlow()

    init {
        _users.value = getUsers()
    }

//    private fun loadUsers() {
//        viewModelScope.launch {
//            _users.value = getUsers()
//        }
//    }

//    private suspend fun getUsers(): List<User> {
//        val snapshot = db.collection(collectionPathName).get().await()
//        return snapshot.documents.mapNotNull {
//            val user = it.toObject(User::class.java)
//            requireNotNull(user)
//            user.id = it.id
//            user
//        }
//    }

//    suspend fun getUserById(id: String): User? {
//        val snapshot = db.collection(collectionPathName).document(id).get().await()
//        val user = snapshot.toObject(User::class.java)
//        user?.id = snapshot.id
//        return user
//    }

//    fun updateUser(newUser: User) {
//        viewModelScope.launch {
//            db.collection(collectionPathName).document(newUser.id).set(newUser).await()
//            _users.value = getUsers()
//        }
//    }

//    fun deleteUser(userId: String) {
//        viewModelScope.launch {
//            db.collection(collectionPathName).document(userId).delete().await()
//            _users.value = getUsers()
//        }
//    }

    fun getUserById(id: String): User? {
        return _users.value.find { it.id == id }
    }

    fun getUserByCedula(cedula: String): User? {
        return _users.value.find { it.cedula == cedula }
    }

    fun getUserByCorreo(correo: String): User? {
        return _users.value.find { it.correo == correo }
    }

    fun createUser(user: User) {
        _users.value += user
    }

    fun updateUser(updatedUser: UserUpdateDTO): Boolean {
        if (updatedUser.id == null) return false
        val user: User = getUserById(updatedUser.id) ?: return false
        _users.value -= user
        user.nombre = updatedUser.nombre
        user.direccion = updatedUser.direccion
        user.telefono = updatedUser.telefono
        _users.value += user
        return true
    }

    fun loginUser(correo: String, contrasena: String): User? {
        return _users.value.find { it.correo == correo && it.contrasena == contrasena }
    }

    private fun getUsers(): List<User> {
        return listOf(
            User(
                id = "0",
                cedula = "0",
                nombre = "Admin",
                direccion = "Calle 0",
                telefono = "0",
                correo = "root",
                contrasena = "root",
                role = Role.ADMIN
            ),
            User(
                id = "1",
                cedula = "666666666",
                nombre = "Alistair Cockburn",
                direccion = "Calle 612",
                telefono = "963852741",
                correo = "user",
                contrasena = "user",
                role = Role.CLIENT
            ),
            User(
                id = "2",
                cedula = "123456789",
                nombre = "Mateo",
                direccion = "Calle 123",
                telefono = "123456789",
                correo = "keyser.soze@spacey.com",
                contrasena = "123456",
                role = Role.CLIENT
            ),
            User(
                id = "3",
                cedula = "987654321",
                nombre = "Juan",
                direccion = "Calle 321",
                telefono = "987654321",
                correo = "william.henry.harrison@example-pet-store.com",
                contrasena = "654321",
                role = Role.ADMIN
            ),
            User(
                id = "4",
                cedula = "111111111",
                nombre = "Pedro",
                direccion = "Calle 999",
                telefono = "111111111",
                correo = "jhon.doe@example-pet-store.com",
                contrasena = "111111",
                role = Role.CLIENT
            )
        )
    }
}