package resolver
import model.Person
import model.Team
import resolver.IResolver
import kotlin.math.max

class Resolver(val players: List<Person>, val teams: List<Team>): IResolver{
    override fun getCountWithoutAgency(): Int {
        return players.count {it.agency.isEmpty()}
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return players.filter { it.position == "DEFENDER" }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: ("" to 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val positionToRus: Map<String, String> = mapOf(
            "GOALKEEPER" to "Вратарь",
            "DEFENDER" to "Защитник",
            "MIDFIELD" to "Полузащитник",
            "FORWARD" to "Нападающий")
        return players.filter { it.nationality == "Germany" }
            .maxByOrNull { it.transfer_cost }
            ?.position
            ?.let {positionToRus[it]}
            ?: "No data"
    }

    override fun getTheRudestTeam(): Team {
        var average: Double = 0.0
        var team: Team = teams[0]
        teams.forEach { it ->
            var red_cards_by_team: Int = 0
            it.players.forEach {
                red_cards_by_team += it.red_cards
            }
            if(red_cards_by_team / it.players.size > average) {
                average = (red_cards_by_team / it.players.size).toDouble()
                team = it
            }
        }
        return team
    }

}