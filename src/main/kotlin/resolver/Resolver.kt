package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isNullOrEmpty() || it.agency == "N/A" }
    }

    override fun getBestScoreDefender(): Pair<String, Int> {
        val defender = players
            .filter { it.position.equals("Defender", ignoreCase = true) }
            .maxByOrNull { it.goals }

        return defender?.let { it.name to it.goals } ?: ("No defender found" to 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter { player ->
            player.nationality.equals("germany", ignoreCase = true)
        }

        if (germanPlayers.isEmpty()) {
            println("Debug: No German players found. Available nationalities: ${players.map { it.nationality }.distinct().take(10)}")
        }

        return germanPlayers
            .maxByOrNull { it.transferValue }
            ?.position
            ?.let { translatePosition(it) }
            ?: "No German players found"
    }

    override fun getTheRudestTeam(): Team {
        val teamRedCards = players.groupBy { it.team }
            .mapValues { (_, teamPlayers) ->
                teamPlayers.sumOf { it.redCards }.toDouble() / teamPlayers.size
            }

        return teamRedCards.maxByOrNull { it.value }?.key ?: Team("No teams", "found")
    }

    fun getPositionDistribution(): Map<String, Double> {
        val totalPlayers = players.size.toDouble()
        return players.groupBy { it.position }
            .mapValues { (_, positionPlayers) ->
                (positionPlayers.size / totalPlayers) * 100
            }
    }

    private fun translatePosition(englishPosition: String): String {
        return when (englishPosition.lowercase()) {
            "defender" -> "Defender"
            "forward" -> "Forward"
            "midfielder" -> "Midfielder"
            "goalkeeper" -> "Goalkeeper"
            else -> englishPosition
        }
    }
}
