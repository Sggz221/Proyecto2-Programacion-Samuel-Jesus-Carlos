package org.example.newteam

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.example.newteam.gestion.configuration.Configuration
import org.example.newteam.gestion.di.Dependencies
import java.io.File
import kotlin.concurrent.thread

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("/org/example/newteam/hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()

        thread {
            val service = Dependencies.getIntegrantesService()
            val file = File("data", "personal.csv")
            service.importFromFile(file.path)
            service.getAll().forEach { println(it) }
        }
    }
}

fun main() {
    Application.launch(HelloApplication::class.java)
}
