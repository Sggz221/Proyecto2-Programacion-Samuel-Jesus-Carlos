package org.example.repositories

/**
 * Interfaz que crea los contratos que tendra el CRUD
 */
interface CrudRepository<ID, T> {
    fun save(entity: T): T
    fun delete(id: ID): T?
    fun update(id: ID, entity: T): T?
    fun getAll(): List<T>
    fun getById(id: ID): T?
}