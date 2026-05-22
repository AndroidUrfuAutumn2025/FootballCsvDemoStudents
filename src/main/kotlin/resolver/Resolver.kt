package resolver

import model.Player
import model.Team

class Resolver (private val players: List<Player>): IResolver {

    override fun getCountWithoutAgency(): Int {
        var count = 0
        for (player in players) {
            if (player.agency == null)
                count++
        }

        return count
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        var bestDefender: Player? = null
        var maxGoals = -1

        for (player in players) {
            if (player.position == "DEFENDER" && player.goals > maxGoals) {
                maxGoals = player.goals
                bestDefender = player
            }
        }

        return Pair(bestDefender!!.name, maxGoals)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        var mostExpensive: Player? = null
        var maxCost = -1.0

        for (player in players) {
            if (player.nationality == "Germany" && player.transferCost > maxCost) {
                maxCost = player.transferCost
                mostExpensive = player
            }
        }

        return translatePosition(mostExpensive!!.position)
    }

    override fun getTheRudestTeam(): Team {
        val teamRedCards = mutableMapOf<Team, Int>()
        val teamPlayerCount = mutableMapOf<Team, Int>()

        for (player in players) {
            val team = player.team

            val currentRedCards = teamRedCards.getOrDefault(team, 0)
            teamRedCards[team] = currentRedCards + player.redCards

            val currentCount = teamPlayerCount.getOrDefault(team, 0)
            teamPlayerCount[team] = currentCount + 1
        }

        var rudestTeam: Team? = null
        var maxAverage = -1.0

        for ((team, redCards) in teamRedCards) {
            val playerCount = teamPlayerCount[team]!!
            val average = redCards.toDouble() / playerCount

            if (average > maxAverage) {
                maxAverage = average
                rudestTeam = team
            }
        }

        return rudestTeam!!
    }

    private fun translatePosition(position: String): String =
        when(position) {
            "GOALKEEPER" -> "Вратарь"
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "FORWARD" -> "Нападающий"
            else -> position
        }

    fun getTop10TeamsByCost(): List<Pair<Team, Double>> {
        val teamCosts = mutableMapOf<Team, Double>()

        for (player in players) {
            val currentCost = teamCosts.getOrDefault(player.team, 0.0)
            teamCosts[player.team] = currentCost + player.transferCost
        }

        val sorted = teamCosts.entries.sortedByDescending { it.value }

        val top10 = mutableListOf<Pair<Team, Double>>()
        for (i in 0 until minOf(10, sorted.size)) {
            top10.add(Pair(sorted[i].key, sorted[i].value))
        }

        return top10
    }
}