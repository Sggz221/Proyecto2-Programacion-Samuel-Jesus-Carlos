package org.example.newteam.gestion.viewmodels

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.service.EquipoService
import org.example.newteam.gestion.service.EquipoServiceImpl
import java.io.File
import java.time.LocalDate
//
class EquipoViewModel (
    private val service: EquipoServiceImpl = Dependencies.getIntegrantesService()
) {

    data class EntrenadorState(
        val id: Long = 0L,
        val nombre: String = "",
        val apellidos: String = "",
        val fecha_nacimiento: LocalDate = LocalDate.now(),
        val fecha_incorporacion: LocalDate = LocalDate.now(),
        val salario: Double = 0.0,
        val pais: String = "",
        val especialidad: String = "",
        val imagen: String = ""
    )

    data class JugadorState(
        val id: Long = 0L,
        val nombre: String = "",
        val apellidos: String = "",
        val fecha_nacimiento: LocalDate = LocalDate.now(),
        val fecha_incorporacion: LocalDate = LocalDate.now(),
        val salario: Double = 0.0,
        val pais: String = "",
        val imagen: String = "",
        val posicion: String = "",
        val dorsal: Int = 99,
        val altura: Double = 0.0,
        val peso: Double = 0.0,
        val goles: Int = 0,
        val partidos_jugados: Int = 0,
        val minutos_jugados: Int = 0

    )

    fun loadIntegrantes(file: File) : Result<List<Integrante>, GestionErrors> {
        return service.importFromFile(file.path)
    }
}