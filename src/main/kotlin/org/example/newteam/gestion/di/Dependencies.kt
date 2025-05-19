package org.example.newteam.gestion.di

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.example.newteam.gestion.configuration.Configuration
import org.example.newteam.gestion.dao.EquipoDAO
import org.example.newteam.gestion.dao.UsuarioDAO
import org.example.newteam.gestion.database.JdbiManager
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.repositories.EquipoRepositoryImpl
import org.example.newteam.gestion.service.EquipoServiceImpl
import org.example.newteam.gestion.storage.EquipoStorageImpl
import org.example.newteam.gestion.validator.IntegranteValidator
import org.example.newteam.gestion.viewmodels.EquipoViewModel
import org.jdbi.v3.core.Jdbi
import org.lighthousegames.logging.logging
import java.util.concurrent.TimeUnit

/**
 * Objeto que representa la inyección de las distintas dependencias necesarias para el funcionamiento de la aplicación.
 * @see [JdbiManager]
 * @see[EquipoDAO]
 * @see [Caffeine]
 * @see [EquipoRepositoryImpl]
 * @see [IntegranteValidator]
 * @see [EquipoRepositoryImpl]
 * @see[EquipoServiceImpl]
 * @see[EquipoStorageImpl]
 * @see [EquipoViewModel]
 * @see [UsuarioDAO]
 */
object Dependencies {
    private val logger = logging()

    init {
        logger.debug { "Inicializando gestor de dependencias" }
    }

    /**
     * Provee un JDBI
     * @return [Jdbi]
     */
    fun provideDatabaseManager(): Jdbi {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando JDBI" }
        return JdbiManager.instance
    }

    /**
     * Provee un EquipoDAO
     * @return [EquipoDAO]
     */
    fun provideIntegrantesDao(jdbi: Jdbi): EquipoDAO {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando DAO de Integrantes" }
        return jdbi.onDemand(EquipoDAO::class.java)
    }

    /**
     * Provee una Cache
     * @return[Caffeine]
     * @see [Configuration.configurationProperties]
     */
    private fun provideIntegrantesCache(
        capacity: Long = Configuration.configurationProperties.cacheSize,
        duration: Long = Configuration.configurationProperties.cacheExpiration
    ): Cache<Long, Integrante> {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando Caché de Integrantes (capacidad: $capacity - duración: $duration)" }
        return Caffeine.newBuilder()
            .maximumSize(capacity) // LRU con máximo de x elementos
            .expireAfterWrite(duration, TimeUnit.MILLISECONDS) // Expira x milisegundos después de la escritura
            .build<Long, Integrante>()
    }

    /**
     * Provee un repositorio
     * @return [EquipoRepositoryImpl]
     */
    private fun provideIntegrantesRepository(dao: EquipoDAO): EquipoRepositoryImpl {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando Repositorio de Integrantes" }
        return EquipoRepositoryImpl(dao)
    }

    /**
     * Provee un validador
     * @return[IntegranteValidator]
     */
    private fun provideIntegrantesValidator(): IntegranteValidator {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando Validador de Integrantes" }
        return IntegranteValidator()
    }

    /**
     * Provee un storage
     * @return [EquipoStorageImpl]
     */
    private fun provideIntegrantesStorage(): EquipoStorageImpl {
        logger.debug { "INYECCIÓN DE DEPENDENCIAS: Proporcionando Storage de Integrantes" }
        return EquipoStorageImpl()
    }

    /**
     * Provee un servicio, al que se le inyectan el repositorio, la caché, el validador y el storage.
     * @return [EquipoServiceImpl]
     */
    private fun provideIntegrantesService(
        repository: EquipoRepositoryImpl,
        cache: Cache<Long, Integrante>,
        validator: IntegranteValidator,
        storage: EquipoStorageImpl
    ): EquipoServiceImpl {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando Servicio de Integrantes" }
        return EquipoServiceImpl(repository, cache, validator, storage)
    }

    /**
     * Realiza la inyección de dependencias en el orden necesario.
     * @see provideDatabaseManager
     * @see provideIntegrantesDao
     * @see provideIntegrantesRepository
     * @see provideIntegrantesCache
     * @see provideIntegrantesValidator
     * @see provideIntegrantesStorage
     * @see provideIntegrantesService
     */
    fun getIntegrantesService(): EquipoServiceImpl {
        return provideIntegrantesService(
            repository = provideIntegrantesRepository(provideIntegrantesDao(provideDatabaseManager())),
            cache = provideIntegrantesCache(),
            validator = provideIntegrantesValidator(),
            storage = provideIntegrantesStorage()
        )
    }

    /**
     * Provee un ViewModel
     * @return [EquipoViewModel]
     */
    fun provideViewModel(): EquipoViewModel {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando ViewModel" }
        return EquipoViewModel()
    }

    /**
     * Provee un UsuarioDAO
     * @return [UsuarioDAO]
     */
    fun provideUserDao(jdbi: Jdbi = provideDatabaseManager()): UsuarioDAO {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando DAO de Usuarios" }
        return jdbi.onDemand(UsuarioDAO::class.java)
    }
}