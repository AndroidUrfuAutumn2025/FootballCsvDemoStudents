package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>) : IResolver {
    override fun getCountWithoutAgency(): Int =
        players.count { it.agency.isNullOrBlank() }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val defenders = players.filter { it.position.uppercase() == "DEFENDER" }
        val best = defenders.maxByOrNull { it.goals }
        return best?.let { it.name to it.goals } ?: ("" to 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter { it.nationality.equals("Germany", ignoreCase = true) }
        val mostExpensive = germanPlayers.maxByOrNull { it.transferCost ?: 0L }
        return mostExpensive?.position?.let { positionToRussian(it) } ?: ""
    }

    override fun getTheRudestTeam(): Team {
        val grouped = players.groupBy { it.team to it.city }
        val teamAvgRed = grouped.mapValues { (_, list) ->
            list.sumOf { it.redCards }.toDouble() / list.size
        }
        val rudest = teamAvgRed.maxByOrNull { it.value }?.key
        return rudest?.let { Team(it.first, it.second) } ?: Team("", "")
    }

    private fun positionToRussian(position: String): String = when (position.uppercase()) {
        "DEFENDER" -> "Защитник"
        "FORWARD" -> "Нападающий"
        "MIDFIELD" -> "Полузащитник"
        "GOALKEEPER" -> "Вратарь"
        else -> position
    }
}