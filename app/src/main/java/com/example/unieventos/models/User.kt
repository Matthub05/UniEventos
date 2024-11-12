package com.example.unieventos.models

data class User(
    var id: String = "",
    val cedula: String = "",
    var nombre: String = "",
    var direccion: String = "",
    var telefono: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val role: Role = Role.CLIENT,
    var tickets: List<Ticket> = listOf(),
)

data class UserUpdateDTO(
    val id: String?,
    val nombre: String,
    val direccion: String,
    val telefono: String,
)