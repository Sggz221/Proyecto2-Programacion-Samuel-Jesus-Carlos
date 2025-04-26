package org.example.newteam.gestion.storage

import org.example.newteam.gestion.models.Integrante
import java.io.File

/**
 * Interfaz que representa el contrato para crear un almacenamiento
 */
interface EquipoStorage {
    fun fileRead(file: File): List<Integrante>
    fun fileWrite(equipo: List<Integrante>, file: File)
}