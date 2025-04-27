package org.example.newteam.gestion.storage

import org.example.newteam.gestion.models.Integrante
import java.io.File

class EquipoStorageImpl(
    private val storageCSV: EquipoStorageCSV = EquipoStorageCSV(),
    private val storageXML: EquipoStorageXML = EquipoStorageXML(),
    private val storageJSON: EquipoStorageJSON = EquipoStorageJSON(),
    private val storageBIN: EquipoStorageBIN = EquipoStorageBIN()
): EquipoStorage {

    override fun fileRead(file: File): List<Integrante> {
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

    override fun fileWrite(equipo: List<Integrante>, file: File) {
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
    }
}