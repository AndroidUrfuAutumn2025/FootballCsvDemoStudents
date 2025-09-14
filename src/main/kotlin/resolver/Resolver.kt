package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int =
        players.count { it.agency.isNullOrBlank() }

    override fun getBestScorerDefender(): Pair<String, Int> =
        players.filter { it.position.uppercase() == "DEFENDER" }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: ("" to 0)

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val mostExpensive = players
            .filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost }

        val engToRu = mapOf(
            "DEFENDER" to "Защитник",
            "FORWARD" to "Нападающий",
            "MIDFIELD" to "Полузащитник",
            "GOALKEEPER" to "Вратарь"
        )

        return engToRu[mostExpensive?.position?.uppercase()] ?: "Неизвестно"
    }

    override fun getTheRudestTeam(): Team {
        return players.groupBy { it.teamName to it.city }
            .mapValues { (_, teamPlayers) ->
                teamPlayers.sumOf { it.redCards }.toDouble() / teamPlayers.size
            }
            .maxByOrNull { it.value }?.key
            ?.let { Team(it.first, it.second) }
            ?: Team("Unknown", "Unknown")
    }
}
