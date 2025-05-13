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
    }
}

fun main() {

    Application.launch(HelloApplication::class.java)
}
