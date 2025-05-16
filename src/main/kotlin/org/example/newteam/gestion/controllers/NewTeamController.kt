package org.example.newteam.gestion.controllers

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import javafx.application.Platform
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.Cursor.DEFAULT
import javafx.scene.Cursor.WAIT
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteam.gestion.models.Especialidad
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.models.Jugador
import org.example.newteam.gestion.sesion.Session
import org.example.newteam.gestion.viewmodels.EquipoViewModel
import org.example.newteam.routes.RoutesManager
import org.lighthousegames.logging.logging

class NewTeamController () {
    private val logger = logging()
    private var viewModel: EquipoViewModel = Dependencies.provideViewModel()

    /* Menu */
    @FXML
    lateinit var exitButton: MenuItem
    @FXML
    lateinit var exportButton: MenuItem
    @FXML
    lateinit var importButton: MenuItem
    @FXML
    lateinit var aboutButton: MenuItem
    @FXML
    lateinit var logoutButton: MenuItem

    /* Master */
    @FXML
    lateinit var saveEntrenadorButton: Button
    @FXML
    lateinit var saveJugadorButton: Button
    @FXML
    lateinit var deleteAndCancelButton: Button
    @FXML
    lateinit var editAndSaveButton: Button

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
    lateinit var posicion: ToggleGroup
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
    lateinit var especialidad: ToggleGroup
    @FXML
    lateinit var radioPorteros: RadioButton
    @FXML
    lateinit var radioAsistente: RadioButton
    @FXML
    lateinit var radioPrincipal: RadioButton

    /* Main */
    @FXML
    lateinit var colSalario: TableColumn<Integrante, Double>
    @FXML
    lateinit var colEspecialidad: TableColumn<Integrante, Especialidad>
    @FXML
    lateinit var colRol: TableColumn<Integrante, String>
    @FXML
    lateinit var colNombre: TableColumn<Integrante, String>
    @FXML
    lateinit var listIntegrantes: TableView<Integrante>
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

    /* Lógica */
    fun initialize() {
        initEvents()
        initBindings()
        initDefaultValues()
    }

    private fun initDefaultValues() {
    }

    private fun initBindings(){
        //Comunes
        paisField.textProperty().bind(viewModel.state.map { it.integrante.pais })
        salarioField.textProperty().bind(viewModel.state.map { it.integrante.salario.toString() })
        incorporacionDP.valueProperty().bind(viewModel.state.map { it.integrante.fecha_incorporacion })
        nacimientoDP.valueProperty().bind(viewModel.state.map { it.integrante.fecha_nacimiento })
        apellidosField.textProperty().bind(viewModel.state.map { it.integrante.apellidos })
        nombreField.textProperty().bind(viewModel.state.map { it.integrante.nombre })

        //Jugador
        minutosField.textProperty().bind(viewModel.state.map { it.integrante.minutos_jugados.toString() })
        partidosField.textProperty().bind(viewModel.state.map { it.integrante.partidos_jugados.toString() })
        golesField.textProperty().bind(viewModel.state.map { it.integrante.goles.toString() })
        pesoField.textProperty().bind(viewModel.state.map { it.integrante.peso.toString() })
        alturaField.textProperty().bind(viewModel.state.map { it.integrante.altura.toString() })
        dorsalField.textProperty().bind(viewModel.state.map { it.integrante.dorsal.toString() })

        viewModel.state.addListener{_,_, newValue ->
            if (newValue.integrante.posicion == "CENTROCAMPISTA") {
                radioCentro.isSelected = true
            } else if (newValue.integrante.posicion == "DELANTERO") {
                radioDelantero.isSelected = true
            } else if (newValue.integrante.posicion == "DEFENSA") {
                radioDefensa.isSelected = true
            } else if (newValue.integrante.posicion == "PORTERO") {
                radioPortero.isSelected = true
            }
        }

        //Entrenador
        viewModel.state.addListener{_,_, newValue ->
            if (newValue.integrante.especialidad == "ENTRENADOR_ASISTENTE") {
                radioAsistente.isSelected = true
            } else if (newValue.integrante.especialidad == "ENTRENADOR_PORTEROS") {
                radioPorteros.isSelected = true
            } else if (newValue.integrante.especialidad == "ENTRENADOR_PRINCIPAL") {
                radioPrincipal.isSelected = true
            }
        }

        viewModel.state.addListener {_, _, newValue ->
            if (listIntegrantes.items != newValue.integrantes) listIntegrantes.items = FXCollections.observableArrayList(newValue.integrantes)

        }


    }

    private fun initEvents() {
        aboutButton.setOnAction {
            RoutesManager.initAboutStage()
        }
        exitButton.setOnAction {
            RoutesManager.onAppExit()
        }

        logoutButton.setOnAction {
            showLogoutAlert()
        }

        importButton.setOnAction { onImportarCSVAction() }
    }

    private fun onImportarCSVAction() {
        logger.debug{ "Iniciando FileChooser" }
        FileChooser().run {
            title = "Importar integrantes"
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
            extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
            extensionFilters.add(FileChooser.ExtensionFilter("XML", "*.xml"))
            extensionFilters.add(FileChooser.ExtensionFilter("BIN", "*.bin"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
            /*showAlertOperation(
                AlertType.INFORMATION,
                "Importando datos del fichero CSV",
                "Importando datos..."
            )*/
            // Cambiar el cursor a espera
            RoutesManager.activeStage.scene.cursor = WAIT
            viewModel.loadIntegrantesFromFile(it)
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

    private fun showLogoutAlert(){
        val alert = Alert(AlertType.CONFIRMATION).apply {
            this.title = "Cerrar sesión"
            this.contentText = "Si cierras la sesión perderás todos los datos no guardados. ¿Estás seguro de querer continuar?"
        }.showAndWait().ifPresent { opcion ->
            if (opcion == ButtonType.OK) Session.toLogin(paisField.scene.window as Stage)
        }
    }
}