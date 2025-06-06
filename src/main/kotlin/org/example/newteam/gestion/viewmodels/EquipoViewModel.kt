package org.example.newteam.gestion.viewmodels

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onSuccess
import javafx.beans.property.SimpleObjectProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.scene.image.Image
import org.example.newteam.gestion.configuration.Configuration
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteam.gestion.extensions.redondearA2Decimales
import org.example.newteam.gestion.mapper.toEntrenadorModel
import org.example.newteam.gestion.mapper.toJugadorModel
import org.example.newteam.gestion.models.Entrenador
import org.example.newteam.gestion.models.Especialidad
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.models.Jugador
import org.example.newteam.gestion.service.EquipoServiceImpl
import org.lighthousegames.logging.logging
import java.io.File
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import java.time.Instant
import java.time.LocalDate
import kotlin.concurrent.thread

/**
 * Clase que representa el ViewModel de la aplicación
 * @property service servicio inyectado
 * @see [EquipoServiceImpl]
 */
class EquipoViewModel (
    private val service: EquipoServiceImpl = Dependencies.getIntegrantesService()
) {
    private val logger = logging()

    private var allIntegrantes: List<Integrante> = emptyList()

    val state: SimpleObjectProperty<GeneralState> = SimpleObjectProperty(GeneralState())

    data class GeneralState(
        var integrantes: ObservableList<Integrante> = FXCollections.observableArrayList(), //lista de todos los integrantes
        val integrante: IntegranteState = IntegranteState(), //el integrante seleccionado
        val goalAvg: String = "0.0", //goles promedio
        val minutesAvg: String = "0.0", //minutos jugados promedio
        val totalCost: String = "0.0" // Coste total de la plantilla
    )

    data class IntegranteState(
        val id: Long = 0L,
        val nombre: String = "",
        val apellidos: String = "",
        val fecha_nacimiento: LocalDate = LocalDate.now(),
        val fecha_incorporacion: LocalDate = LocalDate.now(),
        val salario: Double = 0.0,
        val pais: String = "",
        val imagen: String = "media/profile_picture.png",
        val especialidad: String = "",
        val posicion: String = "",
        val dorsal: Int = 0,
        val altura: Double = 0.0,
        val peso: Double = 0.0,
        val goles: Int = 0,
        val partidos_jugados: Int = 0,
        val minutos_jugados: Int = 0
    )

    /**
     * Guarda un integrante
     * @param integrante el integrante a guardar
     * @see updateState
     */
    fun saveIntegrante(integrante: Integrante) {
        service.save(integrante).onSuccess {
            state.value.integrantes.addAll(it)
        }
        updateState()
    }

    /**
     * Elimina un integrante
     * @param id el id del integrante a eliminar
     * @see updateState
     */
    fun deleteIntegrante(id: Long) {
        service.delete(id).onSuccess {
            state.value.integrantes.removeIf { it.id == id }
        }
        updateState()
    }

    /**
     * Carga todos los integrantes en el estado
     * @see [EquipoServiceImpl.getAll]
     * @see updateState
     */
    fun loadAllIntegrantes() {
        logger.debug { "Cargando los integrantes en el estado" }
        val newIntegrantes = service.getAll()
        state.value.integrantes.setAll(newIntegrantes)
        updateState()
    }

    /**
     * Actualiza el estado para que la media de goles, de minutos jugados y el coste total de salarios de la plantilla estén actualizados.
     * @see redondearA2Decimales
     */
    private fun updateState() {
        val goalAvg = state.value.integrantes.filterIsInstance<Jugador>().map { it.goles }.average().redondearA2Decimales().toString()
        val minutesAvg = state.value.integrantes.filterIsInstance<Jugador>().map { it.minutos_jugados }.average().redondearA2Decimales().toString()
        val totalCost = state.value.integrantes.sumOf { it.salario }.redondearA2Decimales().toString()

        state.value = state.value.copy(
            goalAvg = goalAvg,
            minutesAvg = minutesAvg,
            totalCost = totalCost
        )
    }

    /**
     * Guarda en el estado los integrantes ordenados
     * @see updateState
     */
    fun sortIntegrantes(integrantesOrdenados: List<Integrante>) {
        logger.debug { "Ordenando la lista de integrantes" }

        state.value.integrantes.setAll(integrantesOrdenados)
    }

    /**
     * Guarda en el estado los integrantes filtrados
     * @see updateState
     */
    fun filterIntegrantes(integrantesFiltrados: List<Integrante>) {
        logger.debug { "Filtrando la lista de integrantes" }

        state.value.integrantes.setAll(integrantesFiltrados)
        updateState()

    }

    /**
     * Elimina todos los filtros
     * @see filterIntegrantes
     * @see updateState
     */
    fun quitarFiltros() {
        logger.debug { "Quitando filtros" }

        allIntegrantes = service.getAll()
        filterIntegrantes(allIntegrantes)
        updateState()
    }

    /**
     * Realiza una copia de seguridad de los integrantes en un fichero
     * @param file el fichero
     * @return un [Result] de [Unit] en caso de exportar correctamente o de [GestionErrors.StorageError] en caso contrario
     */
    fun exportIntegrantestoFile(file: File) : Result<Unit, GestionErrors> {
        logger.debug { "Exportando integrantes a fichero $file"}
        return service.exportToFile(file.path)
    }

    /**
     * Importa los integrantes de un fichero
     * @param file el fichero
     * @return un [Result] de una lista de [Integrante] en caso de importar correctamente o de [GestionErrors.StorageError] en caso contrario
     */
    fun loadIntegrantesFromFile(file: File) : Result<List<Integrante>, GestionErrors> {
        logger.debug { "Cargando integrantes desde fichero $file"}
        return service.importFromFile(file.path).also { loadAllIntegrantes() }
    }

    /**
     * Crea un integrante vacío
     */
    fun createEmptyIntegrante(emptyIntegrante: IntegranteState) {
        state.value = state.value.copy(integrante = emptyIntegrante)
    }

    /**
     * Actualiza el integrante seleccionado con los datos del integrante que le entra por parámetro
     * @param integrante integrante cuyos datos queremos guardar en el integrante seleccionado
     */
    fun updateIntegranteSelected(integrante: Integrante) {
        if (integrante is Jugador){
            state.value = state.value.copy(
                integrante = IntegranteState(
                    id = integrante.id,
                    nombre = integrante.nombre,
                    apellidos = integrante.apellidos,
                    fecha_nacimiento = integrante.fecha_nacimiento,
                    fecha_incorporacion = integrante.fecha_incorporacion,
                    salario = integrante.salario,
                    pais = integrante.pais,
                    imagen = integrante.imagen,
                    posicion = integrante.posicion.toString(),
                    dorsal = integrante.dorsal,
                    altura = integrante.altura,
                    peso = integrante.peso,
                    goles = integrante.goles,
                    partidos_jugados = integrante.partidos_jugados,
                    minutos_jugados = integrante.minutos_jugados
                )
            )
        }
        else if (integrante is Entrenador){
            state.value = state.value.copy(
                integrante = IntegranteState(
                    id = integrante.id,
                    nombre = integrante.nombre,
                    apellidos = integrante.apellidos,
                    fecha_nacimiento = integrante.fecha_nacimiento,
                    fecha_incorporacion = integrante.fecha_incorporacion,
                    salario = integrante.salario,
                    pais = integrante.pais,
                    imagen = integrante.imagen,
                    especialidad = integrante.especialidad.toString()
                )
            )
        }
    }

    /**
     * Actualiza la imagen de un integrante
     * @param fileName ruta del archivo de imagen
     * @see getImagenName
     */
    fun updateImageIntegrante(fileName: File) {
        logger.debug { "Guardando imagen $fileName" }

        val newName = getImagenName(fileName)
        val newFileImage = File(Configuration.configurationProperties.imagesDirectory, newName)

        logger.debug { "Copiando a: ${newFileImage.absolutePath}" }

        Files.copy(fileName.toPath(), newFileImage.toPath(), StandardCopyOption.REPLACE_EXISTING)

        state.value = state.value.copy(
            integrante = state.value.integrante.copy(
                imagen = newFileImage.toURI().toString()
            )
        )
        val id = state.value.integrante.id
        if (state.value.integrante.especialidad == "") {
            service.update(id, state.value.integrante.toJugadorModel()).onSuccess {
                state.value.integrantes.find { it.id == id }?.let {it.imagen = newFileImage.toURI().toString()}
            }
        }
        else service.update(id, state.value.integrante.toEntrenadorModel()).onSuccess {
            state.value.integrantes.find { it.id == id }?.let {it.imagen = newFileImage.toURI().toString()}
        }
    }

    /**
     * Obtiene el nombre de la imagen a partir de la ruta donde se encuentra
     * @param newFileImage la ruta de la imagen
     * @return el nombre de la imagen
     */
    private fun getImagenName(newFileImage: File): String {
        val name = newFileImage.name
        val extension = name.substring(name.lastIndexOf(".") + 1)
        return "${Instant.now().toEpochMilli()}.$extension"
    }

}