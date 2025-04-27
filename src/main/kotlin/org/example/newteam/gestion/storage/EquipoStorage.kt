package org.example.newteam.gestion.storage

import com.github.michaelbull.result.Result
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteam.gestion.models.Integrante
import java.io.File

/**
 * Interfaz que representa el contrato para crear un almacenamiento
 */
interface EquipoStorage {
    fun fileRead(file: File): Result<List<Integrante>, GestionErrors>
    fun fileWrite(equipo: List<Integrante>, file: File): Result<Unit, GestionErrors>
}