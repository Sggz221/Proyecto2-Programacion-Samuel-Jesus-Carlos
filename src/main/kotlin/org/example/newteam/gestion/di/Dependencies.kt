package org.example.newteam.gestion.di

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.example.newteam.gestion.configuration.Configuration
import org.example.newteam.gestion.dao.VehiculosDAO
import org.example.newteam.gestion.database.JdbiManager
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.repositories.EquipoRepositoryImpl
import org.example.newteam.gestion.service.ServiceImpl
import org.example.newteam.gestion.storage.EquipoStorageImpl
import org.example.newteam.gestion.validator.IntegranteValidator
import org.jdbi.v3.core.Jdbi
import org.lighthousegames.logging.logging
import java.util.concurrent.TimeUnit

object Dependencies {
    private val logger = logging()

    init {
        logger.debug { "Inicializando el gestor de dependencias" }
    }

    fun provideDatabaseManager(): Jdbi {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando JDBI" }
        return JdbiManager.instance
    }

    fun provideIntegrantesDao(jdbi: Jdbi): VehiculosDAO {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando DAO de Integrantes" }
        return jdbi.onDemand(VehiculosDAO::class.java)
    }

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

    private fun provideIntegrantesRepository(dao: VehiculosDAO): EquipoRepositoryImpl {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando Repositorio de Integrantes" }
        return EquipoRepositoryImpl(dao)
    }

    private fun provideIntegrantesValidator(): IntegranteValidator {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando Validador de Integrantes" }
        return IntegranteValidator()
    }

    private fun provideIntegrantesStorage(): EquipoStorageImpl {
        logger.debug { "INYECCIÓN DE DEPENDENCIAS: Proporcionando Storage de Integrantes" }
        return EquipoStorageImpl()
    }

    private fun provideIntegrantesService(
        repository: EquipoRepositoryImpl,
        cache: Cache<Long, Integrante>,
        validator: IntegranteValidator,
        storage: EquipoStorageImpl
    ): ServiceImpl {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando Servicio de Vehículos" }
        return ServiceImpl(repository, cache, validator, storage)
    }

    fun getVehiculosService(): ServiceImpl {
        return provideIntegrantesService(
            repository = provideIntegrantesRepository(provideIntegrantesDao(provideDatabaseManager())),
            cache = provideIntegrantesCache(),
            validator = provideIntegrantesValidator(),
            storage = provideIntegrantesStorage()
        )
    }
}