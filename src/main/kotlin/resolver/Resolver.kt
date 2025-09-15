package resolver
import model.Player
import model.Team
import model.Position
class Resolver(private val players: List<Player>) : IResolver {
    override fun getCountWithoutAgency(): Int {
        return players.count() {
            it.agency == null
        }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val bestDefender = players
            .filter { it.position == Position.DEFENDER }
            .maxByOrNull { it.goals ?: 0 }

        return bestDefender?.let { it.name to (it.goals ?: 0) } ?: ("" to 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val mostExpensivePlayer = players
            .filter { it.nationality == "Germany" }
            .maxByOrNull { it.transferValue ?: 0 }

        return mostExpensivePlayer?.position?.russianName ?: "No information about position"
    }

    override fun getTheRudestTeam(): Team {
        val teams = players.groupBy { it.team }
        return teams.maxByOrNull { (_, playersInTeam) -> playersInTeam.map {it.redCards ?: 0}.average() }?.key ?: throw Exception("No teams available")
    }
}