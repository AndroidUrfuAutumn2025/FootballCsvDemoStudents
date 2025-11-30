package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>) : IResolver {

    // 1. Выведите количество игроков, интересы которых не представляет агентство.
    override fun getCountWithoutAgency(): Int {
        return players.count {it.agency.isNullOrEmpty() }
    }

    // 2. Выведите автора наибольшего числа голов из числа защитников и их количество.
    override fun getBestScorerDefender(): Pair<String, Int>? {
        val bestDefender = players
            .filter { it.position.equals("DEFENDER", ignoreCase = true) }
            .maxByOrNull { it.goals }

        return bestDefender?.let { it.name to it.goals }
    }

    // 3. Выведите русское название позиции самого дорогого немецкого игрока.
    override fun getTheExpensiveGermanPlayerPosition(): String? {
        val expensiveGerman = players
            .filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost }

        return expensiveGerman?.position?.let { translatePosition(it) }
    }

    // 4. Выберите команду с наибольшим средним числом красных карточек на одного игрока.
    override fun getTheRudestTeam(): Team? {
        val teamsMap = players.groupBy { it.teamName }
        val teams = teamsMap.map { (teamName, teamPlayers) ->
            Team(name = teamName, players = teamPlayers.toMutableList())
        }
        return teams.maxByOrNull { team -> team.getAverageRedCards() }
    }

    // Вспомогательный метод перевода
    private fun translatePosition(position: String): String {
        return when (position) {
            "GOALKEEPER" -> "Вратарь"
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "FORWARD" -> "Нападающий"
            else -> position
        }
    }
}