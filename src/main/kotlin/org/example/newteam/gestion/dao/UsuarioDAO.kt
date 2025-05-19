package org.example.newteam.gestion.dao

import org.example.newteam.gestion.models.Usuario
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

/**
 * Interfaz que representa el DAO que realiza la consulta de datos de inicio de sesión sobre la tabla que guarda los usuarios registrados y sus contraseñas.
 */
@RegisterKotlinMapper(Usuario::class)
interface UsuarioDAO {
    /**
     * Obtiene la contraseña de un usuario
     * @return La contraseña hasheada, en caso de existir dicho usuario, null en caso contrario.
     */
    @SqlQuery("SELECT password FROM usuarios WHERE username = :username")
    fun getPassword(@Bind("username") username: String): String?

    /**
     * Añade un usuario en la tabla
     * @see [Usuario]
     */
    @SqlUpdate("INSERT INTO usuarios (username, password) VALUES (:username, :password)")
    fun saveUser(@BindBean user: Usuario)
}