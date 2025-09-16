package resolver

import model.Player
import model.Team
import model.enums.Position
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ResolverTest {
    private lateinit var resolver: Resolver
    private lateinit var testPlayers: List<Player>

    @BeforeEach
    fun setUp() {
        testPlayers = listOf(
            Player("Player1", Team("TeamA", "New York"), Position.FORWARD, "Germany", "Agency2", 100000.0, 2, 4, 5, 3, 1),
            Player("Player2", Team("TeamB", "Argentina"), Position.DEFENDER, "Russia", "", 50000.0, 6, 7, 2, 6, 2),
            Player("Player3", Team("TeamA", "New York"), Position.MIDFIELD, "Japan", "Agency3", 21000.0, 10, 4, 1, 6, 5),
            Player("Player4", Team("TeamC", "Russia"), Position.GOALKEEPER, "Russia", "Agency1", 20000.0, 3, 2, 2, 1, 2)
        )
        resolver = Resolver(testPlayers)
    }

    @Test
    fun getCountWithoutAgencyTest() {
        val result = resolver.getCountWithoutAgency()

        assertEquals(1, result)
    }

    @Test
    fun getBestScorerDefenderTest() {
        val result = resolver.getBestScorerDefender()

        assertEquals("Player2" to 7, result)
    }

    @Test
    fun getTheExpensiveGermanPlayerPositionTest() {
        val result = resolver.getTheExpensiveGermanPlayerPosition()

        assertEquals("Нападающий", result)
    }

    @Test
    fun getTheRudestTeamTest() {
        val result = resolver.getTheRudestTeam()

        assertEquals(Team("TeamA", "New York"), result)
    }

    @Test
    fun getDistributionByPositionsTest() {
        val result = resolver.getDistributionByPositions()

        assertEquals(4, result.size)

        val defenderData = result.find { it.positionName == "Защитник" }
        assertNotNull(defenderData)
        assertEquals(1, defenderData?.count)
        assertEquals(25, defenderData?.percentage)

        val forwardData = result.find { it.positionName == "Нападающий" }
        assertNotNull(forwardData)
        assertEquals(1, forwardData?.count)
        assertEquals(25, forwardData?.percentage)

        val midfielderData = result.find { it.positionName == "Полузащитник" }
        assertNotNull(midfielderData)
        assertEquals(1, midfielderData?.count)
        assertEquals(25, midfielderData?.percentage)

        val goalkeeperData = result.find { it.positionName == "Вратарь" }
        assertNotNull(goalkeeperData)
        assertEquals(1, goalkeeperData?.count)
        assertEquals(25, goalkeeperData?.percentage)
    }
}