package org.example.newteam

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.gestion.models.Usuario
import org.example.newteam.routes.RoutesManager
import java.io.File
import kotlin.concurrent.thread

class HelloApplication : Application() {
    override fun start(stage: Stage) {

        RoutesManager.apply {
            app = this@HelloApplication
        }.run {
            initLoginStage(stage)
            /*
            thread {
                val dao = Dependencies.provideUserDao()
                val hashUser = BCrypt.withDefaults().hashToString(12, "1234".toCharArray())
                val hashAdmin = BCrypt.withDefaults().hashToString(12, "P@ssw0rd".toCharArray())
                val user = Usuario("user", hashUser)
                val admin = Usuario("Admin", hashAdmin)
                dao.saveUser(user)
                dao.saveUser(admin)
            }*/

        }
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}
