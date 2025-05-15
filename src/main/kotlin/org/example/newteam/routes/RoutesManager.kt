package org.example.newteam.routes

import javafx.application.Application
import javafx.application.Platform
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.image.Image
import javafx.scene.layout.Pane
import javafx.stage.Modality
import javafx.stage.Stage
import javafx.stage.WindowEvent
import org.lighthousegames.logging.logging
import java.io.InputStream
import java.net.URL

object RoutesManager {
    private lateinit var mainStage: Stage // Escena principal de la app
    private lateinit var _activeStage: Stage // Escena cargada actualmente en la vista
    val activeStage: Stage
        get() = _activeStage
    lateinit var app: Application

    val logger = logging()
    enum class Vistas(val path: String) {
        ADMIN("views/MainNewteamAdmin.fxml"),
        USER("views/MainNewteamUser.fxml"),
        SPLASH("views/SplashNewTeam.fxml"),
        LOGIN("views/LoginNewTeam.fxml"),
        ABOUT("views/AboutNewTeam.fxml")
    }

    fun initMainStage(stage: Stage) {
        logger.debug { "Iniciando main stage" }
        val fxmlLoader = FXMLLoader(getResource(Vistas.ADMIN.path))
        val parentRoot = fxmlLoader.load<Pane>() // Ponemos tipo Pane porque todos los contenedores de javaFX heredan de este
        val scene = Scene(parentRoot, 1200.0, 600.0)
        stage.title = "NewTeam Manager"
        stage.isResizable = false
        stage.icons.add(Image(getResourceAsStream("media/app-icon.png")))
        stage.scene = scene
        stage.centerOnScreen()
        stage.setOnCloseRequest { onAppExit(event = it) }
        mainStage = stage // Escena principal...
        _activeStage = stage
        mainStage.show()
    }

    fun initSplashStage() {
        logger.debug { "Iniciando splash screen" }
        val fxmlLoader = FXMLLoader(getResource(Vistas.SPLASH.path))
        val parentRoot = fxmlLoader.load<Pane>()
        val myScene = Scene(parentRoot, 600.0, 330.0)
        val stage = Stage()
        stage.title = "Cargando..."
        stage.scene = myScene
        stage.initOwner(mainStage)
        stage.centerOnScreen()
        stage.isIconified = false
        stage.initModality(Modality.WINDOW_MODAL)
        stage.icons.add(Image(getResourceAsStream("media/app-icon.png")))
        stage.isResizable = false
        stage.show()
    }

    fun initAboutStage() {
        logger.debug { "Iniciando about" }
        val fxmlLoader = FXMLLoader(getResource(Vistas.ABOUT.path))
        val parentRoot = fxmlLoader.load<Pane>()
        val myScene = Scene(parentRoot, 574.0, 265.0)
        val stage = Stage()
        stage.title = "Acerca de NewTeam Manager"
        stage.scene = myScene
        stage.initOwner(mainStage)
        stage.centerOnScreen()
        stage.isIconified = false
        stage.initModality(Modality.WINDOW_MODAL)
        stage.icons.add(Image(getResourceAsStream("media/app-icon.png")))
        stage.isResizable = false
        stage.show()
    }

    fun initLoginStage(stage: Stage) {
        logger.debug { "Iniciando login stage" }
        val fxmlLoader = FXMLLoader(getResource(Vistas.LOGIN.path))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 400.0, 650.0)
        stage.title = "Iniciar sesión"
        stage.isResizable = false
        stage.icons.add(Image(getResourceAsStream("media/app-icon.png")))
        stage.scene = scene
        stage.centerOnScreen()
        mainStage = stage
        _activeStage = stage
        mainStage.show()
    }

    fun getResource(resource: String): URL {
        return app::class.java.getResource(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso: $resource")
    }

    fun getResourceAsStream(resource: String): InputStream {
        return app::class.java.getResourceAsStream(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso como stream: $resource")
    }

    fun onAppExit(
        title: String = "Salir de ${mainStage.title}?",
        headerText: String = "¿Estás seguro de que quieres salir de ${mainStage.title}?",
        contentText: String = "Si sales, se cerrará la aplicación y perderás todos los datos no guardados",
        event: WindowEvent? = null
    ) {
        logger.debug { "Cerrando formulario" }
        // Cerramos la aplicación
        val alerta = Alert(Alert.AlertType.CONFIRMATION)
        alerta.title = title
        alerta.headerText = headerText
        alerta.contentText = contentText
        alerta.showAndWait().ifPresent { opcion ->
            if (opcion == ButtonType.OK) {
                Platform.exit()
            } else event?.consume()
        }
    }
}