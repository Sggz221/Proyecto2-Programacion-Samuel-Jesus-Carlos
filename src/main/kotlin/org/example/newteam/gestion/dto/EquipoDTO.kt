package org.example.newteam.gestion.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Clase intermedia para la creacion de un XML
 * @property equipo Almacena una lista de integrantes que representan al equipo
 */
@Serializable
@SerialName("equipo")
data class EquipoDTO(
     val equipo: List<IntegranteXmlDTO>
): java.io.Serializable
