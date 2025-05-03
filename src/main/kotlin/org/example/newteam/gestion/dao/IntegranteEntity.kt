package org.example.newteam.gestion.dao

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Clase que representa a un Integrante en forma de Entidad, para la base de datos.
 * @property id Identificador del objeto
 * @property nombre Nombre del integrante
 * @property apellidos Apellidos del integrante
 * @property fecha_nacimiento Fecha de nacimiento del integrante
 * @property fecha_incorporacion Fecha de incorporacion al equipo del integrante
 * @property pais Pais de origen del integrante
 * @property especialidad Especializacion del integrante
 * @property salario Salario del integrante
 * @property isDeleted Campo de borrado logico del integrante
 * @property posicion Posicion de la que juega el integrante
 * @property dorsal Dorsal del integrante
 * @property altura Altura
 * @property peso Peso del integrante
 * @property goles Numero de goles que ha marcado el integrante
 * @property partidos_jugados Numero de partidos jugados por el integrante
 */

data class IntegranteEntity(
    val id: Long,
    val nombre: String,
    val apellidos: String,
    val fecha_nacimiento: LocalDate,
    val fecha_incorporacion: LocalDate,
    val salario: Double,
    val pais: String,
    val rol: String,
    val especialidad: String?,
    val posicion: String?,
    val dorsal: Int?,
    val altura: Double?,
    val peso: Double?,
    val goles: Int?,
    val partidos_jugados: Int?,
    val minutos_jugados: Int?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val imagen: String
)