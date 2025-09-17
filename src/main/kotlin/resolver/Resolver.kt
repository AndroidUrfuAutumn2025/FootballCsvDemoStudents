package resolver
import model.Player
import model.Team

class Resolver(val players: List<Player>): IResolver {
    override fun getCountWithoutAgency(): Int {
        return players.count {it.agency.isNullOrEmpty() }
    }
    override fun getBestScorerDefender(): Pair<String, Int> {
        val defender = players
            .filter {it.position == "DEFENDER"}
            .maxByOrNull {it.goals}

        return defender?.let {it.name to it.goals} ?: ("Таких защитников нет" to 0)
    }
    override fun getTheExpensiveGermanPlayerPosition(): String {
        val positionToRus: Map<String, String> = mapOf(
            "GOALKEEPER" to "Вратарь",
            "DEFENDER" to "Защитник",
            "MIDFIELD" to "Полузащитник",
            "FORWARD" to "Нападающий")

        return players.filter { it.nationality == "Germany" }
            .maxByOrNull { it.transferCost }
            ?.position
            ?.let {positionToRus[it]}
            ?: "Немецкие игроки не найдены"
    }
    override fun getTheRudestTeam(): Team {
        val teams = players.groupBy {it.team}
            .map{(name, players) -> Team(name, players.toMutableList())}

        return teams.maxBy {team -> team.players.map {it.redCards}.average()}
    }


}