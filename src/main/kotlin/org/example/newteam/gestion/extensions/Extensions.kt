package org.example.newteam.gestion.extensions

import org.example.newteam.gestion.models.Entrenador
import org.example.newteam.gestion.models.Especialidad
import org.example.newteam.gestion.models.Jugador
import org.example.newteam.gestion.models.Posicion
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Crea una copia de un objeto de la clase Jugador
 * @param newId Nuevo id que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newNombre Nuevo nombre que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newApellidos Nuevos apellidos que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param new_fecha_nacimiento Nueva fecha de nacimiento que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param new_fecha_incorporacion Nueva fecha de incorporacion que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newPais Nuevo pais que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newSalario Nuevo salario que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newIsDeleted Nuevo campo de borrado que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newPosicion Nueva posicion que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newDorsal Nueva dorsal que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newAltura Nueva altura que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newPeso Nuevo peso que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newGoles Nuevo numero de goles que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param new_partidos_jugados Nuevo numero de partidos jugados que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @timeStamp Franja de tiempo que recibira el objeto, por defecto el momento en el que se llama a la funcion
 * @return La copia del objeto creado
 */
fun Jugador.copy(
    newId: Long= this.id,
    newNombre: String= this.nombre,
    newApellidos: String = this.apellidos,
    new_fecha_nacimiento: LocalDate = this.fecha_nacimiento,
    new_fecha_incorporacion: LocalDate = this.fecha_incorporacion,
    newSalario: Double = this.salario,
    newPais: String = this.pais,
    newIsDeleted: Boolean = this.isDeleted,
    newPosicion: Posicion = this.posicion,
    newDorsal: Int = this.dorsal,
    newAltura: Double = this.altura,
    newPeso: Double = this.peso,
    newGoles: Int  = this.goles,
    new_partidos_jugados: Int  = this.partidos_jugados,
    timeStamp: LocalDateTime = LocalDateTime.now()
): Jugador {
    return Jugador(
        id = newId,
        nombre = newNombre,
        apellidos = newApellidos,
        fecha_nacimiento = new_fecha_nacimiento,
        fecha_incorporacion = new_fecha_incorporacion,
        salario = newSalario,
        pais = newPais,
        createdAt = timeStamp,
        updatedAt = timeStamp,
        isDeleted = newIsDeleted,
        posicion = newPosicion,
        dorsal = newDorsal,
        altura = newAltura,
        peso = newPeso,
        goles = newGoles,
        partidos_jugados = new_partidos_jugados
    )
}
/**
 * Crea una copia de un objeto de la clase Entrenador
 * @param newId Nuevo id que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newNombre Nuevo nombre que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newApellidos Nuevos apellidos que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param new_fecha_nacimiento Nueva fecha de nacimiento que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param new_fecha_incorporacion Nueva fecha de incorporacion que recibira el objeto en la copia si desea actualizar, por defecto la misma que antes
 * @param newPais Nuevo pais que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newSalario Nuevo salario que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newIsDeleted Nuevo campo de borrado que recibira el objeto en la copia si desea actualizar, por defecto el mismo que antes
 * @param newEspecialidad Nueva especialidad que recibira el objeto, por defecto la misma de antes
 * @return La copia del objeto creado
 */
fun Entrenador.copy(
    newId: Long= this.id,
    newNombre: String= this.nombre,
    newApellidos: String = this.apellidos,
    new_fecha_nacimiento: LocalDate = this.fecha_nacimiento,
    new_fecha_incorporacion: LocalDate = this.fecha_incorporacion,
    newSalario: Double = this.salario,
    newPais: String = this.pais,
    newIsDeleted: Boolean = this.isDeleted,
    newEspecialidad: Especialidad = this.especialidad,
    timeStamp: LocalDateTime = LocalDateTime.now()
): Entrenador {
    return Entrenador(
        id = newId,
        nombre = newNombre,
        apellidos = newApellidos,
        fecha_nacimiento = new_fecha_nacimiento,
        fecha_incorporacion = new_fecha_incorporacion,
        salario = newSalario,
        pais = newPais,
        createdAt = timeStamp,
        updatedAt = timeStamp,
        isDeleted = newIsDeleted,
        especialidad = newEspecialidad,
    )
}