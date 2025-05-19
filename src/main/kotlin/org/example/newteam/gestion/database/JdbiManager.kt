package org.example.newteam.gestion.database

import org.example.newteam.gestion.configuration.Configuration
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.lighthousegames.logging.logging
import java.io.File

/**
 * Clase que representa el JDBI, para simplificar las interacciones con la base de datos.
 * @property isForTest parámetro para distinguir si la base de datos a ejecutar va a ser en memoria (para test) o en archivo (para el funcionamiento normal de la app).
 * @property instance instancia del JDBI.
 * @property urlNormal url de creación de la base de datos de uso normal.
 * @property urlForTest url de creación de la base de datos para uso en tests.
 * @property urlFinal url definitiva a partir de la cuál se crea la base de datos.
 * @see [Configuration]
 */
class JdbiManager (val isForTest: Boolean = false) {
    private val logger = logging()

    companion object { //al instanciarlo en el companion object, seguimos el patrón singleton, solo habrá una instancia de la clase JdbiManager
        val instance: Jdbi = JdbiManager().jdbi
    }
    val urlNormal = Configuration.configurationProperties.databaseUrl
    val urlForTest = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1"
    val urlFinal = if(isForTest) urlForTest else urlNormal
    val jdbi = Jdbi.create(urlFinal) //se crea la bbdd en base a la url del archivo config.properties

    init {
        logger.debug { "Inicializando JdbiManager" }
        jdbi.installPlugin(KotlinPlugin())
        jdbi.installPlugin(SqlObjectPlugin())


        if (Configuration.configurationProperties.databaseInitTables) {
            logger.debug { "Creación de tablas" }
            // Leemos el fichero de resources
            executeSqlScriptFromResources("tables.sql")
        }
    }

    /**
     * Ejecuta un script SQL.
     */
    fun executeSqlScript(scriptFilePath: String) {
        logger.debug { "Ejecutando script SQL: $scriptFilePath" }
        val script = File(scriptFilePath).readText()
        jdbi.useHandle<Exception> { handle ->
            handle.createScript(script).execute()
        }
    }

    /**
     * Ejecuta un script SQL desde la carpeta resources del proyecto.
     */
    fun executeSqlScriptFromResources(resourcePath: String) {
        logger.debug { "Ejecutando script SQL desde recursos: $resourcePath" }
        val inputStream = ClassLoader.getSystemResourceAsStream(resourcePath)?.bufferedReader()!!
        val script = inputStream.readText()
        jdbi.useHandle<Exception> { handle ->
            handle.createScript(script).execute()
        }
    }
}