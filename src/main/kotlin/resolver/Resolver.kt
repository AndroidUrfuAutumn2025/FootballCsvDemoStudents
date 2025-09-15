package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>) : IResolver{
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isBlank() || it.agency == "N/A" || it.agency == "-" }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val defenders = players.filter { it.position.equals("DEFENDER", ignoreCase = true) }
        if (defenders.isEmpty()) return Pair("", 0)

        val bestDefender = defenders.maxByOrNull { it.goals } ?: return Pair("", 0)
        return Pair(bestDefender.name, bestDefender.goals)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter { it.nationality.equals("Germany", ignoreCase = true) }
        if (germanPlayers.isEmpty()) return "Нет немецких игроков"

        val mostExpensive = germanPlayers.maxByOrNull { it.transferCost } ?: return "Нет немецких игроков"

        return when (mostExpensive.position.uppercase()) {
            "GOALKEEPER" -> "Вратарь"
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "FORWARD" -> "Нападающий"
            "STRIKER" -> "Нападающий"
            else -> mostExpensive.position
        }
    }

    override fun getTheRudestTeam(): Team {
        val teamsWithPlayers = players.groupBy { it.team }

        val teamRedCardsAverage = teamsWithPlayers.mapValues { (_, players) ->
            players.sumOf { it.redCards }.toDouble() / players.size
        }

        val rudestTeam = teamRedCardsAverage.maxByOrNull { it.value }?.key
            ?: throw IllegalStateException("Нет данных о командах")

        return rudestTeam
    }

    override fun getTop10TeamsByTransferValue(): List<Pair<Team, Long>> {
        val teamTransferValues = players.groupBy { it.team }
            .map { (team, players) ->
                team to players.sumOf { it.transferCost }
            }
            .sortedByDescending { it.second }

        return teamTransferValues.take(10)
    }
}