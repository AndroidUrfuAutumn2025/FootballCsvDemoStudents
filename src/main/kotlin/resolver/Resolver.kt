package resolver

import model.Player
import model.Team

class Resolver(private val players: ArrayList<Player>, private val teams: ArrayList<Team>) : IResolver {
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isEmpty() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return players
            .filter { it.position == "DEFENDER" }
            .maxByOrNull { it.goals }
            .let { (it?.name to it?.goals) as Pair<String, Int> }
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayer = players
            .filter { it.nationality == "Germany" }
            .maxByOrNull { it.transferCost }
        return when(germanPlayer?.position) {
            "GOALKEEPER" -> "вратарь"
            "DEFENDER" -> "защитник"
            "MIDFIELD" -> "полузащитник"
            "FORWARD" -> "нападающий"
            else -> ""
        }
    }

    override fun getTheRudestTeam(): Team {
        var maxAverageRedCards = 0.0
        var rudestTeam = teams.first()
        teams.forEach { team ->
            val members = team.members;
            val redCardsNumber = members.count { it.redCards > 0}
            val averageRedCards = redCardsNumber / (members.count() * 1.0)
            if(maxAverageRedCards < averageRedCards) {
                maxAverageRedCards = averageRedCards
                rudestTeam = team
            }
        }
        return rudestTeam
    }
}
