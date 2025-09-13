package resolver

import model.Player
import model.Team

/**
 * Решает задачи анализа данных об игроках
 */
class Resolver(private val players: List<Player>) : IResolver {

    private val teams: Map<String, Team> by lazy {
        players.groupBy { it.team }
            .mapValues { (teamName, teamPlayers) ->
                val firstPlayer = teamPlayers.first()
                Team(teamName, firstPlayer.city, teamPlayers)
            }
    }

    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isNullOrBlank() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return players.filter { it.position.equals("DEFENDER", ignoreCase = true) }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: Pair("Защитников не найдено", 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter {
            it.nationality.equals("Germany", ignoreCase = true)
        }

        if (germanPlayers.isEmpty()) return "Немецкие игроки не найдены"

        val mostExpensive = germanPlayers.maxByOrNull { it.transferCost }
            ?: return "Ошибка при поиске игрока"

        return when (mostExpensive.position.uppercase()) {
            "GOALKEEPER" -> "Вратарь"
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "FORWARD" -> "Нападающий"
            else -> mostExpensive.position
        }
    }

    override fun getTheRudestTeam(): Team {
        return teams.values.maxByOrNull { it.averageRedCards }
            ?: throw IllegalStateException("Команды не найдены")
    }

    /**
     * Получает распределение игроков по национальностям
     * @return Map где ключ - национальность, значение - количество игроков
     */
    fun getNationalityDistribution(): Map<String, Int> {
        return players.groupingBy { it.nationality }
            .eachCount()
            .toList()
            .sortedByDescending { it.second }
            .toMap()
    }
}