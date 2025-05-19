package org.example.newteam.gestion.models

/**
 * Clase que representa un usuario con nombre y contraseña
 * @param username El nombre de usuario
 * @param password La contraseña
 */
data class Usuario(
    val username: String,
    val password: String,
)
