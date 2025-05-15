package org.example.newteam.gestion.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.ProgressBar
import javafx.stage.Stage
import org.example.newteam.routes.RoutesManager
import kotlin.concurrent.thread

class SplashController {
    @FXML
    lateinit var progressBar: ProgressBar
    var isFinished: Boolean = false

    fun initialize() {
        thread { isFinished = progressBarInit() }
        initEvents()
    }

    private fun initEvents(){
        progressBar.progressProperty().addListener { _, _, newValue ->
            if(newValue == 1.0) RoutesManager.initLoginStage(progressBar.scene.window as Stage)
        }
    }

    private fun progressBarInit(): Boolean {
        progressBar.progress = 0.0
        for (i in 0..100){
            Platform.runLater {
                progressBar.progress = i / 100.0 // Se rellena la barra de progreso exponencialmente
            }
            Thread.sleep(50)
        }
        return true
    }

}