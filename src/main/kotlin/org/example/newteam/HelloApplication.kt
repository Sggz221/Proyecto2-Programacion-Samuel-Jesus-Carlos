package org.example.newteam

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import org.example.newteam.gestion.dao.IntegranteEntity
import org.example.newteam.gestion.database.JdbiManager
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.gestion.repositories.EquipoRepositoryImpl
import sun.security.jca.GetInstance.getService

class HelloApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(HelloApplication::class.java.getResource("hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Hello!"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    //Application.launch(HelloApplication::class.java)
    val service = Dependencies.getIntegrantesService()

    service.importFromFile()
}