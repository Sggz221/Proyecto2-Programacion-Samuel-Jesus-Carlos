package org.example.newteam.gestion.storage

import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteam.gestion.models.Integrante
import java.io.File

/**
 * Clase que unifica los tipos de storage
 * @property storageCSV [EquipoStorageCSV] El almacenamiento CSV
 * @property storageJSON [EquipoStorageJSON] El almacenamiento JSON
 * @property storageXML [EquipoStorageXML] El almacenamiento XML
 * @property storageBIN [EquipoStorageBIN] El almacenamiento BIN
 */
class EquipoStorageImpl(
    private val storageCSV: EquipoStorageCSV = EquipoStorageCSV(),
    private val storageXML: EquipoStorageXML = EquipoStorageXML(),
    private val storageJSON: EquipoStorageJSON = EquipoStorageJSON(),
    private val storageBIN: EquipoStorageBIN = EquipoStorageBIN()
): EquipoStorage {
    /**
     * Llama a uno u otro storage en función de la extensión del archivo a leer que le entra por parámetro
     * @param file [File] archivo a leer
     * @return [Result] de [List] [Integrante] o [GestionErrors.StorageError]
     * @see [EquipoStorageCSV]
     * @see [EquipoStorageJSON]
     * @see [EquipoStorageXML]
     * @see [EquipoStorageBIN]
     */
    override fun fileRead(file: File): Result<List<Integrante>, GestionErrors> {
        when {
            file.name.endsWith(".csv") -> {
                return storageCSV.fileRead(file)
            }
            file.name.endsWith(".json") -> {
                return storageJSON.fileRead(file)
            }
            file.name.endsWith(".xml") -> {
                return storageXML.fileRead(file)
            }
            else -> {
                return storageBIN.fileRead(file)
            }
        }
    }

    /**
     * Llama a uno u otro storage en función de la extensión del archivo a escribir que le entra por parámetro
     * @param equipo Lista de integrantes a escribir
     * @param file [File] Archivo a escribir
     * @return [Result] de [List] [Integrante] o [GestionErrors.StorageError]
     * @see [EquipoStorageCSV]
     * @see [EquipoStorageJSON]
     * @see [EquipoStorageXML]
     * @see [EquipoStorageBIN]
     */
    override fun fileWrite(equipo: List<Integrante>, file: File): Result<Unit, GestionErrors> {
        when {
            file.name.endsWith(".csv") -> {
                storageCSV.fileWrite(equipo,file)
            }
            file.name.endsWith(".json") -> {
                storageJSON.fileWrite(equipo,file)
            }
            file.name.endsWith(".xml") -> {
                storageXML.fileWrite(equipo,file)
            }
            file.name.endsWith(".bin") -> {
                storageBIN.fileWrite(equipo,file)
            }
        }
        return Ok(Unit)
    }
}