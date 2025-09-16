package resolver

import model.Player
import model.Position
import model.Team

class Resolver(private val players: List<Player>) : IResolver {
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isNullOrBlank() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return players
            .filter { it.position == Position.Defender }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: throw NoSuchElementException("Защитники с голами не найдены!")
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        return players
            .filter { it.nationality.equals("GERMANY", ignoreCase = true) }
            .maxByOrNull { it.transferCost }?.russianPosition
            ?: throw NoSuchElementException("Самый дорогой немецкий игрок не найден!")
    }

    override fun getTheRudestTeam(): Team {
        return players
            .groupBy { it.team }
            .mapValues { (team, players) ->
                players.sumOf { it.redCards }.toDouble() / players.size
            }
            .maxByOrNull { it.value }?.key
            ?: throw NoSuchElementException("Команд с красными карточками не найдено!")
    }

    override fun getForwardsGoalToCost(): List<Pair<Int, Long>> {
        return players
            .filter { it.position == Position.Forward }
            .map { it.goals to it.transferCost }
            .sortedBy { it.first }
    }
}