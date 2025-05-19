package org.example.newteam.gestion.mapper

import org.example.newteam.gestion.dao.IntegranteEntity
import org.example.newteam.gestion.models.Especialidad
import org.example.newteam.gestion.models.Posicion
import org.example.newteam.gestion.dto.IntegranteDTO
import org.example.newteam.gestion.dto.IntegranteXmlDTO
import org.example.newteam.gestion.models.Entrenador
import org.example.newteam.gestion.models.Integrante
import org.example.newteam.gestion.models.Jugador
import org.example.newteam.gestion.viewmodels.EquipoViewModel
import java.time.LocalDate

/**
 * Convierte un [IntegranteEntity] en un [Integrante]
 */
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
            imagen = imagen,
            minutos_jugados = minutos_jugados!!
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
            imagen = imagen
        )
    }
}

/**
 * Convierte un [Jugador] en un [IntegranteEntity]
 */
fun Jugador.toEntity(): IntegranteEntity {
    return IntegranteEntity(
        id = id,
        nombre = nombre,
        apellidos = apellidos,
        fecha_nacimiento = fecha_nacimiento,
        fecha_incorporacion = fecha_incorporacion,
        salario = salario,
        pais = pais,
        rol = "Jugador",
        posicion = posicion.toString(),
        especialidad = null,
        dorsal = dorsal,
        altura = altura,
        peso = peso,
        goles = goles,
        partidos_jugados = partidos_jugados,
        createdAt = createdAt,
        updatedAt = updatedAt,
        minutos_jugados = minutos_jugados,
        imagen = imagen
    )
}
/**
 * Convierte un [Entrenador] en un [IntegranteEntity]
 */
fun Entrenador.toEntity(): IntegranteEntity {
    return IntegranteEntity(
        id = id,
        nombre = nombre,
        apellidos = apellidos,
        fecha_nacimiento = fecha_nacimiento,
        fecha_incorporacion = fecha_incorporacion,
        salario = salario,
        pais = pais,
        rol = "Entrenador",
        posicion = null,
        especialidad = especialidad.toString(),
        dorsal = null,
        altura = null,
        peso = null,
        goles = null,
        partidos_jugados = null,
        createdAt = createdAt,
        updatedAt = updatedAt,
        minutos_jugados = null,
        imagen = imagen
    )
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
            partidos_jugados = partidos_jugados!!,
            imagen = imagen,
            minutos_jugados = minutos_jugados!!
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
            especialidad = Especialidad.valueOf(especialidad!!),
            imagen = imagen
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
            partidos_jugados = partidos_jugados!!.toInt(),
            imagen = imagen,
            minutos_jugados = minutos_jugados!!.toInt(),
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
            especialidad = Especialidad.valueOf(especialidad!!),
            imagen = imagen
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
        partidos_jugados = "",
        minutos_jugados = "",
        imagen = imagen
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
        partidos_jugados = partidos_jugados.toString(),
        minutos_jugados = minutos_jugados.toString(),
        imagen = imagen
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
        partidos_jugados = null,
        minutos_jugados = null,
        imagen = imagen
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
        partidos_jugados = partidos_jugados,
        minutos_jugados = minutos_jugados,
        imagen = imagen
    )
}
/**
 * Convierte un [EquipoViewModel.IntegranteState] en un [Jugador]
 */
fun EquipoViewModel.IntegranteState.toJugadorModel(): Integrante {
    return Jugador(
        nombre = this.nombre,
        apellidos = this.apellidos,
        fecha_nacimiento = this.fecha_nacimiento,
        fecha_incorporacion = this.fecha_incorporacion,
        salario = this.salario,
        pais = this.pais,
        imagen = this.imagen,
        posicion = Posicion.valueOf(this.posicion),
        dorsal = this.dorsal,
        altura = this.altura,
        peso = this.peso,
        goles = this.goles,
        partidos_jugados = this.partidos_jugados,
        minutos_jugados = this.minutos_jugados,
    )
}
/**
 * Convierte un [EquipoViewModel.IntegranteState] en un [Entrenador]
 */
fun EquipoViewModel.IntegranteState.toEntrenadorModel(): Integrante {
    return Entrenador(
        nombre = this.nombre,
        apellidos = this.apellidos,
        fecha_nacimiento = this.fecha_nacimiento,
        fecha_incorporacion = this.fecha_incorporacion,
        salario = this.salario,
        pais = this.pais,
        imagen = this.imagen,
        especialidad = Especialidad.valueOf(this.especialidad),
    )
}
