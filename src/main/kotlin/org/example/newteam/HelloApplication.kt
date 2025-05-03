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
        thread {
            val service = Dependencies.getIntegrantesService()
            val file = File("data", "personal.csv")
            service.importFromFile(file.path)
            service.getAll().forEach { println(it) }
            val file2 = File("backup", "personal.xml")
            service.exportToFile(file2.path)
            val file3 = File("backup", "personal.json")
            service.exportToFile(file3.path)
            val file4 = File("backup", "personal.bin")
            service.exportToFile(file4.path)
        }
    }
}

fun main() {

    Application.launch(HelloApplication::class.java)
}
