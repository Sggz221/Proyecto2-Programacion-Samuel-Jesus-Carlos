package org.example.newteam.gestion.viewmodels

import com.github.michaelbull.result.Result
import javafx.beans.property.SimpleObjectProperty
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.models.Jugador
import org.example.newteam.gestion.service.EquipoServiceImpl
import org.lighthousegames.logging.logging
import java.io.File
import java.time.LocalDate
//
class EquipoViewModel (
    private val service: EquipoServiceImpl = Dependencies.getIntegrantesService()
) {
    private val logger = logging()

    val state: SimpleObjectProperty<GeneralState> = SimpleObjectProperty(GeneralState())

    data class GeneralState(
        var integrantes: List<Integrante> = emptyList(), //lista de todos los integrantes
        val integrante: IntegranteState = IntegranteState(), //el integrante seleccionado
        val goalAvg: String = "0.0", //goles promedio
        val minutesAvg: String = "0.0", //minutos jugados promedio
        val totalCost: String = "0.0" // Coste total de la plantilla
    )

    data class IntegranteState(
        val nombre: String = "",
        val apellidos: String = "",
        val fecha_nacimiento: LocalDate = LocalDate.now(),
        val fecha_incorporacion: LocalDate = LocalDate.now(),
        val salario: Double = 0.0,
        val pais: String = "",
        val imagen: String = "",
        val especialidad: String = "",
        val posicion: String = "",
        val dorsal: Int = 99,
        val altura: Double = 0.0,
        val peso: Double = 0.0,
        val goles: Int = 0,
        val partidos_jugados: Int = 0,
        val minutos_jugados: Int = 0
    )

    fun saveIntegrante(integrante: Integrante) {
        service.save(integrante)
        updateState()
    }

    private fun loadAllIntegrantes() {
        logger.debug { "Cargando los integrantes en el estado" }
        val newIntegrantes = service.getAll()
        state.value.integrantes = newIntegrantes
        updateState()
    }
    private fun updateState() {
        val goalAvg = state.value.integrantes.filterIsInstance<Jugador>().map { it.goles }.average().toString()
        val minutesAvg = state.value.integrantes.filterIsInstance<Jugador>().map { it.minutos_jugados }.average().toString()
        val totalCost = state.value.integrantes.sumOf { it.salario }.toString()

        state.value = state.value.copy(
            goalAvg = goalAvg,
            minutesAvg = minutesAvg,
            totalCost = totalCost,
            integrante = IntegranteState()
        )
    }

    fun exportIntegrantestoFile(file: File) : Result<Unit, GestionErrors> {
        logger.debug { "Exportando integrantes a fichero $file"}
        return service.exportToFile(file.path)
    }

    fun loadIntegrantesFromFile(file: File) : Result<List<Integrante>, GestionErrors> {
        logger.debug { "Cargando integrantes desde fichero $file"}
        return service.importFromFile(file.path).also { loadAllIntegrantes() }
    }
}