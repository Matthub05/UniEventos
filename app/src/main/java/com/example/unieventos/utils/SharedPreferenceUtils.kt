package com.example.unieventos.utils

import android.content.Context
import com.example.unieventos.dto.UserDTO
import com.example.unieventos.models.Role

object SharedPreferenceUtils {

    private const val SESION_PREFERENCE = "sesion"

    fun savePreference(context: Context, idUser: String, rol: Role) {
        val sharedPreferences = context.getSharedPreferences(SESION_PREFERENCE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("idUser", idUser)
        editor.putString("rol", rol.toString())
        editor.apply()
    }

    fun clearPreference(context: Context) {
        val sharedPreferences = context.getSharedPreferences(SESION_PREFERENCE, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    fun getCurrentUser(context: Context): UserDTO? {
        val sharedPreferences = context.getSharedPreferences(SESION_PREFERENCE, Context.MODE_PRIVATE)
        val idUser = sharedPreferences.getString("idUser", "")
        val rol = sharedPreferences.getString("rol", "")
        return if (idUser.isNullOrEmpty() || rol.isNullOrEmpty()) null
            else UserDTO(idUser, Role.valueOf(rol))
    }

}