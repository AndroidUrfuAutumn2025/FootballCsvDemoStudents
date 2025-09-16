import model.Player
import resolver.Resolver
import kotlin.test.Test
import kotlin.test.assertEquals

class ResolverTest {

    @Test
    fun testGetCountWithoutAgency() {
        val testPlayers = listOf(
            Player("Player1", "TeamA", "City", "DEFENDER", "Russia", "Agency1", 100, 10, 5, 2, 1, 0),
            Player("Player2", "TeamA", "City", "FORWARD", "Germany", null, 200, 15, 10, 3, 2, 1),
            Player("Player3", "TeamB", "City", "MIDFIELD", "Brazil", "Agency2", 150, 12, 3, 5, 0, 0)
        )

        val resolver = Resolver(testPlayers)
        assertEquals(1, resolver.getCountWithoutAgency())
    }

    @Test
    fun testGetBestScorerDefender() {
        val testPlayers = listOf(
            Player("Def1", "TeamA", "City", "DEFENDER", "Russia", "Agency", 100, 10, 3, 2, 1, 0),
            Player("Def2", "TeamB", "City", "DEFENDER", "Germany", "Agency", 200, 15, 7, 3, 2, 1),
            Player("Forward", "TeamC", "City", "FORWARD", "Brazil", "Agency", 150, 12, 10, 5, 0, 0)
        )

        val resolver = Resolver(testPlayers)
        val result = resolver.getBestScorerDefender()
        assertEquals("Def2", result.first)
        assertEquals(7, result.second)
    }

    @Test
    fun testGetTheExpensiveGermanPlayerPosition() {
        val testPlayers = listOf(
            Player("Player1", "TeamA", "City", "DEFENDER", "Germany", "Agency", 100, 10, 5, 2, 1, 0),
            Player("Player2", "TeamB", "City", "FORWARD", "Germany", "Agency", 300, 15, 10, 3, 2, 1),
            Player("Player3", "TeamC", "City", "MIDFIELD", "Brazil", "Agency", 200, 12, 3, 5, 0, 0)
        )

        val resolver = Resolver(testPlayers)
        val result = resolver.getTheExpensiveGermanPlayerPosition()
        assertEquals("Нападающий", result)
    }
}