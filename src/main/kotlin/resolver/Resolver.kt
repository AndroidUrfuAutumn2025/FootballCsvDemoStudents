package resolver

import model.Player
import model.Team
import parser.CsvParser

class Resolver(private val players: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int =
        players.count { it.agency.isNullOrBlank() }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val defender = players
            .filter { it.position.equals("DEFENDER", ignoreCase = true) }
            .maxByOrNull { it.goals } ?: throw NoSuchElementException("No defenders found")
        return defender.name to defender.goals
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val german = players
            .filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost } ?: throw NoSuchElementException("No German players found")
        return translatePositionToRussian(german.position)
    }

    private fun translatePositionToRussian(position: String): String =
        when (position.uppercase()) {
            "GOALKEEPER" -> "Вратарь"
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "FORWARD" -> "Нападающий"
            else -> position
        }

    override fun getTheRudestTeam(): Team {
        val teamAvgRed = players.groupBy { it.team }
            .mapValues { (_, pl) -> pl.sumOf { it.redCards }.toDouble() / pl.size }
        return teamAvgRed.maxByOrNull { it.value }?.key ?: throw NoSuchElementException("No teams found")
    }

    override fun getTop10TeamsByTransferValue(): Map<String, Double> {
        val teamValues = players.groupBy { it.team.name }
            .mapValues { (_, pl) -> pl.sumOf { it.transferCost } }
        return teamValues.entries
            .sortedByDescending { it.value }
            .take(10)
            .associate { it.key to it.value }
    }

    companion object {
        fun createFromFile(filename: String): Resolver =
            Resolver(CsvParser.parsePlayersFromFile(filename))
    }
}
