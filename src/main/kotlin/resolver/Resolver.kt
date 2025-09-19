package resolver
import model.Player
import model.Team

class Resolver(private val players: List<Player>) : IResolver {

    override fun getCountWithoutAgency(): Int {
        var count = 0
        for (player in players) {
            if (player.agency.isBlank()) {
                count++
            }
        }
        return count
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        var bestName = "Нет защитников"
        var bestGoals = 0

        for (player in players) {
            if (player.position.contains("DEFENDER", true) && player.goals > bestGoals) {
                bestName = player.name
                bestGoals = player.goals
            }
        }

        return Pair(bestName, bestGoals)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        var maxPrice = 0L
        var position = "Немецких игроков нет"

        for (player in players) {
            if (player.nationality.equals("Germany", true) && player.transferCost > maxPrice) {
                maxPrice = player.transferCost
                position = when (player.position.uppercase()) {
                    "GOALKEEPER" -> "Вратарь"
                    "DEFENDER" -> "Защитник"
                    "MIDFIELD" -> "Полузащитник"
                    "FORWARD", "ATTACKER" -> "Нападающий"
                    else -> player.position
                }
            }
        }

        return position
    }

    override fun getTheRudestTeam(): Team {
        val teamRedCards = mutableMapOf<String, Int>()
        val teamPlayerCount = mutableMapOf<String, Int>()
        val teamObjects = mutableMapOf<String, Team>()

        for (player in players) {
            val teamName = player.team.name
            val team = player.team

            teamObjects[teamName] = team

            teamRedCards[teamName] = teamRedCards.getOrDefault(teamName, 0) + player.redCards
            teamPlayerCount[teamName] = teamPlayerCount.getOrDefault(teamName, 0) + 1
        }

        var maxAverage = 0.0
        var rudestTeamName = teamObjects.keys.firstOrNull() ?: ""

        for ((teamName, redCards) in teamRedCards) {
            val playerCount = teamPlayerCount[teamName] ?: 1
            val average = redCards.toDouble() / playerCount
            if (average > maxAverage) {
                maxAverage = average
                rudestTeamName = teamName
            }
        }

        return teamObjects[rudestTeamName] ?: players.first().team
    }
}