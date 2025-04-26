package org.example.newteam.gestion.errors

sealed class GestionErrors(val message: String) {
    class InvalidoError (message: String) : GestionErrors ("Integrante no válido: $message")
    class StorageError (message: String) : GestionErrors ("Error en el storage: $message")
    class NotFoundError (message: String) : GestionErrors ("Integrante no encontrado: $message")
}