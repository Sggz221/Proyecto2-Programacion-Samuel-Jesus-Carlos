package org.example.newteam.gestion.controllers

import com.github.michaelbull.result.*
import javafx.application.Platform
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
import kotlin.concurrent.thread

class NewTeamAdminController () {

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
    private var isEditButton: Boolean = true

    private var nombreAscending: Boolean = true

    private var salarioAscending: Boolean = true

    private var alreadyFiltered: Boolean = false

    fun initialize() {
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
        //Comunes
        // De interfaz a ViewModel

        /*paisField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.pais) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(pais = newvalue))
        }
        salarioField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.salario.toString()) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(salario = newvalue.toDoubleOrNull() ?: 0.0))
        }
        incorporacionDP.valueProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.fecha_incorporacion) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(fecha_incorporacion = newvalue))
        }
        nacimientoDP.valueProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.fecha_nacimiento) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(fecha_nacimiento = newvalue))
        }
        apellidosField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.apellidos) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(apellidos = newvalue))
        }
        nombreField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.nombre) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(nombre = newvalue))
        }

        //Jugador
        minutosField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.minutos_jugados.toString()) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(minutos_jugados = newvalue.toIntOrNull() ?: 0))
        }
        partidosField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.partidos_jugados.toString()) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(partidos_jugados = newvalue.toIntOrNull() ?: 0))
        }
        golesField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.goles.toString()) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(goles = newvalue.toIntOrNull() ?: 0))
        }
        minutosField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.minutos_jugados.toString()) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(minutos_jugados = newvalue.toIntOrNull() ?: 0))
        }
        alturaField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.altura.toString()) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(altura = newvalue.toDoubleOrNull() ?: 0.0))
        }
        dorsalField.textProperty().addListener{_,_,newvalue ->
            if(newvalue != viewModel.state.value.integrante.dorsal.toString()) viewModel.state.value = viewModel.state.value.copy(integrante = EquipoViewModel.IntegranteState(dorsal = newvalue.toIntOrNull() ?: 0))
        }*/

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

    private fun onTablaSelected(newValue: Integrante) {
        logger.debug { " Integrante seleccionado en la tabla: $newValue " }
        disableAll()
        viewModel.updateIntegranteSelected(newValue)
        logger.debug { "IntegranteState selected: ${viewModel.state.value.integrante}" }
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

        saveJugadorButton.setOnAction {
            especialidad.toggles.forEach{ it.isSelected = false }
            onCheckEditState()
            onCreateJugadorAction()
        }

        saveEntrenadorButton.setOnAction {
            posicion.toggles.forEach{ it.isSelected = false }
            onCheckEditState()
            onCreateEntrenadorAction()
        }

        editAndSaveButton.setOnAction {
            onCheckEditState()
        }
        deleteAndCancelButton.setOnAction {
            onCheckDeleteState()
        }

        profilePicture.setOnMouseClicked {
            onImageSelectAction()
        }

        sortByNombre.setOnAction { onSortByNombreAction() }

        sortBySalario.setOnAction { onSortBySalarioAction() }

        sortByNothing.setOnAction { onSortByNothingAction() }

        filterByJugadores.setOnAction { onFilterByJugadoresAction() }

        filterByEntrenadores.setOnAction { onFilterByEntrenadoresAction() }

        filterByNothing.setOnAction { onFilterByNothingAction() }

    }

    private fun onImageSelectAction() {
        FileChooser().run {
            title = "Selecciona una imagen para el integrante"
            extensionFilters.addAll(FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"))
            showOpenDialog(RoutesManager.activeStage)
        }?.let {
            viewModel.updateImageIntegrante(it)
        }
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

    private fun onCheckDeleteState() {
        if (isEditButton) deleteFunction()
        else cancelFunction()
    }

    private fun deleteFunction() {
        onDeleteIntegranteAction()
    }

    private fun onDeleteIntegranteAction() {
        val id = viewModel.state.value.integrante.id
        viewModel.deleteIntegrante(id)
    }

    private fun onCheckEditState() {
        if (isEditButton) editFunction()
        else saveFunction(viewModel.state.value.integrante.especialidad == "") // Si no tiene especialidad, es Jugador
    }

    private fun cancelFunction() {
        styleToEditButton()
        styleToDeleteButton()
        isEditButton = true
    }

    private fun saveFunction(esJugador: Boolean) {
        styleToEditButton()
        styleToDeleteButton()
        onSaveIntegranteAction(esJugador)
        isEditButton = true
    }

    private fun editFunction(){
        styleToSaveButton()
        styleToCancelButton()
        if(viewModel.state.value.integrante.especialidad == "") enableJugador()
        else enableEntrenador()
        isEditButton = false
    }

    /**
     * Cambia el estilo del boton a el de un boton de editar
     */
    private fun styleToEditButton() {
        editAndSaveButton.style = "-fx-background-color: #be9407"
        editAndSaveButton.text = "Editar"
        // CAMBIAR FOTO
    }
    private fun styleToSaveButton() {
        editAndSaveButton.style = "-fx-background-color: #33b3ff"
        editAndSaveButton.text = "Guardar"
        // CAMBIAR FOTO
    }
    private fun styleToDeleteButton() {
        deleteAndCancelButton.style = "-fx-background-color: #FF2C2C"
        deleteAndCancelButton.text = "Eliminar"
        // CAMBIAR FOTO
    }
    private fun styleToCancelButton() {
        deleteAndCancelButton.style = "-fx-background-color: #e59111"
        deleteAndCancelButton.text = "Cancelar"
        // CAMBIAR FOTO
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

    private fun enableComunes(){
        paisField.isDisable = false
        salarioField.isDisable = false
        incorporacionDP.isDisable = false
        nacimientoDP.isDisable = false
        apellidosField.isDisable = false
        nombreField.isDisable = false
    }

    private fun enableJugador(){
        enableComunes()
        minutosField.isDisable = false
        partidosField.isDisable = false
        golesField.isDisable = false // P@ssw0rd
        pesoField.isDisable = false
        alturaField.isDisable = false
        dorsalField.isDisable = false
        posicion.toggles.forEach { (it as RadioButton).isDisable = false }
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

    private fun enableEntrenador(){
        enableComunes()
        especialidad.toggles.forEach { (it as RadioButton).isDisable = false }
    }
    private fun onCreateEntrenadorAction(){
        logger.debug { "Creando Entrenador" }
        enableEntrenador()
        disableJugador()
        val emptyEntrenador = EquipoViewModel.IntegranteState(especialidad = "ENTRENADOR_PORTEROS")
        viewModel.createEmptyIntegrante(emptyEntrenador)
    }
    private fun onCreateJugadorAction() {
        logger.debug { "Creando Jugador" }
        enableJugador()
        disableEntrenador()
        val emptyJugador = EquipoViewModel.IntegranteState()
        viewModel.createEmptyIntegrante(emptyJugador)
    }

    private fun onSaveIntegranteAction(esJugador: Boolean) {
        logger.debug { "Guardando nuevo jugador" }
        validarJugador()
        parseViewToIntegrante(esJugador) // Actualizamos el integrante seleccionado con los datos de la vista
        val newIntegrante: Integrante
        logger.debug { "Mapeando integrante" }
        if (esJugador) newIntegrante = viewModel.state.value.integrante.toJugadorModel()
        else newIntegrante = viewModel.state.value.integrante.toEntrenadorModel()

        viewModel.saveIntegrante(newIntegrante)

    }

    private fun parseViewToIntegrante(esJugador: Boolean) {
        if (esJugador) {
            viewModel.state.value = viewModel.state.value.copy(
                integrante = EquipoViewModel.IntegranteState(
                    nombre = nombreField.text,
                    apellidos = apellidosField.text,
                    fecha_nacimiento = nacimientoDP.value,
                    fecha_incorporacion = incorporacionDP.value,
                    salario = salarioField.text.toDoubleOrNull() ?: 0.0,
                    pais = paisField.text,
                    //imagen =
                    posicion = getPosicionFromView(),
                    dorsal = dorsalField.text.toIntOrNull() ?: 0,
                    altura = alturaField.text.toDoubleOrNull() ?: 0.0,
                    peso = pesoField.text.toDoubleOrNull() ?: 0.0,
                    goles = golesField.text.toIntOrNull() ?: 0,
                    partidos_jugados = partidosField.text.toIntOrNull() ?: 0,
                    minutos_jugados = minutosField.text.toIntOrNull() ?: 0,
                )
            )
        }
        else {
            viewModel.state.value = viewModel.state.value.copy(
                integrante = EquipoViewModel.IntegranteState(
                    nombre = nombreField.text,
                    apellidos = apellidosField.text,
                    fecha_nacimiento = nacimientoDP.value,
                    fecha_incorporacion = incorporacionDP.value,
                    salario = salarioField.text.toDoubleOrNull() ?: 0.0,
                    pais = paisField.text,
                    //imagen =
                    especialidad = getEspecialidadFromView()
                )
            )
        }
        logger.debug { "Integrante parseado al estado: ${viewModel.state.value.integrante}" }
    }

    private fun getPosicionFromView(): String {
        if(radioDelantero.isSelected) return "DELANTERO"
        else if (radioCentro.isSelected) return "CENTROCAMPISTA"
        else if (radioDefensa.isSelected) return "DEFENSA"
        else if (radioPortero.isSelected) return "PORTERO"
        else return "FALLO"
    }
    private fun getEspecialidadFromView(): String {
        if(radioPrincipal.isSelected) return "ENTRENADOR_PRINCIPAL"
        else if (radioAsistente.isSelected) return "ENTRENADOR_ASISTENTE"
        else if (radioPorteros.isSelected) return "ENTRENADOR_PORTEROS"
        else return "FALLO"
    }

    private fun validarJugador (): Result<Unit, GestionErrors.InvalidoError> {
        logger.debug { "Validando jugador" }

        if (nombreField.text.isBlank()){
            return Err(GestionErrors.InvalidoError("El nombre no puede estar vacío"))
        }

        if (apellidosField.text.isBlank()){
            return Err(GestionErrors.InvalidoError("Los apellidos no pueden estar vacíos"))
        }

        if (nacimientoDP.value > LocalDate.now()){
            return Err(GestionErrors.InvalidoError("La fecha de nacimiento no puede ser posterior a la fecha actual"))
        }

        if (incorporacionDP.value > LocalDate.now()){
            return Err(GestionErrors.InvalidoError("La fecha de incorporación no puede ser posterior a la fecha actual"))
        }

        if (incorporacionDP.value < nacimientoDP.value) {
            return Err(GestionErrors.InvalidoError("La fecha de incorporación no puede ser anterior a la fecha de nacimiento"))
        }

        if (salarioField.text.toDouble() < 0.0){
            return Err(GestionErrors.InvalidoError("El salario no puede ser negativo"))
        }

        if (paisField.text.isBlank()){
            return Err(GestionErrors.InvalidoError("El país de origen no puede estar en blanco"))
        }

        if (dorsalField.text.toInt() !in 1..99) {
            return Err(GestionErrors.InvalidoError("El dorsal no puede ser menor a 1 ni mayor a 99"))
        }

        if (alturaField.text.toDouble() !in 0.0..3.0){
            return Err(GestionErrors.InvalidoError("La altura no puede ser negativa ni superar los 3 metros"))
        }

        if (pesoField.text.toDouble() < 0.0) {
            return Err(GestionErrors.InvalidoError("El peso no puede ser negativo"))
        }

        if (golesField.text.toInt() < 0) {
            return Err(GestionErrors.InvalidoError("El número de goles no puede ser negativo"))
        }

        if (partidosField.text.toInt() < 0){
            return Err(GestionErrors.InvalidoError("El número de partidos jugados no puede ser negativo"))
        }

        return Ok(Unit)
    }

    private fun validarEntrenador (): Result<Unit, GestionErrors.InvalidoError> {
        logger.debug { "Validando entrenador" }
        if (nombreField.text.isBlank()){
            return Err(GestionErrors.InvalidoError("El nombre no puede estar vacío"))
        }

        if (apellidosField.text.isBlank()){
            return Err(GestionErrors.InvalidoError("Los apellidos no pueden estar vacíos"))
        }

        if (nacimientoDP.value > LocalDate.now()){
            return Err(GestionErrors.InvalidoError("La fecha de nacimiento no puede ser posterior a la fecha actual"))
        }

        if (incorporacionDP.value > LocalDate.now()){
            return Err(GestionErrors.InvalidoError("La fecha de incorporación no puede ser posterior a la fecha actual"))
        }

        if (incorporacionDP.value < nacimientoDP.value) {
            return Err(GestionErrors.InvalidoError("La fecha de incorporación no puede ser anterior a la fecha de nacimiento"))
        }

        if (salarioField.text.toDouble() < 0.0){
            return Err(GestionErrors.InvalidoError("El salario no puede ser negativo"))
        }

        if (paisField.text.isBlank()){
            return Err(GestionErrors.InvalidoError("El país de origen no puede estar en blanco"))
        }

        return Ok(Unit)

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
            Platform.runLater {
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
            Platform.runLater {
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