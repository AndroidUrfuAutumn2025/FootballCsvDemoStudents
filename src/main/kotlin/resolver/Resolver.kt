package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>, private val teams: List<Team>) : IResolver {

    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isNullOrBlank() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return players
            .filter { it.position.equals("DEFENDER", ignoreCase = true) }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: throw NoSuchElementException("No defenders found")
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter {
            it.nationality.equals("GERMANY", ignoreCase = true)
        }

        if (germanPlayers.isEmpty()) {
            throw NoSuchElementException("No German players found")
        }

        val mostExpensive = germanPlayers.maxByOrNull { it.transferCost }!!

        return when (mostExpensive.position.uppercase()) {
            "GOALKEEPER" -> "Вратарь"
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "FORWARD" -> "Нападающий"
            else -> mostExpensive.position
        }
    }

    override fun getTheRudestTeam(): Team {
        return teams.maxByOrNull { team ->
            val totalRedCards = team.players.sumOf { it.redCards }
            val playerCount = team.players.size
            if (playerCount > 0) totalRedCards.toDouble() / playerCount else 0.0
        } ?: throw NoSuchElementException("No teams found")
    }
}