package resolver

import model.Player
import model.Team
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

class ResolverTest {

    private val testTeam1 = Team("Team A", "City A")
    private val testTeam2 = Team("Team B", "City B")

    private val testPlayers = listOf(
        Player("Player 1", testTeam1, "DEFENDER", "Germany", "Agency 1", 1000000, 10, 5, 2, 1, 0),
        Player("Player 2", testTeam1, "DEFENDER", "Brazil", "", 2000000, 15, 3, 4, 2, 1),
        Player("Player 3", testTeam2, "FORWARD", "Germany", "Agency 2", 5000000, 20, 10, 5, 0, 0),
        Player("Player 4", testTeam2, "MIDFIELD", "France", "Agency 3", 3000000, 12, 2, 3, 1, 0),
        Player("Player 5", testTeam1, "DEFENDER", "Spain", "Agency 1", 1500000, 8, 8, 1, 0, 2)
    )

    private val resolver: IResolver = Resolver(testPlayers)

    @Test
    fun testGetCountWithoutAgency() {
        val result = resolver.getCountWithoutAgency()
        assertEquals(1, result)
    }

    @Test
    fun testGetBestScorerDefender() {
        // Защитники: Player 1 (5 голов), Player 2 (3 гола), Player 5 (8 голов)
        val result = resolver.getBestScorerDefender()
        assertEquals("Player 5", result.first)
        assertEquals(8, result.second)
    }

    @Test
    fun testGetTheExpensiveGermanPlayerPosition() {
        // Немецкие игроки: Player 1 (1M), Player 3 (5M) - самый дорогой FORWARD
        val result = resolver.getTheExpensiveGermanPlayerPosition()
        assertEquals("Нападающий", result)
    }

    @Test
    fun testGetTheRudestTeam() {
        // Team A: Player 2 (1 красная), Player 5 (2 красных) = 3 красных на 3 игроков = 1.0 в среднем
        // Team B: только Player 3 и Player 4 (0 красных) = 0 в среднем
        val result = resolver.getTheRudestTeam()
        assertEquals("Team A", result.name)
    }
}