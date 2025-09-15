package resolver

import model.Player
import model.Team

object PositionMapper {
    fun toRussian(position: String): String = when (position.uppercase()) {
        "GOALKEEPER" -> "Вратарь"
        "DEFENDER" -> "Защитник"
        "MIDFIELD" -> "Полузащитник"
        "FORWARD" -> "Нападающий"
        else -> position
    }
}

class Resolver(private val players: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isNullOrBlank() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val defenders = players.filter { it.position.uppercase() == "DEFENDER" }
        val best = defenders.maxByOrNull { it.goals }
            ?: throw NoSuchElementException("No defenders found")
        return best.name to best.goals
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germans = players.filter { it.nationality.uppercase() == "GERMANY" || it.nationality.uppercase() == "DEUTSCHLAND" }
        val expensive = germans.maxByOrNull { it.transferCost }
            ?: throw NoSuchElementException("No German players found")
        return PositionMapper.toRussian(expensive.position)
    }

    override fun getTheRudestTeam(): Team {
        return players
            .groupBy { it.team }
            .maxByOrNull { (_, teamPlayers) ->
                teamPlayers.sumOf { it.redCards }.toDouble() / teamPlayers.size
            }?.key ?: throw NoSuchElementException("No teams found")
    }

    fun getPositionDistribution(): Map<String, Double> {
        val totalPlayers = players.size.toDouble()
        return players.groupBy { it.position.uppercase() }
            .mapValues { (_, list) -> list.size / totalPlayers * 100 }
            .mapKeys { PositionMapper.toRussian(it.key) }
    }
}

