package resolver

import model.Player
import model.Team

/**
 * Реализуйте интерфейса IResolver, который решает поставленные задачи
 */
class Resolver(private val players: List<Player>) : IResolver{

    /**
     * Вывод количества игроков, интересы которых не представляет агентство.
     */
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isNullOrBlank() }
    }

    /**
     * Вывод автора наибольшего числа голов из числа защитников и их количество.
     */
    override fun getBestScorerDefender(): Pair<String, Int> {
        return players
            .filter { it.position.equals("DEFENDER", ignoreCase = true) }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: ("Защитники не найдены" to 0)
    }

    /**
     * Вывод русского названия позиции самого дорогого немецкого игрока
     */
    override fun getTheExpensiveGermanPlayerPosition(): String {
        val mostExpensive = players
            .filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost }
            ?: return "Немецкие игроки не найдены"

        val positionMap = mapOf(
            "GOALKEEPER" to "Вратарь",
            "DEFENDER" to "Защитник",
            "MIDFIELD" to "Полузащитник",
            "FORWARD" to "Нападающий"
        )

        return positionMap[mostExpensive.position.uppercase()] ?: mostExpensive.position
    }

    /**
     * Вывод команды с наибольшим средним числом красных карточек на одного игрока
     */
    override fun getTheRudestTeam(): Team {
        val teamMap = players.groupBy { it.team.name to it.team.city }

        val teamsWithAverages = teamMap.map { (key, teamPlayers) ->
            val team = Team(name = key.first, city = key.second, players = teamPlayers)
            team to team.averageRedCards
        }

        return teamsWithAverages.maxByOrNull { it.second }?.first
            ?: throw NoSuchElementException("Не найдено команд с красными карточками")
    }

    /**
     * Топ-10 команд с наибольшей суммарной трансферной стоимостью
     */
    fun getTop10TeamsByTransferCost(): List<Pair<Team, Long>> {
        return players.groupBy { it.team }
            .mapValues { (_, teamPlayers) -> teamPlayers.sumOf { it.transferCost } }
            .toList()
            .sortedByDescending { it.second }
            .take(10)
    }
}