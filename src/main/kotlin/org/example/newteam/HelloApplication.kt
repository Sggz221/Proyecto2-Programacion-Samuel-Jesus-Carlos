package org.example.newteam

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.routes.RoutesManager
import java.io.File
import kotlin.concurrent.thread

class HelloApplication : Application() {
    override fun start(stage: Stage) {

        RoutesManager.apply {
            app = this@HelloApplication
        }.run {
            initMainStage(stage)
        }
        /* thread {
            val service = Dependencies.getIntegrantesService()
            val file = File("data", "personal.csv")
            service.importFromFile(file.path)
            service.getAll().forEach { println(it) }
        }*/
    }
}

fun main() {

    Application.launch(HelloApplication::class.java)
}
