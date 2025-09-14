package resolver

import model.Player
import model.PositionData
import model.enums.Position
import model.Team
import kotlin.math.roundToInt

class Resolver(private val players: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int = players.count { it.agency.isEmpty() }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return players
            .filter { it.position == Position.DEFENDER }
            .maxByOrNull { it.goalCount }
            ?.let { it.name to it.goalCount }
            ?: throw NoSuchElementException("Defenders not found")
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        return players
                .filter { it.nationality == "Germany" }
                .maxByOrNull { it.transferCost }
                ?.position
                ?.translate
                ?: throw NoSuchElementException("German player not found")
    }

    override fun getTheRudestTeam(): Team {
        return players
            .groupBy { it.team }
            .mapValues { (_, players) ->
                players.map { it.redCardCount }.average()
            }
            .maxByOrNull { it.value }
            ?.key
            ?: throw NoSuchElementException("No teams with players found")
    }

    override fun getDistributionByPositions(): List<PositionData> {
        return players
            .groupingBy { it.position }
            .eachCount()
            .map { (position, count) ->
                PositionData(
                    positionName = position.translate,
                    count = count,
                    percentage = (count.toDouble() / players.size * 100).roundToInt()
                )
            }
    }
}