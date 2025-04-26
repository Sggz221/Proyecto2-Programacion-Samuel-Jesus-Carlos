package org.example.newteam.gestion.di

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.example.newteam.gestion.configuration.Configuration
import org.example.newteam.gestion.dao.VehiculosDAO
import org.example.newteam.gestion.database.JdbiManager
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.repositories.EquipoRepositoryImpl
import org.example.newteam.gestion.service.ServiceImpl
import org.example.newteam.gestion.storage.EquipoStorageCSV
import org.example.newteam.gestion.validator.IntegranteValidator
import org.example.repositories.EquipoRepository
import org.jdbi.v3.core.Jdbi
import org.lighthousegames.logging.logging
import java.util.concurrent.TimeUnit

object Dependencies {
    private val logger = logging()

    init {
        logger.debug { "Inicializando gestor de dependencias" }
    }

    fun provideDatabaseManager(): Jdbi {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando JDBI" }
        return JdbiManager.instance
    }

    fun provideVehiculosDao(jdbi: Jdbi): VehiculosDAO {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando DAO de Vehiculos" }
        return jdbi.onDemand(VehiculosDAO::class.java)
    }


    /*private fun provideVehiculosCache(
        capacity: Long = Config.cacheSize,
        duration: Long = Config.cacheExpiration
    ): Cache<Long, Integrante> {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando Caché de Vehículos (capacidad: $capacity - duración: $duration)" }
        return Caffeine.newBuilder()
            .maximumSize(capacity) // LRU con máximo de 5 elementos
            .expireAfterWrite(duration, TimeUnit.SECONDS) // Expira x segundos después de la escritura
            .build<Long, Integrante>()
    }*/

    private fun provideVehiculosRepository(dao: VehiculosDAO): EquipoRepositoryImpl {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando Repositorio de Vehículos" }
        return EquipoRepositoryImpl(dao)
    }


    private fun provideVehiculosValidator(): IntegranteValidator {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando Validador de Vehículos" }
        return IntegranteValidator()
    }

    private fun provideVehiculosStorage(): EquipoStorageCSV {
        logger.debug { "INYECCIÓN DE DEPENDENCIAS: Proporcionando Storage de Vehículos" }
        return EquipoStorageCSV()
    }


    private fun provideVehiculosService(
        repository: EquipoRepositoryImpl,
        cache: Cache<Long, Integrante>,
        validator: IntegranteValidator,
        storage: EquipoStorageCSV
    ): ServiceImpl {
        logger.debug { "INYECCIÓN DEPENDENCIAS: Proporcionando Servicio de Vehículos" }
        return ServiceImpl(repository, cache, validator, storage)
    }

    fun getVehiculosService(): ServiceImpl {
        return provideVehiculosService(
            repository = provideVehiculosRepository(provideVehiculosDao(provideDatabaseManager())),
            cache = provideVehiculosCache(),
            validator = provideVehiculosValidator(),
            storage = provideVehiculosStorage()
        )
    }
}