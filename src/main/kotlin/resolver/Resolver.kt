package resolver

import model.Team
import model.Player
import parser.CsvParser

class Resolver (private val players: List<Player>): IResolver {

    override fun getCountWithoutAgency(): Int =
        players.count { it.agency.isNullOrBlank() }

    override fun getBestScorerDefender() : Pair<String, Int>{
        val defender = players.filter { it.position.equals("DEFENDER", ignoreCase=true) }
            .maxByOrNull { it.goals } ?: throw NoSuchElementException("No defenders found")
        return defender.name to defender.goals
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val german = players.filter { it.nationality.equals("Germany", ignoreCase=true) }
            .maxByOrNull { it.transferCost } ?: throw NoSuchElementException("No German players found")
        return translatePositionToRussian(german.position)
    }

    private fun translatePositionToRussian(position: String): String =
        when(position.uppercase()) {
            "GOALKEEPER" -> "Вратарь"
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "FORWARD" -> "Нападающий"
            else -> position
        }

    override fun getTheRudestTeam(): Team {
        val rudestTeam = players.groupBy { it.team }.mapValues { (_, p1) -> p1.sumOf { it.redCards }.toDouble() / p1.size}
        return rudestTeam.maxByOrNull { it.value }?.key ?: throw NoSuchElementException("No teams found")
    }

    override fun playersFromDifferentCountries(): Map<String, Double> {
        val total = players.size.toDouble()
        return players.groupBy { it.nationality }.mapValues { (_, group) -> group.size / total * 100}
            .entries.sortedByDescending { it.value }.associate { it.key to it.value }
    }

    companion object {
        fun createFromFile(filename: String): Resolver =
            Resolver(CsvParser.parsePlayer(filename))
    }
}