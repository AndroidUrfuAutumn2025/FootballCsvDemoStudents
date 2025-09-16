import parser.CsvParser
import plot.TeamVisualizer
import resolver.Resolver
import java.io.File

fun main() {
    try {
        val players = CsvParser.parse(File("src/main/resources/fakePlayers.csv"))
        println("Загружено ${players.size} игроков")

        val resolver = Resolver(players)

        println("1. Игроков без агентства: ${resolver.getCountWithoutAgency()}")

        val bestScorer = resolver.getBestScorerDefender()
        println("2. Лучший бомбардир среди защитников: ${bestScorer.first} (${bestScorer.second} голов)")

        val germanPosition = resolver.getTheExpensiveGermanPlayerPosition()
        println("3. Позиция самого дорогого немецкого игрока: $germanPosition")

        val rudestTeam = resolver.getTheRudestTeam()
        println("4. Команда с наибольшим средним числом красных карточек: ${rudestTeam.name} (${rudestTeam.city})")

        println("\nВизуализация топ-10 команд по трансферной стоимости...\n")
        val topTeams = resolver.getTop10TeamsByTransferCost()
        TeamVisualizer.visualizeTop10Teams(topTeams)

        println("Топ-10 команд по трансферной стоимости:")
        topTeams.forEachIndexed { index, (team, cost) ->
            val costFormatted = "%,d".format(cost)
            println(" ${index + 1}) ${team.name} (${team.city}): $costFormatted €")
        }

    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
        e.printStackTrace()
    }
}