package org.example.newteam.gestion.repositories

import org.example.newteam.gestion.dao.IntegranteEntity
import org.example.newteam.gestion.dao.IntegrantesDAO
import org.example.newteam.gestion.extensions.copy
import org.example.newteam.gestion.mapper.toEntity
import org.example.newteam.gestion.mapper.toModel
import org.example.newteam.gestion.models.Entrenador
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.models.Jugador
import org.example.newteam.gestion.validator.IntegranteValidator
import org.example.repositories.EquipoRepository
import org.lighthousegames.logging.logging
import java.time.LocalDateTime

/**
 * Clase que implementa la interfaz [EquipoRepository] para gestionar un equpo de futbol en memoria con una serie de [Integrante] que pueden ser [Jugador] o [Entrenador]
 */
class EquipoRepositoryImpl(
    private val dao: IntegrantesDAO
): EquipoRepository<Long, Integrante> {
    private val logger = logging()
    private val equipo = mutableMapOf<Long, Integrante>()
    private var validator = IntegranteValidator()


    /**
     * Funcion que guarda un [Integrante] en una posicion libre del mapa que representa al equipo
     * @param entity [Integrante] El integrante que se intenta guardar en el mapa
     * @return [Integrante]
     */
    override fun save(entity: Integrante): Integrante {
        logger.debug { "Guardando integrante del equipo..." }
        //Actualizamos los campos createdAt y updatedAt
        val timestamp = LocalDateTime.now()
        val integranteToSave=
            when(entity) {
                is Jugador -> entity.copy(timeStamp = timestamp).toEntity()
                is Entrenador -> entity.copy(timeStamp = timestamp).toEntity()
                else -> null
            }
        //Guardamos el vehículo en la base de datos y lo devolvemos como modelo
        val generatedId = dao.save(integranteToSave!!).toLong()
        val savedIntegrante = dao.getById(generatedId)!!.toModel()
        logger.info { "Integrante guardado" }
        return savedIntegrante
    }

    /**
     * Elimina un [Integrante] del mapa que representa al equipoen base a un ID
     * @param id [Long] el identificador que representa el objeto que se quiere borrar del mapa
     * @return [Integrante] o nulo si no encuentra el objeto
     */
    override fun delete(id: Long): Integrante? {
        logger.debug { "Borrando integrante del equipo con ID: $id" }
        val integranteToDelete: Integrante? = dao.getById(id)?.toModel()

        if (integranteToDelete == null) {
            logger.info { "No se ha encontrado un Integrante con id: $id" }
            return null
        }
        // Si todo sale bien...
        dao.delete(id)
        logger.info { "Integrante con ID: $id se ha eliminado correctamente" }
        return integranteToDelete
    }

    /**
     * Funcion que actualiza un integrante
     *  @param id [Long] el identificador que representa el objeto que se quiere actualizar
     *  @param entity [Integrante] El integrante que se quiere actualizar
     * @return [Integrante] o nulo si no encuentra el objeto
     */
    override fun update(id: Long, entity: Integrante): Integrante? {
        logger.debug{"Actualizando integrante del equipo con ID: $id"}
        val integranteToUpdate: IntegranteEntity? = dao.getById(id)
        // Comprobamos si es null
        if (integranteToUpdate == null) {
            logger.info { "No se ha encontrado un Integrante con id: $id" }
            return null
        }
        // Si todo sale bien...
        val timestamp = LocalDateTime.now()
        val integranteUpdated = integranteToUpdate.copy(updatedAt = timestamp).toModel()
        dao.update(integranteToUpdate)
        logger.info { "Integrante con ID: $id se ha actualizado correctamente" }
        return integranteUpdated
    }

    /**
     * Obtiene todos los [Integrante] del mapa y lo convierte en una [List] de [Integrante]
     * @return [List] de [Integrante]
     */
    override fun getAll(): List<Integrante> {
        logger.debug { "Obteniendo todos los integrantes" }
        return dao.getAll().map { it.toModel() }
    }

    /**
     * Obtiene un [Integrante] en base a un id
     * @param id [Long] Identificador que representa el objeto
     * @return [Integrante] o nulo si no encuentra el objeto
     */
    override fun getById(id: Long): Integrante? {
        logger.debug { "Obteniendo integrante del equipo con ID: $id" }
        return dao.getById(id)?.toModel()
    }
}