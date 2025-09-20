package resolver

import model.Player
import model.Team

interface IResolver {

    fun countPlayersWithoutAgency(players: List<Player>): Int

    fun getTopScoringDefender(players: List<Player>): Pair<String, Int>?

    fun getPositionOfMostExpensiveGermanPlayer(players: List<Player>): String?

    fun getTeamWithHighestAverageRedCards(players: List<Player>): Team?

    fun getPlayerDistributionByNationality(players: List<Player>): Map<String, Double>
}

class PlayerStatsResolver : IResolver {

    override fun countPlayersWithoutAgency(players: List<Player>): Int {
        return players.count { it.agency == null }
    }

    override fun getTopScoringDefender(players: List<Player>): Pair<String, Int>? {
        return players
            .filter { it.position.equals("DEFENDER", ignoreCase = true) && it.goals > 0 }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
    }

    override fun getPositionOfMostExpensiveGermanPlayer(players: List<Player>): String? {
        val mostExpensiveGerman = players
            .filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferValue }

        return mostExpensiveGerman?.position?.let {
            when (it.uppercase()) {
                "GOALKEEPER" -> "Вратарь"
                "DEFENDER" -> "Защитник"
                "MIDFIELD" -> "Полузащитник"
                "FORWARD" -> "Нападающий"
                else -> it
            }
        }
    }

    override fun getTeamWithHighestAverageRedCards(players: List<Player>): Team? {
        if (players.isEmpty()) return null

        val playersByTeam = players.groupBy { it.team }

        val teamAverageRedCards = playersByTeam.mapValues { (_, teamPlayers) ->
            if (teamPlayers.isEmpty()) 0.0 else teamPlayers.sumOf { it.redCards }.toDouble() / teamPlayers.size
        }

        return teamAverageRedCards
            .filterValues { it > 0 }
            .maxByOrNull { it.value }
            ?.key
    }

    override fun getPlayerDistributionByNationality(players: List<Player>): Map<String, Double> {
        if (players.isEmpty()) return emptyMap()

        val playersByNationality = players.groupBy { it.nationality }
        val totalPlayers = players.size.toDouble()

        return playersByNationality.mapValues { (_, countryPlayers) ->
            (countryPlayers.size / totalPlayers) * 100.0
        }.toList().sortedByDescending { (_, value) -> value }.toMap()
    }
}