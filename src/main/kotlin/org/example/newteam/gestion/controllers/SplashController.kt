package org.example.newteam.gestion.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.ProgressBar
import kotlin.concurrent.thread

class SplashController {
    @FXML
    lateinit var progressBar: ProgressBar

    fun initialize() {
        thread { progressBarInit() }
    }

    private fun progressBarInit() {
        progressBar.progress = 0.0
        for (i in 0..100){
            Platform.runLater {
                println("Progresando...")
                progressBar.progress = i / 100.0 // Se rellena la barra de progreso exponencialmente
            }
            Thread.sleep(50)
        }
    }

}