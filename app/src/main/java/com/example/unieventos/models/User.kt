package com.example.unieventos.models

data class User(
    var id: String = "",
    val cedula: String = "",
    var name: String = "",
    var direction: String = "",
    var phone: String = "",
    val email: String = "",
    val password: String = "",
    val role: Role = Role.CLIENT,
    var tickets: List<Ticket> = listOf(),
    var favoriteArtistsId: List<String> = listOf(),
)

data class UserUpdateDTO(
    val id: String?,
    val name: String,
    val direction: String,
    val phone: String,
)