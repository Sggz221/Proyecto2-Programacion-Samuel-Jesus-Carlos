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
import org.example.newteam.gestion.controllers.SplashController
import org.lighthousegames.logging.logging
import java.io.InputStream
import java.net.URL

/**
 * Objeto que maneja las llamadas a las vistas del programa
 * @property mainStage [Stage] escena principal de la app
 * @property activeStage [Stage] escena cargada actualmente en la vista
 * @property
 */
object RoutesManager {
    private lateinit var mainStage: Stage // Escena principal de la app
    private lateinit var _activeStage: Stage // Escena cargada actualmente en la vista
    val activeStage: Stage
        get() = _activeStage
    lateinit var app: Application

    val logger = logging()

    /**
     * Clase que almacena las rutas de las vistas
     * @param path La ruta a especificar
     * @property ADMIN Vista de administrador
     * @property USER Vista de usuario
     * @property LOGIN Vista de inicio de sesion
     * @property ABOUT Vista de Sobre nosotros
     * @property SPLASH Vista de splash screen al incio de la app
     */
    enum class Vistas(val path: String) {
        ADMIN("views/MainNewteamAdmin.fxml"),
        USER("views/MainNewteamUser.fxml"),
        SPLASH("views/SplashNewTeam.fxml"),
        LOGIN("views/LoginNewTeam.fxml"),
        ABOUT("views/AboutNewTeam.fxml")
    }

    /**
     * Inicia la vista de administrador
     * @param stage [Stage] vista de administrador
     * @see [Vistas.ADMIN]
     */
    fun initAdminStage(stage: Stage) {
        logger.debug { "Iniciando admin stage" }
        val fxmlLoader = FXMLLoader(getResource(Vistas.ADMIN.path))
        val parentRoot = fxmlLoader.load<Pane>() // Ponemos tipo Pane porque todos los contenedores de javaFX heredan de este
        val scene = Scene(parentRoot, 990.0, 675.0)
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

    /**
     * Inicia la vista del usuario
     * @param stage [Stage] vista del usuario
     * @see [Vistas.USER]
     */
    fun initUserStage(stage: Stage) {
        logger.debug { "Iniciando user stage" }
        val fxmlLoader = FXMLLoader(getResource(Vistas.USER.path))
        val parentRoot = fxmlLoader.load<Pane>()
        val scene = Scene(parentRoot, 970.0, 600.0)
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

    /**
     * Funcion que llama a el Splash Screen y lo muestra
     * @param stage [Stage]
     * @see [Vistas.SPLASH]
     */
    fun initSplashStage(stage: Stage) {
        logger.debug { "Iniciando splash screen" }
        val fxmlLoader = FXMLLoader(getResource(Vistas.SPLASH.path))
        val parentRoot = fxmlLoader.load<Pane>()
        val myScene = Scene(parentRoot, 600.0, 330.0)
        stage.title = "Cargando..."
        stage.scene = myScene
        stage.centerOnScreen()
        stage.icons.add(Image(getResourceAsStream("media/app-icon.png")))
        stage.isResizable = false
        mainStage = stage
        _activeStage = stage
        mainStage.show()
    }

    /**
     * Inicia la vista de acerca de
     * @param stage [Stage] vista de acerca de
     * @see [Vistas.ABOUT]
     */
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
    /**
     * Funcion que llama al Login y lo muestra
     * @param stage [Stage]
     * @see [Vistas.LOGIN]
     */
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
        stage.setOnCloseRequest {  }
        mainStage = stage
        _activeStage = stage
        mainStage.show()
    }

    /**
     * Recupera un recurso a partir de la ruta del mismo
     * @param resource Ruta del recurso a recuperar
     * @return el recurso recuperado
     */
    fun getResource(resource: String): URL {
        return app::class.java.getResource(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso: $resource")
    }

    /**
     * Funcion que recibe por parametro la ruta de un archivo y lo devuelve como un Stream de datos
     * @param resource [String] La ruta del recurso almacenada en resources
     * @return [InputStream] El recurso
     * @throws RuntimeException
     */
    fun getResourceAsStream(resource: String): InputStream {
        return app::class.java.getResourceAsStream(resource)
            ?: throw RuntimeException("No se ha encontrado el recurso como stream: $resource")
    }

    /**
     * Inicia una ventana modal que pide confirmación para salir de la aplicación
     * @param title título de la ventana
     * @param headerText texto de la cabecera de la ventana
     * @param contentText texto del contenido de la ventana
     * @param event evento de la ventana
     */
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