import drawers.ChartDrawer
import parser.CsvParser
import resolver.Resolver

const val PLAYERS_FILE_PATH = "src/main/resources/fakePlayers.csv"

fun main() {
    val players = CsvParser.parsePlayers(PLAYERS_FILE_PATH)

    Resolver(players).let {
        println("Количество игроков, интересы которых не представляет агенство: \"${it.getCountWithoutAgency()}\"")
        println("Автор наибольшего числа голов из числа защитников и их количество: \"${it.getBestScorerDefender()}\"")
        println("Русское название позиции самого дорогого немецкого игрока: \"${it.getTheExpensiveGermanPlayerPosition()}\"")
        println("Команда с наибольшим средним числом красных карточек на одного игрока: \"${it.getTheRudestTeam().name}\"")
    }

    ChartDrawer.drawPositionsPieChart(players)
}