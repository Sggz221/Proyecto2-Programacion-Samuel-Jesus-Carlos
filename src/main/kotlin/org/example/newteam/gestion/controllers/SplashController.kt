package org.example.newteam.gestion.controllers

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.ProgressBar
import javafx.stage.Stage
import org.example.newteam.routes.RoutesManager
import kotlin.concurrent.thread

/**
 * Clase que representa el controlador de la vista de carga (splash) del programa.
 * @property progressBar [ProgressBar] la barra de progreso
 * @property isFinished [Boolean] variable que indica si la barra de progreso ha llegado a su fin
 */
class SplashController {
    @FXML
    lateinit var progressBar: ProgressBar
    var isFinished: Boolean = false

    /**
     * Método automáticamente llamado por JavaFX cuando se crea el [SplashController] asociado al correspondiente .fxml
     * @see initEvents
     */
    fun initialize() {
        thread { isFinished = progressBarInit() }
        initEvents()
    }

    /**
     * Establece la función que tendrá la barra de progreso.
     */
    private fun initEvents(){
        progressBar.progressProperty().addListener { _, _, newValue ->
            if(newValue == 1.0) RoutesManager.initLoginStage(progressBar.scene.window as Stage)
        }
    }

    /**
     * Rellena la barra de progreso
     * @see [progressBar]
     */
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