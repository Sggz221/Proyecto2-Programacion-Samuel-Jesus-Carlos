package org.example.newteam.gestion.storage

import kotlinx.serialization.json.Json
import org.example.newteam.gestion.dto.IntegranteDTO
import org.example.exceptions.Exceptions
import org.example.newteam.gestion.mapper.toDto
import org.example.newteam.gestion.mapper.toModel
import org.example.newteam.gestion.models.Entrenador
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.models.Jugador
import org.lighthousegames.logging.logging
import java.io.File
/**
 * Almacenamiento que Implementa la interfaz [EquipoStorage] para
 * manejar el trato con archivos JSON para poder leer de un archivo
 * un tipo de objeto o al reves
 */
class EquipoStorageJSON: EquipoStorage {
    private var logger = logging()

    /**
     * Permite leer de un archivo una lista de [Integrante]
     * Lee el archivo como una lista de DTO de integrante y lo mapea al modelo segun va leyendo
     * @throws [Exceptions.StorageException] Si el fichero no existe, no es un fichero o no se tienen permisos de lectura
     * @return Lista de integrantes
     */
    override fun fileRead(file: File): List<Integrante> {
        logger.debug { "Leyendo fichero JSON" }

        if (!file.exists() || !file.isFile || !file.canRead()) throw Exceptions.StorageException("El fichero no existe, la ruta especificada no es un fichero o no se tienen permisos de lectura")

        val json = Json { ignoreUnknownKeys = true }

        val jsonString = file.readText() // Leemos cada linea del fichero
        val listaIntegrantesDTO: List<IntegranteDTO> = json.decodeFromString(jsonString) // Convertimos el texto anteriormente leido a una lsita de IntegrantesDTO
        val listaIntegrantes = listaIntegrantesDTO.map { it.toModel() } // Mapeamos la lista de DTO para convertir cada elemento a un modelo segun convenga

        return  listaIntegrantes
    }

    /**
     * Permite leer de un archivo una lista de [Integrante]
     * Lee el archivo como una lista de DTO de integrante y lo mapea al modelo segun va leyendo
     * @throws [Exceptions.StorageException] Si el fichero no existe, no es un fichero o no se tienen permisos de lectura
     * @return Lista de integrantes
     */
    override fun fileWrite(equipo: List<Integrante>, file: File) {
        logger.debug { "Escribiendo integrantes del equipo en fichero JSON" }

        if (!file.parentFile.exists() || !file.parentFile.isDirectory) {
            throw Exceptions.StorageException("El directorio padre del fichero no existe")
        }

        val json = Json { ignoreUnknownKeys = true ; prettyPrint = true }

        val listaIntegrantesDTO: List<IntegranteDTO> = equipo.mapNotNull {
            when (it) {
                is Jugador -> {it.toDto()}
                is Entrenador -> {it.toDto()}
                else -> null
            }
        }  // Convertir integrantes a DTOs segun el modelo
        val jsonString: String = json.encodeToString(listaIntegrantesDTO)  // Serializar a JSON
        file.writeText(jsonString)  // Guardar en el archivo

    }
}