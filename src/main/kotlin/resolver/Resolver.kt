package resolver
import model.Player
import model.Team

class Resolver(val players: List<Player>): IResolver {
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isNullOrEmpty() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val defender = players
            .filter { it.position == "DEFENDER" }
            .maxByOrNull { it.goals }

        return defender?.let { it.name to it.goals } ?: ("Таких защитников нет" to 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val positionToRus = mapOf(
            "GOALKEEPER" to "Вратарь",
            "DEFENDER" to "Защитник",
            "MIDFIELD" to "Полузащитник",
            "FORWARD" to "Нападающий"
        )

        return players.filter { it.nationality == "Germany" }
            .maxByOrNull { it.transferCost }
            ?.position
            ?.let { positionToRus[it] }
            ?: "Немецкие игроки не найдены"
    }

    override fun getTheRudestTeam(): Team {
        val rudestTeamName = players.groupBy { it.team }
            .map { (teamName, teamPlayers) ->
                teamName to teamPlayers.sumOf { it.redCards } / teamPlayers.size.toDouble()
            }
            .maxByOrNull { it.second }
            ?.first ?: "Неизвестная команда"

        return Team(rudestTeamName)
    }
}