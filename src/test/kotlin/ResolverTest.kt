import model.Player
import resolver.Resolver
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals

/**
 * Тестовый класс для проверки функциональности класса Resolver
 */
class ResolverTest {

    /**
     * Создает тестовый список игроков
     */
    private fun createTestPlayers(): List<Player> {
        return listOf(
            Player(
                "Player1",
                "TeamA",
                "CityA",
                "DEFENDER",
                "Germany",
                "Agency1",
                1000000,
                10,
                5,
                2,
                1,
                0
            ),
            Player(
                "Player2",
                "TeamA",
                "CityA",
                "DEFENDER",
                "Brazil",
                null,
                2000000,
                15,
                10,
                3,
                2,
                1
            ),
            Player(
                "Player3",
                "TeamB",
                "CityB",
                "FORWARD",
                "Germany",
                "Agency2",
                3000000,
                20,
                15,
                5,
                0,
                0
            ),
            Player(
                "Player4",
                "TeamB",
                "CityB",
                "GOALKEEPER",
                "Qatar",
                "Agency1",
                1500000,
                8,
                0,
                0,
                0,
                2
            ),
            Player(
                "Player5",
                "TeamC",
                "CityC",
                "MIDFIELD",
                "Ecuador",
                null,
                2500000,
                12,
                3,
                7,
                1,
                1
            )
        )
    }

    /**
     * Проверяет корректность подсчета игроков без агентства
     * Ожидается, что из 5 тестовых игроков 2 не имеют агентства
     */
    @Test
    fun testGetCountWithoutAgency() {
        val resolver = Resolver(createTestPlayers())
        assertEquals(2, resolver.getCountWithoutAgency())
    }

    /**
     * Проверяет корректность поиска защитника с наибольшим количеством голов
     * Ожидается, что лучшим бомбардиром среди защитников будет Player2 с 10 голами
     */
    @Test
    fun testGetBestScorerDefender() {
        val resolver = Resolver(createTestPlayers())
        val result = resolver.getBestScorerDefender()
        assertEquals("Player2", result.first)
        assertEquals(10, result.second)
    }

    /**
     * Проверяет корректность определения позиции самого дорогого немецкого игрока
     * Ожидается, что самым дорогим немецким игроком будет нападающий (Player3)
     */
    @Test
    fun testGetTheExpensiveGermanPlayerPosition() {
        val resolver = Resolver(createTestPlayers())
        val result = resolver.getTheExpensiveGermanPlayerPosition()
        assertEquals("Нападающий", result)
    }

    /**
     * Проверяет корректность определения команды с наибольшим средним количеством красных карточек
     * Ожидается, что самой грубой командой будет TeamB (2 красные карточки у Player4)
     */
    @Test
    fun testGetTheRudestTeam() {
        val resolver = Resolver(createTestPlayers())
        val result = resolver.getTheRudestTeam()
        assertEquals("TeamB", result.name)
    }
}