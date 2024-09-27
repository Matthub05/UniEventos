package com.example.unieventos.models

data class User(
    val id: String,
    val cedula: String,
    val nombre: String,
    val direccion: String,
    val telefono: String,
    val correo: String,
    val contrasena: String,
    val role: Role
)