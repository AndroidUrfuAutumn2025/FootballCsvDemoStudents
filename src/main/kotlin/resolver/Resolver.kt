package resolver

import model.Player
import model.Position
import model.Team
import parser.CsvParser

class Resolver : IResolver {
    private val players: List<Player> = CsvParser.parse()

    override fun getCountWithoutAgency(): Int = players.count { it.agency == null }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val defender: Player? = players
            .filter { it.position == Position.DEFENDER }
            .maxByOrNull { it.goals }

        return defender?.let {
            Pair(first = it.name, second = it.goals)
        } ?: Pair(first = "There is no such player", second = -1)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayer: Player? = players
            .filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost }

        return germanPlayer?.position?.ruText ?: "There is no such player"
    }

    override fun getTheRudestTeam(): Team {
        val teamRedCards: Map<Team, Double> = players
            .groupBy { it.team }
            .mapValues { (_, players) ->
                players.sumOf { it.redCards }.toDouble() / players.size
            }

        return teamRedCards.maxByOrNull {
            it.value
        }?.key ?: Team("There is no such team", "")
    }

    fun getNationalityShare(): Map<String, Double> {
        val total: Double = players.size.toDouble()
        return players
            .groupBy { it.nationality }
            .mapValues { (_, players) -> players.size / total }
    }
}