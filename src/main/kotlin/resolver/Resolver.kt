package resolver

import model.Player
import model.Team
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf

import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.util.color.Color

class Resolver{
    // Выведите количество игроков, интересы которых не представляет агенство.
    fun getCountWithoutAgency(players: List<Player>): Int {

        val playersWithoutAgency = players.filter { player ->
            player.agency.isBlank()}

        return playersWithoutAgency.size
    }

    // Выведите автора наибольшего числа голов из числа защитников и их количество.
    fun getBestScorerDefender(players: List<Player>): Pair<String, Int>{

        val defenders = players.filter { player ->
            player.position.equals("DEFENDER", ignoreCase = true)}

        if (defenders.isEmpty()) {
            return Pair("Не найдено", 0)
        }

        var bestDefender = defenders[0]
        for (defender in defenders) {
            if (defender.goalCount > bestDefender.goalCount) {
                bestDefender = defender
            }
        }

        return Pair(bestDefender.name, bestDefender.goalCount)
    }

    // Выведите русское название позиции самого дорогого немецкого игрока.
    fun getTheExpensiveGermanPlayerPosition(players: List<Player>): String?{

        val translatePositions = mapOf(
            "GOALKEEPER" to "Вратарь",
            "DEFENDER" to "Защитник",
            "MIDFIELD" to "Полузащитник",
            "FORWARD" to "Нападающий"
        )

        val germanPlayers = players.filter { player ->
            player.nationality.equals("Germany", ignoreCase = true)}
        if (germanPlayers.isEmpty()) {
            return "Немецкие игроки не найдены"
        }
        var mostExpensivePlayer = germanPlayers[0]
        for (player in germanPlayers) {
            if (player.cost > mostExpensivePlayer.cost) {
                mostExpensivePlayer = player
            }
        }
        val russianPosition = translatePositions[mostExpensivePlayer.position]
        return russianPosition
    }

    // Выберите команду с наибольшим числом удалений на одного игрока.
    fun getTheRudestTeam(teams: List<Team>): Team{
        var rudeTeam: Team = teams[0]
        var maxRedCardsPerPlayer = 0.0

        for (team in teams) {
            val totalRedCards = team.playerList.sumOf { player -> player.redCardCount }
            val averageRedCards = totalRedCards.toDouble() / team.playerList.size

            if (averageRedCards > maxRedCardsPerPlayer) {
                maxRedCardsPerPlayer = averageRedCards
                rudeTeam = team
            }
        }
        return rudeTeam
    }

    fun createGistogram(teams: List<Team>) {
        val topTeams = teams.map { team ->
                val totalCost = team.playerList.sumOf { it.cost.toLong() }
                team.name to totalCost
            }
                .sortedByDescending { it.second }
                .take(10)

        val teamNames = topTeams.map { it.first }
        val teamCosts = topTeams.map { it.second.toDouble() }
        val plot = plot {
            bars {
                x(teamNames)
                y(teamCosts)

                fillColor = Color.hex(0x4E79A7)
                alpha = 0.8
                width = 0.6
            }
            layout {
                title = "Топ-10 команд по суммарной трансферной стоимости"
                size = 1000 to 600
            }
        }

        plot.save("histogram.png")
        println("Гистограмма сохронена в файл histogram.png")
    }
}