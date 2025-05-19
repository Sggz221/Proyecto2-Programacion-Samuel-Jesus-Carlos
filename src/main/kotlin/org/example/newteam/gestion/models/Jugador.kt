package org.example.newteam.gestion.models

import java.time.LocalDate
import java.time.LocalDateTime
/**
 * Clase que representa un Jugador y hereda de [Integrante]
 * @param id [Long] Identificador
 * @param nombre [String] Nombre
 * @param apellidos [String] Apellidos
 * @param fecha_nacimiento [LocalDate] Fecha de nacimiento
 * @param fecha_incorporacion [LocalDate] Fecha de incorporacion al equipo
 * @param pais [String] Pais de origen
 * @param createdAt [LocalDateTime] Fecha y hora a la que se creo el objeto
 * @param updatedAt [LocalDateTime] Fecha y hora a la que se actualizo el objeto por ultima vez
 * @param isDeleted [Boolean] Campo que identifica si esta operativo o no
 * @param posicion [Posicion] Posicion de la que juega el jugador
 * @param dorsal [Int] Numero del jugador en el equipo
 * @param altura [Double] Altura en metros del jugador
 * @param peso  [Double] Peso en kilogramos del jugador
 * @param goles [Int] Numero de goles que ha marcado el jugador en total
 * @param partidos_jugados [Int] Numero de partidos que ha jugado el jugador en total
 * @param minutos_jugados [Int] Numero de minutos que ha jugado el jugador en total
 * @param imagen [String] Imagen de perfil del jugador
 * @property rol [String] Rol del integrante, en este caso, es un jugador
 */
class Jugador(
    id: Long = 0L,
    nombre: String,
    apellidos: String,
    fecha_nacimiento: LocalDate,
    fecha_incorporacion: LocalDate,
    salario: Double,
    pais: String,
    createdAt: LocalDateTime = LocalDateTime.now(),
    updatedAt: LocalDateTime = LocalDateTime.now(),
    imagen: String,
    val posicion: Posicion,
    val dorsal: Int,
    val altura: Double,
    val peso: Double,
    val goles: Int,
    val partidos_jugados: Int,
    val minutos_jugados: Int
): Integrante(id = id, nombre = nombre, apellidos = apellidos, fecha_nacimiento = fecha_nacimiento, fecha_incorporacion = fecha_incorporacion, salario = salario, pais = pais, createdAt = createdAt,updatedAt = updatedAt, imagen = imagen) {
    /**
     * Sobreescribe la funcion [toString] predeterminada dandole un formato mas legible
     */
    override fun toString(): String {
        return "Jugador(id= $id, nombre= $nombre, apellidos= $apellidos, fecha_nacimiento= $fecha_nacimiento, fecha_incorporacion= $fecha_incorporacion, salario= $salario, pais = $pais, createdAt= $createdAt, updatedAt= $updatedAt, posicion= $posicion, dorsal= $dorsal, altura= $altura, peso= $peso, goles= $goles, partidos_jugados= $partidos_jugados, minutos_jugados = $minutos_jugados, imagen = $imagen)"
    }

    val rol: String = "Jugador"

    val miEspecialidad = posicion.toString()
}