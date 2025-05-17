package org.example.newteam.gestion.controllers

import com.github.michaelbull.result.*
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.Cursor.DEFAULT
import javafx.scene.Cursor.WAIT
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.cell.PropertyValueFactory
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteam.gestion.mapper.toEntrenadorModel
import org.example.newteam.gestion.mapper.toJugadorModel
import org.example.newteam.gestion.models.Especialidad
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.sesion.Session
import org.example.newteam.gestion.viewmodels.EquipoViewModel
import org.example.newteam.routes.RoutesManager
import org.lighthousegames.logging.logging
import java.time.LocalDate

class NewTeamUserController () {
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

    /* Footer */
    @FXML
    lateinit var totalPlantilla: Label
    @FXML
    lateinit var minutosAvg: Label
    @FXML
    lateinit var golesAvg: Label

    /* Lógica */
    fun initialize() {
        initEvents()
        initBindings()
        initDefaultValues()
    }

    private fun initDefaultValues() {
        //Tabla
        listIntegrantes.items = FXCollections.observableArrayList(viewModel.state.value.integrantes)
        //Columnas, ya se bindean solas en base al contenido de la tabla
        colNombre.cellValueFactory =PropertyValueFactory("nombreCompleto")
        colSalario.cellValueFactory =PropertyValueFactory("salario")
        colRol.cellValueFactory = PropertyValueFactory("rol")
        colEspecialidad.cellValueFactory = PropertyValueFactory("miEspecialidad")
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
        //Tabla
        viewModel.state.addListener {_, _, newValue ->
            if (listIntegrantes.items != newValue.integrantes) listIntegrantes.items = FXCollections.observableArrayList(newValue.integrantes)

        }

        listIntegrantes.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaSelected(newValue) }
        }

        //Footer
        golesAvg.textProperty().bind(viewModel.state.map { it.goalAvg })
        minutosAvg.textProperty().bind(viewModel.state.map { it.minutesAvg })
        totalPlantilla.textProperty().bind(viewModel.state.map { it.totalCost })
    }

    private fun onTablaSelected(newValue: Integrante) {
        logger.debug { " Integrante seleccionado en la tabla: $newValue " }
        viewModel.updateIntegranteSelected(newValue)
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

        importButton.setOnAction { onImportarAction() }

        exportButton.setOnAction { onExportarAction() }
    }

    private fun onExportarAction(){
        logger.debug{ "Iniciando FileChooser" }

        FileChooser().run {
            title = "Exportar integrantes"
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
            extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
            extensionFilters.add(FileChooser.ExtensionFilter("XML", "*.xml"))
            extensionFilters.add(FileChooser.ExtensionFilter("BIN", "*.bin"))
            showSaveDialog(RoutesManager.activeStage)
        }?.let {
            // Cambiar el cursor a espera
            RoutesManager.activeStage.scene.cursor = WAIT
            viewModel.exportIntegrantestoFile(it)
                .onSuccess {
                    showAlertOperation(
                        title = "Datos exportados",
                        mensaje = "Se han exportado los Integrantes."
                    )
                }.onFailure { error: GestionErrors->
                    showAlertOperation(alerta = AlertType.ERROR, title = "Error al exportar", mensaje = error.message)
                }
            RoutesManager.activeStage.scene.cursor = DEFAULT
        }
    }

    private fun onImportarAction() {
        logger.debug{ "Iniciando FileChooser" }
        FileChooser().run {
            title = "Importar integrantes"
            extensionFilters.add(FileChooser.ExtensionFilter("CSV", "*.csv"))
            extensionFilters.add(FileChooser.ExtensionFilter("JSON", "*.json"))
            extensionFilters.add(FileChooser.ExtensionFilter("XML", "*.xml"))
            extensionFilters.add(FileChooser.ExtensionFilter("BIN", "*.bin"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
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