package resolver

import model.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class ResolverTest {

    private lateinit var testPlayers: List<Player>
    private lateinit var resolver: Resolver

    @BeforeEach
    fun setUp() {
        // Создаём тестовых игроков
        testPlayers = listOf(
            // Игрок 1: Защитник с агентством
            Player(
                name = "Иван Иванов",
                team = Team("Спартак", "Москва"),
                position = Position.DEFENDER,
                nationality = "Russia",
                agency = "Star Agency",
                transferCost = 10_000_000,
                participations = 20,
                goals = 5,
                assists = 3,
                yellowCards = 2,
                redCards = 0
            ),
            // Игрок 2: Немецкий нападающий (самый дорогой)
            Player(
                name = "Томас Мюллер",
                team = Team("Бавария", "Мюнхен"),
                position = Position.FORWARD,
                nationality = "Germany",
                agency = "Top Agent",
                transferCost = 25_000_000,
                participations = 25,
                goals = 15,
                assists = 10,
                yellowCards = 1,
                redCards = 0
            ),
            // Игрок 3: Без агентства
            Player(
                name = "Петр Петров",
                team = Team("Зенит", "Санкт-Петербург"),
                position = Position.MIDFIELD,
                nationality = "Russia",
                agency = null,
                transferCost = 15_000_000,
                participations = 22,
                goals = 8,
                assists = 12,
                yellowCards = 3,
                redCards = 1
            ),
            // Игрок 4: Ещё один защитник (лучший по голам)
            Player(
                name = "Сергей Защитник",
                team = Team("Локомотив", "Москва"),
                position = Position.DEFENDER,
                nationality = "Russia",
                agency = "Agent",
                transferCost = 8_000_000,
                participations = 18,
                goals = 7,  // Больше всех среди защитников
                assists = 4,
                yellowCards = 1,
                redCards = 0
            ),
            // Игрок 5: Игрок с красными карточками (для теста грубой команды)
            Player(
                name = "Грубиян Сидоров",
                team = Team("Грубая команда", "Город"),
                position = Position.DEFENDER,
                nationality = "Russia",
                agency = "Agent",
                transferCost = 5_000_000,
                participations = 10,
                goals = 1,
                assists = 2,
                yellowCards = 4,
                redCards = 3  // Много красных карточек
            ),
            // Игрок 6: Ещё один игрок той же грубой команды
            Player(
                name = "Второй Грубиян",
                team = Team("Грубая команда", "Город"),
                position = Position.MIDFIELD,
                nationality = "Russia",
                agency = "Agent",
                transferCost = 6_000_000,
                participations = 12,
                goals = 2,
                assists = 3,
                yellowCards = 2,
                redCards = 2  // Тоже красные карточки
            )
        )

        resolver = Resolver(testPlayers)
    }

    @Test
    fun `test getCountWithoutAgency should return correct number`() {
        // У нас только 1 игрок без агентства (Петр Петров)
        val result = resolver.getCountWithoutAgency()
        assertEquals(1, result, "Должен быть 1 игрок без агентства")
    }

    @Test
    fun `test getBestScorerDefender should return defender with most goals`() {
        val (name, goals) = resolver.getBestScorerDefender()

        // Ожидаем: Сергей Защитник с 7 голами
        assertEquals("Сергей Защитник", name)
        assertEquals(7, goals)
    }

    @Test
    fun `test getTheExpensiveGermanPlayerPosition should return forward`() {
        val position = resolver.getTheExpensiveGermanPlayerPosition()

        // Самый дорогой немец - Томас Мюллер (нападающий)
        assertEquals("нападающий", position)
    }

    @Test
    fun `test getTheRudestTeam should return team with highest average red cards`() {
        val team = resolver.getTheRudestTeam()

        // "Грубая команда" имеет: (3 + 2) / 2 игрока = 2.5 в среднем
        assertEquals("Грубая команда", team.name)
        assertEquals("Город", team.city)
    }

    @Test
    fun `test getPositionsShare should calculate correct percentages`() {
        val shares = resolver.getPositionsShare()

        // Всего 6 игроков:
        // DEFENDER: 3 игрока (50%)
        // FORWARD: 1 игрок (16.67%)
        // MIDFIELD: 2 игрока (33.33%)
        // GOALKEEPER: 0 игроков (0%)

        assertEquals(0.5, shares[Position.DEFENDER]!!, 0.01)
        assertEquals(0.3333, shares[Position.MIDFIELD]!!, 0.01)
        assertEquals(0.1667, shares[Position.FORWARD]!!, 0.01)

        // GOALKEEPER нет в мапе, потому что 0 игроков
        assertNull(shares[Position.GOALKEEPER])
    }

    @Test
    fun `test getBestScorerDefender with no defenders should return empty pair`() {
        // Создаём список без защитников
        val noDefenders = testPlayers.filter { it.position != Position.DEFENDER }
        val specialResolver = Resolver(noDefenders)

        val (name, goals) = specialResolver.getBestScorerDefender()

        // Когда нет защитников, должен вернуть пустую пару
        assertEquals("", name)
        assertEquals(0, goals)
    }

    @Test
    fun `test getTheExpensiveGermanPlayerPosition with no german players should return empty string`() {
        // Создаём список без немцев
        val noGermans = testPlayers.filter { !it.nationality.equals("Germany", ignoreCase = true) }
        val specialResolver = Resolver(noGermans)

        val position = specialResolver.getTheExpensiveGermanPlayerPosition()

        // Когда нет немецких игроков, должен вернуть пустую строку
        assertEquals("", position)
    }
}