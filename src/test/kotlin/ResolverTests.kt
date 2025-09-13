import constants.Nationalities
import model.Player
import model.Team
import model.enums.Position
import org.junit.jupiter.api.assertThrows
import resolver.Resolver
import kotlin.test.Test
import kotlin.test.assertEquals

class ResolverTests {
    private val teamA = Team("Team A", "City A")
    private val teamB = Team("Team B", "City B")
    private val players = listOf(
        Player("Player1", teamA, Position.DEFENDER, Nationalities.GERMANY, null, 10_000, 20, 5, 2, 1, 0),
        Player("Player2", teamA, Position.MIDFIELD, "France", "Agency1", 8_000, 18, 7, 3, 2, 1),
        Player("Player3", teamB, Position.DEFENDER, "Spain", null, 15_000, 25, 6, 4, 0, 2),
        Player("Player4", teamB, Position.FORWARD, Nationalities.GERMANY, "Agency2", 50_000, 30, 20, 10, 3, 1)
    )
    private val resolver = Resolver(players)

    @Test
    fun getCountWithoutAgencyReturnsCorrectCount() {
        val count = resolver.getCountWithoutAgency()

        assertEquals(2, count)
    }

    @Test
    fun getBestScorerDefenderReturnsDefenderWithMostGoals() {
        val bestDefender = resolver.getBestScorerDefender()

        assertEquals("Player3" to 6, bestDefender)
    }

    @Test
    fun getBestScorerDefenderThrowsExceptionWhenNoDefenders() {
        val noDefendersResolver = Resolver(players.filter { it.position != Position.DEFENDER })

        val exception = assertThrows<Exception> { noDefendersResolver.getBestScorerDefender() }
        assertEquals("Среди игроков нет защитников.", exception.message)
    }

    @Test
    fun getTheExpensiveGermanPlayerPositionReturnsCorrectPosition() {
        val position = resolver.getTheExpensiveGermanPlayerPosition()

        assertEquals(Position.FORWARD.displayName, position)
    }

    @Test
    fun `getTheExpensiveGermanPlayerPosition throws exception when no Germans`() {
        val noGermansResolver = Resolver(players.filter { it.nationality != Nationalities.GERMANY })

        val exception = assertThrows<Exception> { noGermansResolver.getTheExpensiveGermanPlayerPosition() }
        assertEquals("Среди игроков нет немцев.", exception.message)
    }

    @Test
    fun getTheRudestTeamReturnsTeamWithHighestAverageRedCards() {
        val rudestTeam = resolver.getTheRudestTeam()

        assertEquals(teamB, rudestTeam)
    }

    @Test
    fun getTheRudestTeamThrowsExceptionWhenNoTeams() {
        val emptyResolver = Resolver(emptyList())

        val exception = assertThrows<Exception> { emptyResolver.getTheRudestTeam() }
        assertEquals("Нет ни одной команды.", exception.message)
    }
}