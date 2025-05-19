package org.example.newteam.gestion.dao

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

/**
 * Interfaz que representa el DAO que realiza las operaciones CRUD sobre la tabla integrantes de la base de datos.
 */
@RegisterKotlinMapper(IntegranteEntity::class)
interface EquipoDAO {

    /**
     * Ejecuta una consulta que devuelve todos los integrantes de la tabla.
     * @return Uns lista de todos los integrantes de la tabla.
     * @see [IntegranteEntity]
     */
    @SqlQuery("SELECT * FROM integrantes")
    fun getAll(): List<IntegranteEntity>

    /**
     * Ejecuta una consulta que devuelve el integrante de la tabla cuyo id coincide con el id buscado, en caso de existir.
     * @return El integrante con el id buscado, o null en caso de no existir.
     * @see [IntegranteEntity]
     */
    @SqlQuery("SELECT * FROM integrantes where id = :id")
    fun getById(@Bind ("id") id: Long): IntegranteEntity?

    /**
     * Inserta un nuevo integrante en la base de datos.
     * @return el id que la base de datos le asigna al nuevo integrante insertado.
     * @see [IntegranteEntity]
     */
    @SqlUpdate("INSERT INTO integrantes (nombre, apellidos, fecha_nacimiento, fecha_incorporacion, salario, pais, rol, especialidad, posicion, dorsal, altura, peso, goles, partidos_jugados, minutos_jugados, createdAt, updatedAt, imagen) VALUES (:nombre, :apellidos, :fecha_nacimiento, :fecha_incorporacion, :salario, :pais, :rol, :especialidad, :posicion, :dorsal, :altura, :peso, :goles, :partidos_jugados, :minutos_jugados, :createdAt, :updatedAt, :imagen)")
    @GetGeneratedKeys("id") //Por que como el id es autonumérico y generado por la BBDD, lo necesitamos, es lo que devuelve la función
    fun save(@BindBean integrante: IntegranteEntity): Int

    /**
     * Elimina de la base de datos el integrante con el id que le entra por parámetro, en caso de existir.
     * @return El número de filas de la tabla integrantes eliminadas.
     * @see [IntegranteEntity]
     */
    @SqlUpdate("DELETE FROM integrantes where id = :id")
    fun delete(@Bind("id") id: Long): Int // Devuelve el número de filas de la tabla afectadas. Si devuelve 1, se ha eliminado el integrante. Si devuelve 0, no porque no existe ningún integrante con ese id.

    /**
     * Actualiza un integrante en la base de datos.
     * @return El número de filas de la tabla integrantes actualizadas.
     * @see [IntegranteEntity]
     */
    @SqlUpdate("UPDATE integrantes SET nombre = :nombre, apellidos = :apellidos, fecha_nacimiento = :fecha_nacimiento, fecha_incorporacion = :fecha_incorporacion, salario = :salario, pais = :pais, rol = :rol, especialidad = :especialidad, posicion = :posicion, dorsal = :dorsal, altura = :altura, peso = :peso, goles = :goles, partidos_jugados = :partidos_jugados, minutos_jugados = :minutos_jugados, createdAt = :createdAt, updatedAt = :updatedAt, imagen = :imagen WHERE id = :id")
    fun update(@BindBean integrante: IntegranteEntity): Int // Devuelve el número de filas de la tabla afectadas. Si devuelve 1, se ha actualizado el integrante. Si devuelve 0, no porque no existe ningún integrante con ese id.
}