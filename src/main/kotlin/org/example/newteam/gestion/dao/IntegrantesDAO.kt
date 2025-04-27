package org.example.newteam.gestion.dao

import org.jdbi.v3.sqlobject.customizer.Bind
import org.jdbi.v3.sqlobject.customizer.BindBean
import org.jdbi.v3.sqlobject.kotlin.RegisterKotlinMapper
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

@RegisterKotlinMapper(IntegranteEntity::class)
interface IntegrantesDAO {

    @SqlQuery("SELECT * FROM integrantes")
    fun getAll(): List<IntegranteEntity>

    @SqlQuery("SELECT * FROM integrantes where id = :id")
    fun getById(@Bind ("id") id: Long): IntegranteEntity?

    @SqlUpdate("INSERT INTO integrantes (nombre, apellidos, fecha_nacimiento, fecha_incorporacion, salario, pais, rol, especialidad, posicion, dorsal, altura, peso, goles, partidos_jugados, createdAt, updatedAt, isDeleted) VALUES (:nombre, :apellidos, :fecha_nacimiento, :fecha_incorporacion, :salario, :pais, :rol, :especialidad, :posicion, :dorsal, :altura, :peso, :goles, :partidos_jugados, :createdAt, :updatedAt, :isDeleted)")
    @GetGeneratedKeys("id") //Por que como el id es autonumérico y generado por la BBDD, lo necesitamos, es lo que devuelve la función
    fun save(@BindBean integrante: IntegranteEntity): Int

    @SqlUpdate("DELETE FROM integrantes where id = :id")
    fun delete(@Bind("id") id: Long): Int // Devuelve el número de filas de la tabla afectadas. Si devuelve 1, se ha eliminado el integrante. Si devuelve 0, no porque no existe ningún integrante con ese id.

    @SqlUpdate("UPDATE integrantes SET nombre = :nombre, apellidos = :apellidos, fecha_nacimiento = :fecha_nacimiento, fecha_incorporacion = :fecha_incorporacion, salario = :salario, pais = :pais, rol = :rol, especialidad = :especialidad, posicion = :posicion, dorsal = :dorsal, altura = :altura, peso = :peso, goles = :goles, partidos_jugados = :partidos_jugados, createdAt = :createdAt, updatedAt = :updatedAt, isDeleted = :isDeleted WHERE id = :id")
    fun update(@BindBean vehiculo: IntegranteEntity): Int // Devuelve el número de filas de la tabla afectadas. Si devuelve 1, se ha actualizado el integrante. Si devuelve 0, no porque no existe ningún integrante con ese id.
}