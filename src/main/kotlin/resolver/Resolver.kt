package resolver

import model.Player
import model.Position
import model.Team

class Resolver(val players: List<Player>, val teams: List<Team>) : IResolver {
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isNullOrBlank() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return players.filter { it.position == Position.DEFENDER }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: ("Не найдено" to 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        return players.filter { it.nationality.equals("germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost }
            ?.let { it.position?.title }
            ?: "Не найдено"
    }

    override fun getTheRudestTeam(): Team {
        return teams.maxByOrNull { it.averageRedCards } ?: Team("Неизвестно", "")
    }
}