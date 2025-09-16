package resolver

import model.Team
import model.Player
import model.Position

class Resolver(val allPlayers : List<Player>) : IResolver {
    override fun getCountWithoutAgency(): Int = allPlayers.count{ it.agency.isEmpty() }

    override fun getBestScorerDefender(): Pair<String, Int> = allPlayers
        .filter{ it.position == Position.DEFENDER }
        .maxBy{ it.goals }
        .let{ it.name to it.goals }

    override fun getTheExpensiveGermanPlayerPosition(): String = allPlayers
        .filter{ it.nationality == "Germany" }
        .maxBy{ it.transferCost }.position.toRussian()

    override fun getTheRudestTeam(): Team = allPlayers.groupBy { it.team.name }
        .maxBy { (_, players) -> players.map { it.redCards }.average() }
        .let { (teamName, _) -> Team(teamName) }

    fun getTopHighestTransferCostTeams(teamsCount : Int) : List<Pair<String, Double>> =
        allPlayers
            .groupBy{ it.team.name }
            .mapValues{ (_, players) -> players.sumOf{ it.transferCost } }
            .toList()
            .sortedByDescending { it.second }
            .take(teamsCount)
}
