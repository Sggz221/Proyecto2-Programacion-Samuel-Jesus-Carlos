package org.example.newteam.gestion.service

import com.github.benmanes.caffeine.cache.Cache
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.newteam.gestion.errors.GestionErrors
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.repositories.EquipoRepositoryImpl
import org.example.newteam.gestion.storage.*
import org.example.newteam.gestion.validator.IntegranteValidator
import org.lighthousegames.logging.logging
import java.io.File


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
    private val repository: EquipoRepositoryImpl,
    private val cache: Cache<Long, Integrante>,
    private val validator: IntegranteValidator,
    private val storage: EquipoStorageImpl
): Service {
    private val logger = logging()

    /**
     * Importa un fichero de una ruta especificada por parametro y segun su extension llama al almacenamiento indicado para su correcto manipulamiento
     * @param filePath [String] Cadena de texto que indica la ruta de un archivo
     */
    override fun importFromFile(filePath: String) {
        logger.debug { "Importando integrantes del fichero $filePath" }

        val file = File(filePath)
        val equipo : List<Integrante> = storage.fileRead(file)
        equipo.forEach {repository.save(it)}

    }

    /**
     * Exporta un fichero de una ruta especificada por parametro y segun su extension llama al almacenamiento indicado para su correcto manipulamiento
     * @param filePath [String] Cadena de texto que indica la ruta de un archivo
     */
    override fun exportToFile(filePath: String) {
        logger.debug { "Exportando integrantes al fichero $filePath" }

        val file = File(filePath)
        storage.fileWrite(repository.getAll(), file)

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
     * @return [GestionErrors.NotFoundError] Si no encuentra al integrante
     * @return [List] de [Integrante]
     * @see [CacheImpl.get]
     * @see [CacheImpl.put]
     * @see [EquipoRepositoryImpl.getById]
     */
    override fun getById(id: Long): Result<Integrante, GestionErrors> {
        logger.debug { "Obteniendo el integrante del equipo con id $id" }

        var result = cache.getIfPresent(id)

        if (result == null){

            result = repository.getById(id)

            if (result == null){
                return Err(GestionErrors.NotFoundError("Integrante no encontrado con id $id"))
            } else {
                cache.put(id,result)
                return Ok(result)
            }
        }
        return Ok(result)
    }

    /**
     * Guarda a un [Integrante] en el repositorio despues de validarlo
     * @return El integrante guardado
     * @see [EquipoRepositoryImpl.save]
     * @see [IntegranteValidator.validar]
     */
    override fun save(integrante: Integrante): Result<Integrante, GestionErrors> {
        logger.debug { "Guardando integrante" }
        validator.validar(integrante)
        return Ok(repository.save(integrante))
    }

    /**
     * Actualiza un integrante del equipo despues de validarlo
     * @param id [Long] Identificador del objeto
     * @param integrante [Integrante] Objeto que se quiere guardar
     * @return [GestionErrors.NotFoundError] si no encuentra al integrante
     * @return El integrante guardado
     * @see [IntegranteValidator.validar]
     * @see [EquipoRepositoryImpl.update]
     * @see [CacheImpl.remove]
     */
    override fun update(id: Long, integrante: Integrante): Result<Integrante, GestionErrors> {
        logger.debug { "Actualizando integrante" }
        validator.validar(integrante)

        val actualizado: Integrante? = repository.update(id,integrante)

        if (actualizado == null) {
            return Err(GestionErrors.NotFoundError("Integrante no encontrado con id $id"))
        } else {
            cache.invalidate(id)
        }

        return Ok(actualizado)
    }

    /**
     * Borra un integrante del repositorio y la cache en base a un Id
     * @param id [Long] Identificador del objeto
     * @throws [Exceptions.NotFoundException] si no encuentra al integrante
     * @return El integrante
     * @see [EquipoRepositoryImpl.delete]
     * @see [CacheImpl.remove]
     */
    override fun delete(id: Long): Result<Integrante, GestionErrors> {
        logger.debug { "Borrando integrante" }

        val borrado: Integrante? = repository.delete(id)

        if (borrado == null) {
            return Err(GestionErrors.NotFoundError("Integrante no encontrado con id $id"))
        } else {
            cache.invalidate(id)
        }

        return Ok(borrado)
    }
    /**
     * Borra logicamente a un integrante, en esencia es igual a [update] solo que para un campo especifico de [Integrante.isDeleted]
     * @param id [Long] Identificador del objeto
     * @param integrante [Integrante] Objeto que se quiere guardar
     * @return [GestionErrors.NotFoundError] si no encuentra al integrante
     * @return El integrante guardado
     * @see [EquipoRepositoryImpl.deleteLogical]
     * @see [CacheImpl.remove]
     */
    override fun deleteLogical(id: Long, integrante: Integrante): Result<Integrante, GestionErrors> {
        logger.debug { "Borrando lógicamente integrante" }

        val borrado: Integrante? = repository.deleteLogical(id,integrante)

        if (borrado == null) {
            return Err(GestionErrors.NotFoundError("Integrante no encontrado con id $id"))
        } else {
            cache.invalidate(id)
        }

        return Ok(borrado)
    }
}