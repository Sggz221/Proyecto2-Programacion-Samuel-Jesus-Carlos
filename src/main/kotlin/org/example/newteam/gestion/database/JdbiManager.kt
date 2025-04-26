package org.example.newteam.gestion.database

import org.example.newteam.gestion.configuration.Configuration
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.lighthousegames.logging.logging
import java.io.File

class JdbiManager private constructor () { //private para que no se pueda instanciar desde fuera
    private val logger = logging()

    companion object { //al instanciarlo en el companion object, seguimos el patrón singleton, solo habrá una instancia de la clase JdbiManager
        val instance: Jdbi = JdbiManager().jdbi
    }

    private val jdbi = Jdbi.create(Configuration.configurationProperties.databaseUrl) //se crea la bbdd en base a la url del archivo config.properties


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

    fun executeSqlScript(scriptFilePath: String) {
        logger.debug { "Ejecutando script SQL: $scriptFilePath" }
        val script = File(scriptFilePath).readText()
        jdbi.useHandle<Exception> { handle ->
            handle.createScript(script).execute()
        }
    }

    fun executeSqlScriptFromResources(resourcePath: String) {
        logger.debug { "Ejecutando script SQL desde recursos: $resourcePath" }
        val inputStream = ClassLoader.getSystemResourceAsStream(resourcePath)?.bufferedReader()!!
        val script = inputStream.readText()
        jdbi.useHandle<Exception> { handle ->
            handle.createScript(script).execute()
        }
    }
}