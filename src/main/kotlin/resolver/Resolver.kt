package resolver

import model.Player
import model.Team

class Resolver(val playersList: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int {
        return playersList.count { it.agency == null }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val bestDefender = playersList
            .filter { it.position == "DEFENDER" }
            .maxByOrNull { it.goals }
        if (bestDefender == null) {
            return Pair("Нет данных", 0)
        }
        return Pair(bestDefender.name, bestDefender.goals)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayer = playersList
            .filter { it.nationality == "Germany" }
            .maxByOrNull { it.transferCost }
        return if (germanPlayer != null) {
            engToRusPosition(germanPlayer.position)
        } else {
            "Нет данных"
        }
    }

    override fun getTheRudestTeam(): Team {
        val teamWithMaxRedCardsAvg = playersList
            .groupBy { it.team }
            .maxByOrNull { entry ->
                val teamPlayers = entry.value
                if (teamPlayers.isEmpty()) 0.0
                else teamPlayers.sumOf { it.redCards }.toDouble() / teamPlayers.size
            }
        return if (teamWithMaxRedCardsAvg != null) {
            Team(teamWithMaxRedCardsAvg.key, "") // город не известен, можно оставить пустым
        } else {
            Team("None", "")
        }
    }

    private fun engToRusPosition(engPos: String): String {
        return when (engPos) {
            "DEFENDER" -> "ЗАЩИТНИК"
            "FORWARD" -> "НАПАДАЮЩИЙ"
            "MIDFIELD" -> "ПОЛУЗАЩИТНИК"
            "GOALKEEPER" -> "ВРАТАРЬ"
            else -> engPos
        }
    }
}