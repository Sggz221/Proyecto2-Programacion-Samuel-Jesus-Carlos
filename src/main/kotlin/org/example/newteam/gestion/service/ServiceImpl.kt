package org.example.newteam.gestion.service

import org.example.Cache.CacheImpl
import org.example.exceptions.Exceptions
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.repositories.EquipoRepositoryImpl
import org.example.newteam.gestion.storage.EquipoStorageBIN
import org.example.newteam.gestion.storage.EquipoStorageCSV
import org.example.newteam.gestion.storage.EquipoStorageJSON
import org.example.newteam.gestion.storage.EquipoStorageXML
import org.example.newteam.gestion.validator.IntegranteValidator
import org.lighthousegames.logging.logging
import java.io.File

private val CACHE_SIZE = 5

/**
 * Clase Servicio que implementa [Service] y se le inyecta la Cache, almacenamiento, repositorio y validador
 * @param repository [EquipoRepositoryImpl] Repositiorio de un equipo de futbol
 * @param cache [CacheImpl] Cache que agiliza las consultas en memoria
 * @param validator [IntegranteValidator] Validador de un [Integrante]
 * @param storageCSV [EquipoStorageCSV] Almacenamiento encargado de gestionar las operaciones con ficheros CSV
 * @param storageJSON [EquipoStorageJSON] Almacenamiento encargado de gestionar las operaciones con ficheros JSON
 * @param storageXML [EquipoStorageXML] Almacenamiento encargado de gestionar las operaciones con ficheros XML
 * @param storageBIN [EquipoStorageBIN] Almacenamiento encargado de gestionar las operaciones con ficheros BIN
 */
class ServiceImpl(
    private val repository: EquipoRepositoryImpl = EquipoRepositoryImpl(),
    private val cache: CacheImpl<Long, Integrante> = CacheImpl(CACHE_SIZE),
    private val validator: IntegranteValidator = IntegranteValidator(),
    private val storageCSV: EquipoStorageCSV = EquipoStorageCSV(),
    private val storageJSON: EquipoStorageJSON = EquipoStorageJSON(),
    private val storageXML: EquipoStorageXML = EquipoStorageXML(),
    private val storageBIN: EquipoStorageBIN = EquipoStorageBIN()
): Service {
    private val logger = logging()

    /**
     * Importa un fichero de una ruta especificada por parametro y segun su extension llama al almacenamiento indicado para su correcto manipulamiento
     * @param filePath [String] Cadena de texto que indica la ruta de un archivo
     */
    override fun importFromFile(filePath: String) {
        logger.debug { "Importando integrantes del fichero $filePath" }

        val file = File(filePath)
        val equipo : List<Integrante>

        when {
            file.name.endsWith(".csv") -> {
                equipo = storageCSV.fileRead(file)
                equipo.forEach { repository.save(it) }
            }
            file.name.endsWith(".json") -> {
                equipo = storageJSON.fileRead(file)
                equipo.forEach { repository.save(it) }
            }
            file.name.endsWith(".xml") -> {
                equipo = storageXML.fileRead(file)
                equipo.forEach { repository.save(it) }
            }
            file.name.endsWith(".bin") -> {
                equipo = storageBIN.fileRead(file)
                equipo.forEach { repository.save(it) }
            }
        }
    }

    /**
     * Exporta un fichero de una ruta especificada por parametro y segun su extension llama al almacenamiento indicado para su correcto manipulamiento
     * @param filePath [String] Cadena de texto que indica la ruta de un archivo
     */
    override fun exportToFile(filePath: String) {
        logger.debug { "Exportando integrantes al fichero $filePath" }

        val file = File(filePath)

        when {
            file.name.endsWith(".csv") -> {
                storageCSV.fileWrite(repository.getAll(),file)
            }
            file.name.endsWith(".json") -> {
                storageJSON.fileWrite(repository.getAll(),file)
            }
            file.name.endsWith(".xml") -> {
                storageXML.fileWrite(repository.getAll(),file)
            }
            file.name.endsWith(".bin") -> {
                storageBIN.fileWrite(repository.getAll(),file)
            }
        }
    }

    /**
     * Llama al repositiorio y devuelve una lista con todos los integrantes del equipo en memoria
     * @return [List] de [Integrante]
     */
    override fun getAll(): List<Integrante> {
        logger.debug { "Obteniendo todos los integrantes del equipo" }
        return repository.getAll()
    }

    /**
     * Llama al repositiorio y devuelve un [Integrante] en funcion de su id y lo guarda en la cache
     * @param id [Long] Identificador del objeto
     * @throws [Exceptions.NotFoundException] Si no encuentra al integrante
     * @return [List] de [Integrante]
     * @see [CacheImpl.get]
     * @see [CacheImpl.put]
     * @see [EquipoRepositoryImpl.getById]
     */
    override fun getById(id: Long): Integrante {
        logger.debug { "Obteniendo el integrante del equipo con id $id" }

        var result = cache.get(id)

        if (result == null){

            result = repository.getById(id)

            if (result == null){
                throw Exceptions.NotFoundException("Integrante no encontrado con id $id")
            } else {
                cache.put(id,result)
                return result
            }
        }

        return result
    }

    /**
     * Guarda a un [Integrante] en el repositorio despues de validarlo
     * @return El integrante guardado
     * @see [EquipoRepositoryImpl.save]
     * @see [IntegranteValidator.validar]
     */
    override fun save(integrante: Integrante): Integrante {
        logger.debug { "Guardando integrante" }
        validator.validar(integrante)
        return repository.save(integrante)
    }

    /**
     * Actualiza un integrante del equipo despues de validarlo
     * @param id [Long] Identificador del objeto
     * @param integrante [Integrante] Objeto que se quiere guardar
     * @throws [Exceptions.NotFoundException] si no encuentra al integrante
     * @return El integrante guardado
     * @see [IntegranteValidator.validar]
     * @see [EquipoRepositoryImpl.update]
     * @see [CacheImpl.remove]
     */
    override fun update(id: Long, integrante: Integrante): Integrante {
        logger.debug { "Actualizando integrante" }
        validator.validar(integrante)

        val actualizado: Integrante? = repository.update(id,integrante)

        if (actualizado == null) {
            throw Exceptions.NotFoundException("Integrante no encontrado con id $id")
        } else {
            cache.remove(id)
        }

        return actualizado
    }

    /**
     * Borra un integrante del repositorio y la cache en base a un Id
     * @param id [Long] Identificador del objeto
     * @throws [Exceptions.NotFoundException] si no encuentra al integrante
     * @return El integrante
     * @see [EquipoRepositoryImpl.delete]
     * @see [CacheImpl.remove]
     */
    override fun delete(id: Long): Integrante {
        logger.debug { "Borrando integrante" }

        val borrado: Integrante? = repository.delete(id)

        if (borrado == null) {
            throw Exceptions.NotFoundException("Integrante no encontrado con id $id")
        } else {
            cache.remove(id)
        }

        return borrado
    }
    /**
     * Borra logicamente a un integrante, en esencia es igual a [update] solo que para un campo especifico de [Integrante.isDeleted]
     * @param id [Long] Identificador del objeto
     * @param integrante [Integrante] Objeto que se quiere guardar
     * @throws [Exceptions.NotFoundException] si no encuentra al integrante
     * @return El integrante guardado
     * @see [EquipoRepositoryImpl.deleteLogical]
     * @see [CacheImpl.remove]
     */
    override fun deleteLogical(id: Long, integrante: Integrante): Integrante {
        logger.debug { "Borrando lógicamente integrante" }

        val borrado: Integrante? = repository.deleteLogical(id,integrante)

        if (borrado == null) {
            throw Exceptions.NotFoundException("Integrante no encontrado con id $id")
        } else {
            cache.remove(id)
        }

        return borrado
    }
}