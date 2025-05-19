package org.example.newteam.gestion.errors

/**
 * Clase que representa los errores que pueden darse durante la ejecución del programa
 * @see [InvalidoError]
 * @see [StorageError]
 * @see [NotFoundError]
 */
sealed class GestionErrors(val message: String) {
    /**
     * Error lanzado cuando sucede un error en la validación de datos del integrante
     */
    class InvalidoError (message: String) : GestionErrors ("Integrante no válido: $message")
    /**
     * Error lanzado cuando sucede un error en el la escritura o lectura de archivos por parte del Storage
     */
    class StorageError (message: String) : GestionErrors ("Error en el storage: $message")
    /**
     * Error lanzado cuando un integrante no es encontrado en la base de datos
     */
    class NotFoundError (message: String) : GestionErrors ("Integrante no encontrado: $message")
}