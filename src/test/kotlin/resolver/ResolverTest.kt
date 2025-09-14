package resolver

import model.Player
import model.Position
import model.Team
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ResolverTest {

    private lateinit var teamA: Team
    private lateinit var teamB: Team
    private lateinit var players: List<Player>
    private lateinit var resolver: Resolver

    @BeforeEach
    fun setUp() {
        teamA = Team("Team A", "City A")
        teamB = Team("Team B", "City B")

        val p1 = Player(
            name = "Defender 1",
            team = teamA,
            position = Position.DEFENDER,
            nationality = "Germany",
            agency = null,
            transferCost = 1_000_000,
            participations = 10,
            goals = 5,
            assists = 1,
            yellowCards = 2,
            redCards = 1
        )

        val p2 = Player(
            name = "Forward 1",
            team = teamA,
            position = Position.FORWARD,
            nationality = "Brazil",
            agency = "Some Agency",
            transferCost = 2_000_000,
            participations = 12,
            goals = 10,
            assists = 5,
            yellowCards = 1,
            redCards = 0
        )

        val p3 = Player(
            name = "Defender 2",
            team = teamB,
            position = Position.DEFENDER,
            nationality = "Germany",
            agency = "Agency",
            transferCost = 3_000_000,
            participations = 15,
            goals = 7,
            assists = 2,
            yellowCards = 3,
            redCards = 3
        )

        val p4 = Player(
            name = "Forward 2",
            team = teamB,
            position = Position.FORWARD,
            nationality = "Germany",
            agency = null,
            transferCost = 5_000_000,
            participations = 8,
            goals = 12,
            assists = 4,
            yellowCards = 0,
            redCards = 2
        )

        // добавляем игроков в команды
        teamA.players.addAll(listOf(p1, p2))
        teamB.players.addAll(listOf(p3, p4))

        players = listOf(p1, p2, p3, p4)
        resolver = Resolver(players)
    }

    @Test
    fun `test getCountWithoutAgency`() {
        val count = resolver.getCountWithoutAgency()

        assertEquals(2, count)
    }

    @Test
    fun `test getBestScorerDefender`() {
        val (name, goals) = resolver.getBestScorerDefender()

        assertEquals("Defender 2", name)
        assertEquals(7, goals)
    }

    @Test
    fun `test getTheExpensiveGermanPlayerPosition`() {
        val position = resolver.getTheExpensiveGermanPlayerPosition()

        assertEquals(Position.FORWARD.russianName, position)
    }

    @Test
    fun `test getTheRudestTeam`() {
        val rudestTeam = resolver.getTheRudestTeam()

        assertEquals(teamB, rudestTeam)
    }
}