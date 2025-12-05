package resolver

import model.Player
import model.Team
import parser.CsvParser

class Resolver(filePath: String) : IResolver {

    private val players: List<Player> = CsvParser.parseCsv(filePath)

    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isBlank() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val defenders = players.filter { it.position.uppercase() == "DEFENDER" }
        val best = defenders.maxByOrNull { it.goals }
        return best?.let { it.name to it.goals } ?: "" to 0
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter { it.nationality.uppercase() == "GERMANY" || it.nationality.uppercase() == "GERMAN" }
        val mostExpensive = germanPlayers.maxByOrNull { it.transferCost }
        return mostExpensive?.position ?: ""
    }

    override fun getTheRudestTeam(): Team {
        val teams = players.groupBy { it.team }
        val rudestTeam = teams.maxByOrNull { entry ->
            val totalRedCards = entry.value.sumOf { it.redCards }
            val playerCount = entry.value.size
            if (playerCount == 0) 0.0 else totalRedCards.toDouble() / playerCount
        }
        val teamName = rudestTeam?.key ?: ""
        val teamPlayers = rudestTeam?.value ?: emptyList()
        return Team(teamName, teamPlayers)
    }

    override fun getTeamTotalCosts(): List<Pair<String, Long>> {
        return players.groupBy { it.team }
            .map { (teamName, teamPlayers) -> teamName to teamPlayers.sumOf { it.transferCost } }
            .sortedByDescending { it.second }
            .take(10)
    }

}
