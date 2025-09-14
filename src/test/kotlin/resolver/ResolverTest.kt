package resolver

import model.Player
import model.Team
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

/**
 * Тесты для класса Resolver
 */
class ResolverTest {


    /**
     * Создание команд
     */
    private val teamA = Team("Team A", "City A")
    private val teamB = Team("Team B", "City B")

    /**
     * Создание игроков
     */
    private val players = listOf(
        Player("John", teamA, "GOALKEEPER", "Germany", null, 50_000_000, 0, 0, 0, 0, 0),
        Player("Max", teamA, "DEFENDER", "Germany", "Agency1", 20_000_000, 5, 1, 0, 0, 1),
        Player("Alex", teamA, "DEFENDER", "Spain", null, 25_000_000, 10, 10, 0, 0, 2),
        Player("Luke", teamB, "MIDFIELD", "France", "Agency2", 30_000_000, 8, 1, 0, 0, 1),
        Player("Tom", teamB, "FORWARD", "Germany", "Agency1", 60_000_000, 15, 0, 0, 0, 0),
        Player("Sam", teamB, "DEFENDER", "Germany", null, 10_000_000, 3, 3, 0, 0, 3)
    )

    /**
     * Инициализация Resolver
     */
    private val resolver = Resolver(players)

    /**
     * Проверяет, что метод правильно считает количество игроков,
     * у которых поле `agency` равно null или пустое.
     */
    @Test
    fun `getCountWithoutAgency returns correct count`() {
        val count = resolver.getCountWithoutAgency()
        assertEquals(3, count)
    }

    /**
     * Проверяет, что метод возвращает защитника с наибольшим числом голов
     */
    @Test
    fun `getBestScorerDefender returns correct player`() {
        val bestDefender = resolver.getBestScorerDefender()
        assertEquals("Alex", bestDefender.first)
        assertEquals(10, bestDefender.second)
    }

    /**
     * Проверяет, что метод возвращает русское название позиции самого дорогого немецкого игрока
     */
    @Test
    fun `getTheExpensiveGermanPlayerPosition returns correct position`() {
        val position = resolver.getTheExpensiveGermanPlayerPosition()
        assertEquals("Нападающий", position)
    }

    /**
     * Проверяет, что метод возвращает команду с наибольшим средним числом красных карточек на одного игрока
     */
    @Test
    fun `getTheRudestTeam returns correct team`() {
        val rudestTeam = resolver.getTheRudestTeam()
        assertEquals("Team B", rudestTeam.name)
    }

    /**
     * Проверяет, что метод возвращает топ-10 команд по суммарной трансферной стоимости
     */
    @Test
    fun `getTop10TeamsByTransferCost returns correct ordering`() {
        val topTeams = resolver.getTop10TeamsByTransferCost()
        assertEquals(2, topTeams.size)
        assertEquals("Team B", topTeams[0].first.name)
        assertEquals("Team A", topTeams[1].first.name)
    }
}
