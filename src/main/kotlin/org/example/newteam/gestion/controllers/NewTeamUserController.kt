package org.example.newteam.gestion.controllers

import com.github.michaelbull.result.*
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.Cursor.DEFAULT
import javafx.scene.Cursor.WAIT
import javafx.scene.control.*
import javafx.scene.control.Alert.AlertType
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.FileChooser
import javafx.stage.Stage
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteam.gestion.mapper.toEntrenadorModel
import org.example.newteam.gestion.mapper.toJugadorModel
import org.example.newteam.gestion.models.Entrenador
import org.example.newteam.gestion.models.Especialidad
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.models.Jugador
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
    lateinit var aboutButton: MenuItem
    @FXML
    lateinit var logoutButton: MenuItem

    /* Detalle */

    // Comunes
    @FXML
    lateinit var profilePicture: ImageView
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

    private var nombreAscending: Boolean = true

    private var salarioAscending: Boolean = true

    private var alreadyFiltered: Boolean = false


    fun initialize() {
        viewModel.loadAllIntegrantes()
        initEvents()
        initBindings()
        initDefaultValues()
    }

    private fun initDefaultValues() {
        disableAll()

        //Tabla
        listIntegrantes.items = viewModel.state.value.integrantes
        //Columnas, ya se bindean solas en base al contenido de la tabla
        colNombre.cellValueFactory =PropertyValueFactory("nombreCompleto")
        colSalario.cellValueFactory =PropertyValueFactory("salario")
        colRol.cellValueFactory = PropertyValueFactory("rol")
        colEspecialidad.cellValueFactory = PropertyValueFactory("miEspecialidad")
    }

    private fun initBindings(){
        //Barra de búqueda
        searchBar.textProperty().addListener { _, _, newValue ->
            filterByName(newValue)
        }

        //Reflejar cambios del estado en el detalle
        viewModel.state.addListener { _, _, newValue ->

            //Comunes
            if(newValue.integrante.imagen != profilePicture.image.url) profilePicture.image =
                if(isExternalImage(newValue.integrante.imagen)){
                    Image(newValue.integrante.imagen)
                } else {
                    Image(RoutesManager.getResourceAsStream(newValue.integrante.imagen))
                }
            if (newValue.integrante.nombre != nombreField.text) nombreField.text = newValue.integrante.nombre
            if (newValue.integrante.apellidos != apellidosField.text) apellidosField.text = newValue.integrante.apellidos
            if (newValue.integrante.pais != paisField.text) paisField.text = newValue.integrante.pais
            if (newValue.integrante.salario.toString() != salarioField.text) salarioField.text = newValue.integrante.salario.toString()
            if (newValue.integrante.fecha_incorporacion != incorporacionDP.value) incorporacionDP.value = newValue.integrante.fecha_incorporacion
            if (newValue.integrante.fecha_nacimiento != nacimientoDP.value) nacimientoDP.value = newValue.integrante.fecha_nacimiento

            //Jugador
            if (newValue.integrante.minutos_jugados.toString() != minutosField.text) minutosField.text = newValue.integrante.minutos_jugados.toString()
            if (newValue.integrante.partidos_jugados.toString() != partidosField.text) partidosField.text = newValue.integrante.partidos_jugados.toString()
            if (newValue.integrante.goles.toString() != golesField.text) golesField.text = newValue.integrante.goles.toString()
            if (newValue.integrante.peso.toString() != pesoField.text) pesoField.text = newValue.integrante.peso.toString()
            if (newValue.integrante.altura.toString() != alturaField.text) alturaField.text = newValue.integrante.altura.toString()
            if (newValue.integrante.dorsal.toString() != dorsalField.text) dorsalField.text = newValue.integrante.dorsal.toString()
            if (newValue.integrante.posicion == "CENTROCAMPISTA") {
                radioCentro.isSelected = true
                desmarcarEspecialidadesEntrenador()
            } else if (newValue.integrante.posicion == "DELANTERO") {
                radioDelantero.isSelected = true
                desmarcarEspecialidadesEntrenador()
            } else if (newValue.integrante.posicion == "DEFENSA") {
                radioDefensa.isSelected = true
                desmarcarEspecialidadesEntrenador()
            } else if (newValue.integrante.posicion == "PORTERO") {
                radioPortero.isSelected = true
                desmarcarEspecialidadesEntrenador()
            }

            //Entrenador
            if (newValue.integrante.especialidad == "ENTRENADOR_ASISTENTE") {
                radioAsistente.isSelected = true
                desmarcarPosicionesJugador()
            } else if (newValue.integrante.especialidad == "ENTRENADOR_PORTEROS") {
                radioPorteros.isSelected = true
                desmarcarPosicionesJugador()
            } else if (newValue.integrante.especialidad == "ENTRENADOR_PRINCIPAL") {
                radioPrincipal.isSelected = true
                desmarcarPosicionesJugador()
            }
        }


        listIntegrantes.selectionModel.selectedItemProperty().addListener { _, _, newValue ->
            newValue?.let { onTablaSelected(newValue) }
        }

        //Footer
        golesAvg.textProperty().bind(viewModel.state.map { it.goalAvg })
        minutosAvg.textProperty().bind(viewModel.state.map { it.minutesAvg })
        totalPlantilla.textProperty().bind(viewModel.state.map { it.totalCost })
    }

    private fun isExternalImage(path: String):Boolean {
        if (path.startsWith("file:/"))
            return true
        else
            return false
    }

    private fun disableAll() {
        disableComunes()
        disableJugador()
        disableEntrenador()
    }

    private fun disableComunes(){
        paisField.isDisable = true
        salarioField.isDisable = true
        incorporacionDP.isDisable = true
        nacimientoDP.isDisable = true
        apellidosField.isDisable = true
        nombreField.isDisable = true
    }

    private fun disableJugador(){
        minutosField.isDisable = true
        partidosField.isDisable = true
        golesField.isDisable = true
        pesoField.isDisable = true
        alturaField.isDisable = true
        dorsalField.isDisable = true
        posicion.toggles.forEach { (it as RadioButton).isDisable = true }
    }

    private fun disableEntrenador(){
        especialidad.toggles.forEach { (it as RadioButton).isDisable = true }
    }

    private fun desmarcarEspecialidadesEntrenador() {
        radioAsistente.isSelected = false
        radioPorteros.isSelected = false
        radioPrincipal.isSelected = false
    }

    private fun desmarcarPosicionesJugador() {
        radioCentro.isSelected = false
        radioDelantero.isSelected = false
        radioDefensa.isSelected = false
        radioPortero.isSelected = false
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

        sortByNombre.setOnAction { onSortByNombreAction() }

        sortBySalario.setOnAction { onSortBySalarioAction() }

        sortByNothing.setOnAction { onSortByNothingAction() }

        filterByJugadores.setOnAction { onFilterByJugadoresAction() }

        filterByEntrenadores.setOnAction { onFilterByEntrenadoresAction() }

        filterByNothing.setOnAction { onFilterByNothingAction() }

    }

    private fun filterByName (cadena: String){
        logger.debug { "Filtrando integrantes por nombre" }

        viewModel.quitarFiltros()

        val integrantesFiltradosPorNombre = viewModel.state.value.integrantes.filter { it.nombreCompleto.lowercase().contains(cadena.lowercase()) }
        viewModel.filterIntegrantes(integrantesFiltradosPorNombre)
    }

    private fun onFilterByNothingAction() {
        logger.debug { "Quitando filtros de jugador y entrenador" }

        viewModel.quitarFiltros()
        alreadyFiltered = false

    }

    private fun onFilterByEntrenadoresAction() {
        logger.debug { "Filtrando los jugadores" }

        if (alreadyFiltered) viewModel.loadAllIntegrantes()

        val entrenadoresFiltrados: List<Integrante> = viewModel.state.value.integrantes.filterIsInstance<Entrenador>()
        viewModel.filterIntegrantes(entrenadoresFiltrados)
        alreadyFiltered = true
    }

    private fun onFilterByJugadoresAction() {
        logger.debug { "Filtrando los jugadores" }

        if (alreadyFiltered) viewModel.loadAllIntegrantes()

        val jugadoresFiltrados: List<Integrante> = viewModel.state.value.integrantes.filterIsInstance<Jugador>()
        viewModel.filterIntegrantes(jugadoresFiltrados)
        alreadyFiltered = true
    }

    private fun onSortByNothingAction() {
        logger.debug { "Quitando filtros de ordenación" }

        val integrantesSinOrden: List<Integrante> = viewModel.state.value.integrantes.shuffled()

        viewModel.sortIntegrantes(integrantesSinOrden)

    }

    private fun onSortBySalarioAction(){
        logger.debug { "Ordenando integrantes por salario" }

        val integrantesOrdenados: List<Integrante>

        if (salarioAscending) {
            integrantesOrdenados = viewModel.state.value.integrantes.sortedBy { it.salario }
            salarioAscending = false
        } else {
            integrantesOrdenados = viewModel.state.value.integrantes.sortedByDescending { it.salario }
            salarioAscending = true
        }

        viewModel.sortIntegrantes(integrantesOrdenados)
    }

    private fun onSortByNombreAction() {
        logger.debug { "Ordenando integrantes por nombre" }

        val integrantesOrdenados: List<Integrante>

        if (nombreAscending) {
            integrantesOrdenados = viewModel.state.value.integrantes.sortedBy { it.apellidos }
            nombreAscending = false
        } else {
            integrantesOrdenados = viewModel.state.value.integrantes.sortedByDescending { it.apellidos }
            nombreAscending = true
        }

        viewModel.sortIntegrantes(integrantesOrdenados)
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