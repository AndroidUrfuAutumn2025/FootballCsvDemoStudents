package resolver

import model.Player
import model.Position
import model.Team

class Resolver (private val players: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int = players.count { it.agency.isEmpty() }

    override fun getBestScorerDefender(): Pair<String, Int> =
        players
            .filter { it.position == Position.DEFENDER }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: throw Exception("no defenders")

    override fun getTheExpensiveGermanPlayerPosition(): String =
        players
            .filter { it.nationality == "Germany" }
            .maxBy { it.transferCost }
            .let { it.position.russianName }

    override fun getTheRudestTeam(): Team =
        players
            .groupBy { it.team }
            .maxBy { it.value.map { it.redCards }.average() }
            .key

}
