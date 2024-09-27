package com.example.unieventos.viewmodel

import androidx.lifecycle.ViewModel
import com.example.unieventos.models.Role
import com.example.unieventos.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UsersViewModel: ViewModel() {

    private val _users = MutableStateFlow( emptyList<User>() )
    val users: StateFlow< List<User> > = _users.asStateFlow()

    init {
        _users.value = getUsers()
    }

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
                cedula = "123456789",
                nombre = "Mateo",
                direccion = "Calle 123",
                telefono = "123456789",
                correo = "keyser.soze@spacey.com",
                contrasena = "123456",
                role = Role.CLIENT
            ),
            User(
                id = "2",
                cedula = "987654321",
                nombre = "Juan",
                direccion = "Calle 321",
                telefono = "987654321",
                correo = "william.henry.harrison@example-pet-store.com",
                contrasena = "654321",
                role = Role.ADMIN
            ),
            User(
                id = "3",
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