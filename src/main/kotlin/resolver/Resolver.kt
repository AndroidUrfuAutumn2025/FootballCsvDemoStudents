package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>) : IResolver {

    // 1 Количество игроков без агентства
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency == null }
    }

    // 2 Лучший бомбардир среди защитников
    override fun getBestScorerDefender(): Pair<String, Int> {
        val defenders = players.filter { it.position == "DEFENDER" }
        if (defenders.isEmpty()) return Pair("Нет защитников", 0)

        val best = defenders.maxByOrNull { it.goals }!!
        return Pair(best.name, best.goals)
    }

    // 3 Позиция самого дорогого немецкого игрока
    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germans = players.filter { it.nationality == "Germany" }
        if (germans.isEmpty()) return "Нет немецких игроков"

        val mostExpensive = germans.maxByOrNull { it.transferCost }!!
        return translatePosition(mostExpensive.position)
    }

    // 4 Команда с наибольшим средним числом красных карточек
    override fun getTheRudestTeam(): Team {
        // Группируем игроков по командам
        val teamsMap = players.groupBy { it.team }

        // Считаем среднее количество красных карточек для каждой команды
        val teamAverages = teamsMap.map { (teamName, teamPlayers) ->
            val totalRedCards = teamPlayers.sumOf { it.redCards }
            val average = totalRedCards.toDouble() / teamPlayers.size
            teamName to average
        }

        // Находим команду с максимальным средним
        val rudestTeamName = teamAverages.maxByOrNull { it.second }!!.first
        val teamPlayers = teamsMap[rudestTeamName] ?: emptyList()

        return Team(rudestTeamName, teamPlayers)
    }

    // Функция для перевода позиций на русский
    private fun translatePosition(englishPosition: String): String {
        return when (englishPosition) {
            "GOALKEEPER" -> "Вратарь"
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "FORWARD" -> "Нападающий"
            else -> englishPosition
        }
    }
}