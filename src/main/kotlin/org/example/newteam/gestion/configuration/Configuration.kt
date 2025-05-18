package org.example.newteam.gestion.configuration
import org.lighthousegames.logging.logging
import java.nio.file.Files
import java.nio.file.Path
import java.util.*
import kotlin.io.path.pathString


/**
 * Clase que representa la configuración del programa.
 * @property configurationProperties Propiedades de la configuración
 */
object Configuration {
    private val logger = logging()
    val configurationProperties: ConfigurationProperties = loadConfig()

    /**
     * Carga las propiedades de la configuración desde el fichero .properties
     * @return Las propiedades cargadas
     * @see [ConfigurationProperties]
     * @see [crearDirectorios]
     */
    private fun loadConfig(): ConfigurationProperties {
        logger.debug { "Cargando el fichero de configuración" }

        val propiedades = Properties()

        val cadenaPropiedades = this::class.java.getResourceAsStream("/config.properties")
            ?: throw RuntimeException("No se ha encontrado el fichero de configuración")

        propiedades.load(cadenaPropiedades)


        val directorioActual = System.getProperty("user.dir")

        var directorioDataProperties: String? = propiedades.getProperty("data.directory")
        if (directorioDataProperties.isNullOrEmpty()) {
            directorioDataProperties = "data"
        }

        var directorioBackupProperties: String? = propiedades.getProperty("backup.directory")
        if (directorioBackupProperties.isNullOrEmpty()) {
            directorioBackupProperties = "backup"
        }

        var directorioImagenesProperties: String? = propiedades.getProperty("images.directory")
        if (directorioBackupProperties.isNullOrEmpty()) {
            directorioBackupProperties = "images"
        }


        val directorioData = Path.of(directorioActual, directorioDataProperties).pathString
        val directorioBackup = Path.of(directorioActual, directorioBackupProperties).pathString
        val directorioImagenes = Path.of(directorioActual, directorioImagenesProperties).pathString
        val databaseUrl = propiedades.getProperty("database.url")
        val databaseInitTables = propiedades.getProperty("database.init.tables").toBoolean()
        val cacheSize = propiedades.getProperty("cache.size").toLong()
        val cacheExpiration = propiedades.getProperty("cache.expiration").toLong()


        crearDirectorios(directorioData, directorioBackup, directorioImagenes)

        return ConfigurationProperties(directorioData, directorioBackup, directorioImagenes, databaseUrl, databaseInitTables, cacheSize, cacheExpiration)


    }

    /**
     * Crea directorios si no existen
     * @param directorios Directorio o directorios a crear
     */
    private fun crearDirectorios(vararg directorios: String) {
        directorios.forEach {
            val dir = java.io.File(it)
            logger.debug { "Creando directorio con ruta: $it" }
            Files.createDirectories(dir.toPath())
        }
    }
}