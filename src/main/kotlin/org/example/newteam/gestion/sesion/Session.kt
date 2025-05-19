package org.example.newteam.gestion.sesion

import javafx.stage.Stage
import org.example.newteam.gestion.models.Usuario
import org.example.newteam.gestion.sesion.Session.currentUser
import org.example.newteam.routes.RoutesManager

/**
 * Objeto que maneja la sesion actual de la aplicacion y en base a eso llama a una vista u otra
 * @property currentUser Usuario actualmente logueado en la aplicación
 */
object Session {
    var currentUser: Usuario? = null

    /**
     *Lanza la vista del usuario
     * @param user [String] El usuario
     * @param password [String] La contraseña
     * @param stage [Stage] El escenario donde se lanza la vista
     * @see [RoutesManager.initUserStage]
     */
    fun launchUser(user: String, password: String, stage: Stage){
        currentUser = Usuario(user, password)
        RoutesManager.initUserStage(stage)
    }

    /**
     * Lanza la vista del administrador
     * @param admin [String] El administrador
     * @param password [String] La contraseña
     * @param stage [Stage] El escenario donde se lanza la vista
     * @see [RoutesManager.initAdminStage]
     */
    fun launchAdmin(admin: String, password: String, stage: Stage){
        currentUser = Usuario(admin, password)
        RoutesManager.initAdminStage(stage)
    }

    /**
     * Volver a la pantaña de inicio de sesion
     * @param stage [Stage] El escenario donde se lanza la vista
     * @see [RoutesManager.initLoginStage]
     */
    fun toLogin(stage: Stage){
        currentUser = null
        RoutesManager.initLoginStage(stage)
    }
}