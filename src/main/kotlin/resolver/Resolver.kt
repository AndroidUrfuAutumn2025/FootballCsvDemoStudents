package resolver

import model.Player
import model.Position
import model.Team

class Resolver(private val players: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int = players.count { it.agency == null }

    override fun getBestScorerDefender(): Pair<String, Int> = players
        .asSequence()
        .filter { it.position == Position.DEFENDER }
        .maxByOrNull { it.goals }
        ?.let { it.name to it.goals }
        ?: ("" to 0)

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val pos = players
            .asSequence()
            .filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost }
            ?.position
            ?: return ""

        return when (pos) {
            Position.GOALKEEPER -> "вратарь"
            Position.DEFENDER -> "защитник"
            Position.MIDFIELD -> "полузащитник"
            Position.FORWARD -> "нападающий"
        }
    }

    override fun getTheRudestTeam(): Team = players
        .groupBy { it.team }
        .mapValues { (_, teamPlayers) ->
            val totalReds = teamPlayers.sumOf { it.redCards }
            totalReds.toDouble() / teamPlayers.size
        }
        .maxByOrNull { it.value }
        ?.key
        ?: Team("", "")

    override fun getPositionsShare(): Map<Position, Double> {
        val total = players.size.takeIf { it > 0 } ?: return emptyMap()
        return players
            .groupBy { it.position }
            .mapValues { (_, list) -> list.size.toDouble() / total }
    }
}