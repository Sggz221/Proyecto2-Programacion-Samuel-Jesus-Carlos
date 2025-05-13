package org.example.newteam.gestion.service

import com.github.michaelbull.result.Result
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteam.gestion.models.Integrante

/**
 * Interfaz que crea el contrato para crear el servicio para gestionar un equipo de futbol en memoria y en el almacenamiento local
 */
interface EquipoService {
    fun importFromFile(filePath: String): Result<List<Integrante>, GestionErrors>
    fun exportToFile(filePath: String)

    fun getAll(): List<Integrante>
    fun getById(id: Long): Result<Integrante, GestionErrors>
    fun save(integrante: Integrante): Result<Integrante, GestionErrors>
    fun update(id: Long, integrante: Integrante): Result<Integrante, GestionErrors>
    fun delete(id: Long): Result<Integrante, GestionErrors>
}