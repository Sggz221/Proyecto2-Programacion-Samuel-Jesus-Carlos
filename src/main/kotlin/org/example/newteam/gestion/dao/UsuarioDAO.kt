package org.example.newteam.gestion.dao

import org.example.newteam.gestion.models.Usuario
import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

@RegisterKotlinMapper(Usuario::class)
interface UsuarioDAO {
    @SqlQuery("SELECT password FROM usuarios WHERE username = :username")
    fun getPassword(@Bind("username") username: String): String?

    @SqlUpdate("INSERT INTO usuarios (username, password) VALUES (:username, :password)")
    fun saveUser(@BindBean user: Usuario)
}