import parser.CsvParser
import javax.swing.SwingUtilities
import resolver.Resolver
import java.io.File

fun main() {
    val csvFile = File("src/main/resources/fakePlayers.csv")
    val (players, teams) = CsvParser.parsePlayersAndTeams(csvFile)

    val resolver = Resolver(players, teams)

    println("Количество игроков, интересы которых не представляет агенство: ${resolver.getCountWithoutAgency()}")
    println("Автор наибольшего числа голов из числа защитников и их количество: ${resolver.getBestScorerDefender()}")
    println("Русское название позиции самого дорогого немецкого игрока: ${resolver.getTheExpensiveGermanPlayerPosition()}")
    println("Команда с наибольшим числом удалений на одного игрока: ${resolver.getTheRudestTeam().name}")


    // Визуализация
    SwingUtilities.invokeLater {
        DataVisualizer.showGoalsVsCostChart(players)
    }
    println("ВАРИАНТ-3!")
}