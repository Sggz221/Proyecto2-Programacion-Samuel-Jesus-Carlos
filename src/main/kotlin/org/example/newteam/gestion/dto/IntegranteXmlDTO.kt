package org.example.newteam.gestion.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlValue

/**
 * Clase Serializable de Integrante pensada para un XML
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
 * @property minutos_jugados Número de minutos jugados por el integrante
 * @property imagen Imagen de perfil del integrante
 */
@Serializable
@SerialName("personal")
data class IntegranteXmlDTO(
    @SerialName("id")
    val id: Long,
    @SerialName("tipo")
    @XmlElement
    val rol: String,
    @SerialName("nombre")
    @XmlElement
    val nombre: String,
    @SerialName("apellidos")
    @XmlElement
    val apellidos: String,
    @SerialName("fechaNacimiento")
    @XmlElement
    val fecha_nacimiento: String,
    @SerialName("fechaIncorporacion")
    @XmlElement
    val fecha_incorporacion: String,
    @SerialName("salario")
    @XmlElement
    val salario: Double,
    @SerialName("pais")
    @XmlElement
    val pais: String,
    @SerialName("especialidad")
    @XmlElement
    val especialidad: String?,
    @SerialName("posicion")
    @XmlElement
    val posicion: String?,
    @SerialName("dorsal")
    @XmlElement
    val dorsal: String?,
    @SerialName("altura")
    @XmlElement
    val altura: String?,
    @SerialName("peso")
    @XmlElement
    val peso:String?,
    @SerialName("goles")
    @XmlElement
    val goles: String?,
    @SerialName("partidosJugados")
    @XmlElement
    val partidos_jugados: String?,
    @SerialName("minutos_jugados")
    @XmlElement
    val minutos_jugados: String?,
    @SerialName("imagen")
    @XmlElement
    val imagen: String
): java.io.Serializable