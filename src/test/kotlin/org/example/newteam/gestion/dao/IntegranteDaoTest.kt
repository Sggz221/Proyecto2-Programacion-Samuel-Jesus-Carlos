package org.example.newteam.gestion.dao

import org.example.newteam.gestion.database.JdbiManager
import org.example.newteam.gestion.di.Dependencies
import org.jdbi.v3.core.Jdbi
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import java.time.LocalDate
import java.time.LocalDateTime

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegranteDaoTest {
    private lateinit var dao: EquipoDAO

    private val jugador: IntegranteEntity = IntegranteEntity(
        id = 1,
        nombre = "Rodolfo",
        apellidos = "Suárez",
        fecha_nacimiento = LocalDate.of(1970, 1, 1),
        fecha_incorporacion = LocalDate.of(2020, 1, 1),
        salario = 12000.0,
        pais = "España",
        rol = "Jugador",
        especialidad = null,
        posicion = "DELANTERO",
        dorsal = 18,
        altura = 1.89,
        peso = 66.6,
        goles = 1,
        partidos_jugados = 6969,
        minutos_jugados = 42000,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        imagen = "media/profile_picture.png"
    )

    private val entrenador: IntegranteEntity = IntegranteEntity(
        id = 1,
        nombre = "Rodolfo",
        apellidos = "Suárez",
        fecha_nacimiento = LocalDate.of(1970, 1, 1),
        fecha_incorporacion = LocalDate.of(2020, 1, 1),
        salario = 12000.0,
        pais = "España",
        rol = "Entrenador",
        especialidad = "",
        posicion = null,
        dorsal = null,
        altura = null,
        peso = null,
        goles = null,
        partidos_jugados = null,
        minutos_jugados = null,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
        imagen = "media/profile_picture.png"
    )

    @BeforeAll
    fun setUp() {
        // Inicializamos la BD
        val jdbi = JdbiManager(true).jdbi
        val dao = Dependencies.provideUserDao(jdbi)
    }
    @AfterAll
    fun tearDown() {

    }


}