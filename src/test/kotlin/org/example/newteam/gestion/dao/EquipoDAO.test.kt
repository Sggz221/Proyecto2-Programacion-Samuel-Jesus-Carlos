import org.example.newteam.gestion.dao.EquipoDAO
import org.example.newteam.gestion.dao.IntegranteEntity
import org.example.newteam.gestion.database.JdbiManager
import org.example.newteam.gestion.di.Dependencies
import org.example.newteam.gestion.models.Jugador
import org.example.newteam.gestion.models.Posicion
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertEquals

class EquipoDAOTest {

    private lateinit var equipoDAO: EquipoDAO

    val integranteJugador: IntegranteEntity = IntegranteEntity(1, "Juan", "Perez", LocalDate.of(1990, 1, 1), LocalDate.of(2020, 1, 1), 1200.0, "Estonia", "Jugador",null ,"DELANTERO", 12, 1.70, 90.0, 120, 120, 120, LocalDateTime.now() , LocalDateTime.now(), imagen = "media/profile_picture.png")
    val integranteEntrenador: IntegranteEntity = IntegranteEntity(2, "Juan", "Perez", LocalDate.of(1990, 1, 1), LocalDate.of(2020, 1, 1), 1200.0, "Estonia", "Entrenador","ENTRENADOR_PRINCIPAL" ,null, null, null, null, null, null, null, LocalDateTime.now() , LocalDateTime.now(), imagen = "media/profile_picture.png")



    @BeforeEach
    fun setUp() {
        val jdbi = JdbiManager(true).jdbi
        equipoDAO = Dependencies.provideIntegrantesDao(jdbi)
    }

    @Test
    fun `integrantesVacia`() {
        val integrantes = equipoDAO.getAll()

        assertEquals(0, integrantes.size)
    }
    @Test
    fun `agregarIntegrante`() {
    }
}