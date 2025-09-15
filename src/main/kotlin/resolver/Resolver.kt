package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>) : IResolver {
    private val positionTranslation = mapOf(
        "GOALKEEPER" to "вратарь",
        "DEFENDER" to "защитник",
        "MIDFIELD" to "полузащитник",
        "FORWARD" to "нападающий",
    )

    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isBlank() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val defenders = players.filter { it.position == "DEFENDER" }
        val best = defenders.maxByOrNull { it.goals }
        return best?.let { it.name to it.goals } ?: ("Нет защитников" to 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germans = players.filter {
            it.nationality == ("Germany")
        }
        val expensive = germans.maxByOrNull { it.transferCost }
        val position = expensive?.position ?: return "Не найден"

        return positionTranslation[position] ?: expensive.position
    }

    override fun getTheRudestTeam(): Team {
        val grouped = players.groupBy { it.team to it.city }

        val rudest = grouped.maxByOrNull { (_, teamPlayers) ->
            teamPlayers.sumOf { it.redCards }.toDouble() / teamPlayers.size
        }

        return Team(
            name = rudest?.key?.first ?: "Нет данных",
            city = rudest?.key?.second ?: "",
            players = rudest?.value?.toMutableList() ?: mutableListOf()
        )
    }
}
