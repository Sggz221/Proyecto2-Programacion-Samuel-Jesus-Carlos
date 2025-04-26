package org.example.newteam.gestion.mapper

import org.example.newteam.gestion.dao.IntegranteEntity
import org.example.newteam.gestion.models.Especialidad
import org.example.newteam.gestion.models.Posicion
import org.example.newteam.gestion.dto.IntegranteDTO
import org.example.newteam.gestion.dto.IntegranteXmlDTO
import org.example.newteam.gestion.models.Entrenador
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.models.Jugador
import java.time.LocalDate

fun IntegranteEntity.toModel(): Integrante {
    return if(this.rol == "Jugador"){
        Jugador(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fecha_nacimiento = fecha_nacimiento,
            fecha_incorporacion = fecha_incorporacion,
            salario = salario,
            pais = pais,
            posicion = Posicion.valueOf(posicion!!),
            dorsal = dorsal!!,
            altura = altura!!,
            peso = peso!!,
            goles = goles!!,
            partidos_jugados = partidos_jugados!!,
            createdAt = createdAt,
            updatedAt = updatedAt,
            isDeleted = isDeleted
        )
    }
    else{
        Entrenador(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fecha_nacimiento = fecha_nacimiento,
            fecha_incorporacion = fecha_incorporacion,
            salario = salario,
            pais = pais,
            especialidad = Especialidad.valueOf(especialidad!!),
            createdAt = createdAt,
            updatedAt = updatedAt,
            isDeleted = isDeleted
        )
    }
}

/**
 * Funcion de extension que convierte [IntegranteDTO] a un objeto [Integrante] [Jugador] o [Entrenador] segun [IntegranteDTO.rol]
 * @return La version [Integrante] de la DTO. O bien [Jugador] o [Entrenador]
 */
fun IntegranteDTO.toModel(): Integrante {
    return if(this.rol == "Jugador"){
        Jugador(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fecha_nacimiento = LocalDate.parse(fecha_nacimiento),
            fecha_incorporacion = LocalDate.parse(fecha_incorporacion),
            salario = salario,
            pais = pais,
            posicion = Posicion.valueOf(posicion!!),
            dorsal = dorsal!!,
            altura = altura!!,
            peso = peso!!,
            goles = goles!!,
            partidos_jugados = partidos_jugados!!
        )
    }
    else{
        Entrenador(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fecha_nacimiento = LocalDate.parse(fecha_nacimiento),
            fecha_incorporacion = LocalDate.parse(fecha_incorporacion),
            salario = salario,
            pais = pais,
            especialidad = Especialidad.valueOf(especialidad!!)
        )
    }
}
/**
 * Funcion de extension que convierte [IntegranteXmlDTO] a un objeto [Integrante] [Jugador] o [Entrenador] segun [IntegranteXmlDTO.rol]
 * @return La version [Integrante] de la DTO. O bien [Jugador] o [Entrenador]
 */
fun IntegranteXmlDTO.toModel(): Integrante {
    return if(this.rol == "Jugador"){
        Jugador(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fecha_nacimiento = LocalDate.parse(fecha_nacimiento),
            fecha_incorporacion = LocalDate.parse(fecha_incorporacion),
            salario = salario,
            pais = pais,
            posicion = Posicion.valueOf(posicion!!),
            dorsal = dorsal!!.toInt(),
            altura = altura!!.toDouble(),
            peso = peso!!.toDouble(),
            goles = goles!!.toInt(),
            partidos_jugados = partidos_jugados!!.toInt()
        )
    }
    else{
        Entrenador(
            id = id,
            nombre = nombre,
            apellidos = apellidos,
            fecha_nacimiento = LocalDate.parse(fecha_nacimiento),
            fecha_incorporacion = LocalDate.parse(fecha_incorporacion),
            salario = salario,
            pais = pais,
            especialidad = Especialidad.valueOf(especialidad!!)
        )
    }
}

/**
 * Funcion de extension que convierte un [Entrenador] a [IntegranteXmlDTO]
 * @return la version [IntegranteXmlDTO] del objeto con los campos que no tiene vacios
 */
fun Entrenador.toXmlDTO (): IntegranteXmlDTO {
    return IntegranteXmlDTO(
        id = id,
        nombre = nombre,
        apellidos = apellidos,
        fecha_nacimiento = fecha_nacimiento.toString(),
        fecha_incorporacion = fecha_incorporacion.toString(),
        salario = salario,
        pais = pais,
        especialidad = especialidad.toString(),
        rol = "Entrenador",
        posicion = "",
        dorsal = "",
        altura = "",
        peso = "",
        goles = "",
        partidos_jugados = ""
    )
}
/**
 * Funcion de extension que convierte un [Jugador] a [IntegranteXmlDTO]
 * @return la version [IntegranteXmlDTO] del objeto con los campos que no tiene vacios
 */
fun Jugador.toXmlDTO (): IntegranteXmlDTO {
    return IntegranteXmlDTO(
        id = id,
        nombre = nombre,
        apellidos = apellidos,
        fecha_nacimiento = fecha_nacimiento.toString(),
        fecha_incorporacion = fecha_incorporacion.toString(),
        salario = salario,
        pais = pais,
        especialidad = "",
        rol = "Jugador",
        posicion = posicion.toString(),
        dorsal = dorsal.toString(),
        altura = altura.toString(),
        peso = peso.toString(),
        goles = goles.toString(),
        partidos_jugados = partidos_jugados.toString()
    )
}

/**
 * Funcion de extension que convierte un [Entrenador] en su version [IntegranteDTO]
 * @return la version [IntegranteDTO] del objeto con los campos que no tiene vacios
 */
fun Entrenador.toDto (): IntegranteDTO {
    return IntegranteDTO(
        id = id,
        nombre = nombre,
        apellidos = apellidos,
        fecha_nacimiento = fecha_nacimiento.toString(),
        fecha_incorporacion = fecha_incorporacion.toString(),
        salario = salario,
        pais = pais,
        especialidad = especialidad.toString(),
        rol = "Entrenador",
        posicion = "",
        dorsal = null,
        altura = null,
        peso = null,
        goles = null,
        partidos_jugados = null
    )
}
/**
 * Funcion de extension que convierte un [Jugador] en su version [IntegranteDTO]
 * @return la version [IntegranteDTO] del objeto con los campos que no tiene vacios
 */
fun Jugador.toDto (): IntegranteDTO {
    return IntegranteDTO(
        id = id,
        nombre = nombre,
        apellidos = apellidos,
        fecha_nacimiento = fecha_nacimiento.toString(),
        fecha_incorporacion = fecha_incorporacion.toString(),
        salario = salario,
        pais = pais,
        rol = "Jugador",
        especialidad = "",
        posicion = posicion.toString(),
        dorsal = dorsal,
        altura = altura,
        peso = peso,
        goles = goles,
        partidos_jugados = partidos_jugados
    )
}