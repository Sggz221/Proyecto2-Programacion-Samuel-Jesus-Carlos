package org.example.newteam.gestion.configuration

/**
 * Clase que representa las propiedades de la configuración del programa
 * @property dataDirectory directorio del que se leerán los ficheros para cargar información
 * @property backupDirectory directorio en el que se exportarán los ficheros
 * @property databaseUrl cadena necesaria para la creación de la base de datos h2
 * @property databaseInitTables propiedad que define si se creará o no la tabla integrantes
 */
data class ConfigurationProperties(
    val dataDirectory: String,
    val backupDirectory: String,
    val databaseUrl: String,
    val databaseInitTables: Boolean
)
