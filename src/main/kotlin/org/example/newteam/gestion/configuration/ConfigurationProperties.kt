package org.example.newteam.gestion.configuration

/**
 * Clase que representa las propiedades de la configuración del programa
 * @property dataDirectory directorio del que se leerán los ficheros para cargar información
 * @property imagesDirectory directorio donde se guardarán las imágenes de perfil subidas por el usuario
 * @property backupDirectory directorio en el que se exportarán los ficheros
 * @property databaseUrl cadena necesaria para la creación de la base de datos h2
 * @property databaseInitTables propiedad que define si se creará o no la tabla integrantes
 * @property cacheSize tamaño de la caché
 * @property cacheExpiration tiempo de expiración de la caché
 */
data class ConfigurationProperties(
    val dataDirectory: String,
    val backupDirectory: String,
    val imagesDirectory: String,
    val databaseUrl: String,
    val databaseInitTables: Boolean,
    val cacheSize: Long,
    val cacheExpiration: Long
)
