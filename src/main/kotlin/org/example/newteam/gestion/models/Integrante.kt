package org.example.newteam.gestion.models

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Clase abstracta que representa a un integrante de un equipo de futbol. Superclase de [Jugador] y [Entrenador]
 * @param id [Long] Identificador
 * @param nombre [String] Nombre
 * @param apellidos [String] Apellidos
 * @param fecha_nacimiento [LocalDate] Fecha de nacimiento
 * @param fecha_incorporacion [LocalDate] Fecha de incorporacion al equipo
 * @param pais [String] Pais de origen
 * @param createdAt [LocalDateTime] Fecha y hora a la que se creo el objeto
 * @param updatedAt [LocalDateTime] Fecha y hora a la que se actualizo el objeto por ultima vez
 * @param isDeleted [Boolean] Campo que identifica si esta operativo o no
 */
abstract class Integrante (
    val id: Long = 0L,
    var nombre: String,
    val apellidos: String,
    val fecha_nacimiento: LocalDate,
    val fecha_incorporacion: LocalDate, //Localizar
    val salario: Double,
    val pais: String,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var imagen: String = "resources/org/example/newteam/media/profile_picture.png"
){

}