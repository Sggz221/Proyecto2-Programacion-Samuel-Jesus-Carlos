package org.example.newteam.gestion.controllers

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.event.Event
import javafx.fxml.FXML
import javafx.scene.Cursor.DEFAULT
import javafx.scene.Cursor.WAIT
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.stage.FileChooser
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteam.gestion.viewmodels.EquipoViewModel
import org.example.newteam.routes.RoutesManager

class NewTeamController (
    private var viewModel: EquipoViewModel
) {
    /* Menu */
    @FXML
    lateinit var exitButton: MenuItem
    @FXML
    lateinit var exportBinButton: MenuItem
    @FXML
    lateinit var exportXmlButton: MenuItem
    @FXML
    lateinit var exportJsonButton: MenuItem
    @FXML
    lateinit var exportCsvButton: MenuItem
    @FXML
    lateinit var importBinButton: MenuItem
    @FXML
    lateinit var importXmlButton: MenuItem
    @FXML
    lateinit var importJsonButton: MenuItem
    @FXML
    lateinit var importCsvButton: MenuItem
    @FXML
    lateinit var aboutButton: MenuItem

    /* Detalle */

    // Comunes
    @FXML
    lateinit var paisField: TextField
    @FXML
    lateinit var salarioField: TextField
    @FXML
    lateinit var incorporacionDP: DatePicker
    @FXML
    lateinit var nacimientoDP: DatePicker
    @FXML
    lateinit var apellidosField: TextField
    @FXML
    lateinit var nombreField: TextField

    //Jugadores
    @FXML
    lateinit var minutosField: TextField
    @FXML
    lateinit var partidosField: TextField
    @FXML
    lateinit var golesField: TextField
    @FXML
    lateinit var pesoField: TextField
    @FXML
    lateinit var alturaField: TextField
    @FXML
    lateinit var dorsalField: TextField
    @FXML
    lateinit var Posicion: ToggleGroup
    @FXML
    lateinit var radioPortero: RadioButton
    @FXML
    lateinit var radioDefensa: RadioButton
    @FXML
    lateinit var radioCentro: RadioButton
    @FXML
    lateinit var radioDelantero: RadioButton

    // Entrenadores
    @FXML
    lateinit var Especialidad: ToggleGroup
    @FXML
    lateinit var radioPorteros: RadioButton
    @FXML
    lateinit var radioAsistente: RadioButton
    @FXML
    lateinit var radioPrincipal: RadioButton

    /* Main */
    @FXML
    lateinit var colSalario: TableColumn<Any, Any> // TODO: quitar los Any
    @FXML
    lateinit var colEspecialidad: TableColumn<Any, Any> // TODO: quitar los Any
    @FXML
    lateinit var colRol: TableColumn<Any, Any> // TODO: quitar los Any
    @FXML
    lateinit var colNombre: TableColumn<Any, Any> // TODO: quitar los Any
    @FXML
    lateinit var listIntegrantes: TableView<Any> // TODO: quitar los Any
    @FXML
    lateinit var sortBySalario: MenuItem
    @FXML
    lateinit var sortByNombre: MenuItem
    @FXML
    lateinit var sortByNothing: MenuItem
    @FXML
    lateinit var filterByEntrenadores: MenuItem
    @FXML
    lateinit var filterByJugadores: MenuItem
    @FXML
    lateinit var filterByNothing: MenuItem
    @FXML
    lateinit var searchBar: TextField



    fun initialize() {
        initEvents()
    }

    private fun initEvents() {
        aboutButton.setOnAction {
            RoutesManager.initAboutStage()
        }
        exitButton.setOnAction {
            RoutesManager.onAppExit()
        }
        importCsvButton.setOnAction { onImportarCSVAction() }
    }

    private fun onImportarCSVAction() {
        FileChooser().run {
            title = "Importar integrantes"
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
            extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
            extensionFilters.add(FileChooser.ExtensionFilter("XML", "*.xml"))
            extensionFilters.add(FileChooser.ExtensionFilter("BIN", "*.bin"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {

            showAlertOperation(
                AlertType.INFORMATION,
                "Importando datos del fichero CSV",

            )
            // Cambiar el cursor a espera
            RoutesManager.activeStage.scene.cursor = WAIT
            viewModel.loadIntegrantes(it)
                .onSuccess {
                    showAlertOperation(
                        title = "Datos importados",
                        mensaje = "Se han importado los Integrantes."
                    )
                }.onFailure { error: GestionErrors->
                    showAlertOperation(alerta = AlertType.ERROR, title = "Error al importar", mensaje = error.message)
                }
            RoutesManager.activeStage.scene.cursor = DEFAULT
        }
    }
    private fun showAlertOperation(
        alerta: AlertType = AlertType.CONFIRMATION,
        title: String = "",
        mensaje: String = ""
    ) {
        Alert(alerta).apply {
            this.title = title
            this.contentText = mensaje
        }.showAndWait()
    }
}