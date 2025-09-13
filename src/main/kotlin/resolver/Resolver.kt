package resolver

import model.Player
import model.Team
import parser.CsvParser

class Resolver : IResolver {
    private val players: List<Player> = CsvParser.parsePlayers()

    // Функция для перевода позиций на русский
    private fun translatePosition(position: String): String {
        return when (position.uppercase()) {
            "FORWARD" -> "Нападающий"
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "GOALKEEPER" -> "Вратарь"
            else -> position // На случай, если встретится неизвестная позиция
        }
    }

    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency == null }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val defender = players
            .filter { it.position == "DEFENDER" }
            .maxByOrNull { it.goals }

        return defender?.let { Pair(it.name, it.goals) } ?: Pair("", 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayer = players
            .filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost }

        // Используем функцию перевода
        return germanPlayer?.let { translatePosition(it.position) } ?: ""
    }

    override fun getTheRudestTeam(): Team {
        val teamRedCards = players
            .groupBy { it.team }
            .mapValues { (_, players) ->
                players.sumOf { it.redCards }.toDouble() / players.size
            }

        return teamRedCards.maxByOrNull { it.value }?.key ?: Team("", "")
    }

    override fun getTopTeamsByTransferCost(limit: Int): List<Pair<Team, Double>> {
        return players
            .groupBy { it.team }
            .map { (team, players) -> team to players.sumOf { it.transferCost } }
            .sortedByDescending { (_, totalCost) -> totalCost }
            .take(limit)
    }
}