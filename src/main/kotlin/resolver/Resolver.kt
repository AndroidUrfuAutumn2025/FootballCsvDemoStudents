package resolver

import model.Player
import model.Team
import constants.Nationalities
import model.enums.Position

class Resolver(private val players: List<Player>) : IResolver {
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isNullOrEmpty() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return players
            .filter { it.position == Position.DEFENDER }
            .maxByOrNull { it.goals }
            ?.let { Pair(it.name, it.goals) }
            ?: throw Exception("Среди игроков нет защитников.")
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        return players
            .filter { it.nationality == Nationalities.GERMANY }
            .maxByOrNull { it.transferCost }?.position?.displayName
            ?: throw Exception("Среди игроков нет немцев.")
    }

    override fun getTheRudestTeam(): Team {
        return players
            .groupBy { it.team }
            .maxByOrNull { it.value.map { p -> p.redCards }.average() }?.key
            ?: throw Exception("Нет ни одной команды.")
    }

}