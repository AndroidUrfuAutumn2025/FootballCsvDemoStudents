package resolver

import model.Player
import model.Team

class Resolver : IResolver {
    override fun getCountWithoutAgency(players: List<Player>): Int =
        players.count { it.agency.isNullOrBlank() }

    override fun getBestScorerDefender(players: List<Player>): Pair<String, Int>? =
        players.filter { it.position.equals("DEFENDER", ignoreCase = true) }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }

    // Пример соответствия позиции на русском
    private val positionRu = mapOf(
        "GOALKEEPER" to "Вратарь",
        "DEFENDER" to "Защитник",
        "MIDFIELD" to "Полузащитник",
        "FORWARD" to "Нападающий"
    )

    override fun getTheExpensiveGermanPlayerPosition(players: List<Player>): String? =
        players.filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost }
            ?.position
            ?.let { positionRu[it.uppercase()] }

    override fun getTheRudestTeam(teams: List<Team>): String? =
        teams.maxByOrNull { team ->
            if (team.players.isNotEmpty())
                team.players.sumOf { it.redCards }.toDouble() / team.players.size
            else 0.0
        }?.name
}