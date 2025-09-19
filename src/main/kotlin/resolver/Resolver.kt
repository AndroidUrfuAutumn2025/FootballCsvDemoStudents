package resolver

import model.Person
import model.Position
import model.Team

class Resolver(val players: List<Person>, val teams: List<Team>) : IResolver {

    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isEmpty() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return players
            .filter { it.position.toString() == "DEFENDER" }
            .maxByOrNull { it.goals ?: 0 }
            ?.let { it.name to (it.goals ?: 0) }
            ?: ("" to 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        return players
            .filter { it.nationality == "Germany" }
            .maxByOrNull { it.transferCost ?: 0 }
            ?.position
            ?.translate
            ?: "No data"
    }


    override fun getTheRudestTeam(): Team {
        require(teams.isNotEmpty()) { "teams must not be empty" }

        return teams
            .maxByOrNull { team ->
                val reds = team.players.mapNotNull { it.redCards }
                if (reds.isEmpty()) 0.0 else reds.average()
            }!!
    }
}
