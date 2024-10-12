package com.example.unieventos.models

data class User(
    val id: Int,
    val cedula: String,
    var nombre: String,
    var direccion: String,
    var telefono: String,
    val correo: String,
    val contrasena: String,
    val role: Role,
    var tickets: List<Ticket> = listOf(),
)

data class UserUpdateDTO(
    val id: Int?,
    val nombre: String,
    val direccion: String,
    val telefono: String,
)