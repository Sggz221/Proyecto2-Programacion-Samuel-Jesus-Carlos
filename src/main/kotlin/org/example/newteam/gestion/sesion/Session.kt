package org.example.newteam.gestion.sesion

import javafx.stage.Stage
import org.example.newteam.gestion.models.Usuario
import org.example.newteam.routes.RoutesManager

object Session {
    var currentUser: Usuario? = null

    fun launchUser(user: String, password: String, stage: Stage){
        currentUser = Usuario(user, password)
        RoutesManager.initUserStage(stage)
    }

    fun launchAdmin(admin: String, password: String, stage: Stage){
        currentUser = Usuario(admin, password)
        RoutesManager.initAdminStage(stage)
    }

    fun toLogin(stage: Stage){
        currentUser = null
        RoutesManager.initLoginStage(stage)
    }
}