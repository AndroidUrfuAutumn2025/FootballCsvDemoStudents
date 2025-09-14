import model.Team
import parser.CsvParser
import plot.PlotUtils
import resolver.Resolver
import java.io.File

/**
 * Основная точка входа приложения
 */
fun main() {
    try {
        val players = CsvParser.parse(File("src/main/resources/fakePlayers.csv"))

        val resolver = Resolver(players)
        val bestDefender = resolver.getBestScorerDefender()
        val rudestTeam = resolver.getTheRudestTeam()
        val top10: List<Pair<Team, Long>> = resolver.getTop10TeamsByTransferCost()


        println("Ответы:")
        println("1. Игроков без агентства: ${resolver.getCountWithoutAgency()}")
        println("2. Автора наибольшего числа голов из числа защитников и их количество: ${bestDefender.first}, ${bestDefender.second} голов")
        println("3. Русское название позиции самого дорогого немецкого игрока: ${resolver.getTheExpensiveGermanPlayerPosition()}")
        println("4. Команда с наибольшим средним числом красных карточек: ${rudestTeam.name} (${rudestTeam.city})")


        println("\nВариант 2 - Топ-10 команд с наибольшей суммарной трансферной стоимостью:")
        println("Топ-10 команд по суммарной трансферной стоимости:")
        top10.forEachIndexed { index, (team, cost) ->
            val costFormatted = "%,d".format(cost)
            println(" ${index + 1}) ${team.name} (${team.city}): $costFormatted €")
        }
        PlotUtils.visualizeTop10Teams(top10)

    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
        e.printStackTrace()
    }
}