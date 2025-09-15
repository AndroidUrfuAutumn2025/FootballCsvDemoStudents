import model.Player
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.ui.ApplicationFrame
import org.jfree.data.category.DefaultCategoryDataset
import paintdiagram.plotWithJFreeChart
import parser.CsvParser
import resolver.Resolver
import javax.swing.WindowConstants

fun main(args: Array<String>) {
//    print("Yeah rock!")
    val players = CsvParser.loadPlayers()
    val team = CsvParser.loadTeams(players)

    plotWithJFreeChart(players)

    val resolver = Resolver(players, team)

    val rudestTeam = resolver.getTheRudestTeam()
    println("Самая грубая команда: ${rudestTeam.name} (${rudestTeam.city})")

    val bestDefScor = resolver.getBestScorerDefender()
    println("Лучший защитник по голам: ${bestDefScor}")

    val notAgency = resolver.getCountWithoutAgency()
    println("Количество игроков которых не представляет агенство: ${notAgency}")

    val expensiveGermanPlayer = resolver.getTheExpensiveGermanPlayerPosition()
    println("Самый дорогой немецкий игрок: ${expensiveGermanPlayer}")


//    println("Список игроков:")
//    players.forEach { player ->
//        println("${player.name} — ${player.transferValue}")
//    }

//    println("\nСписок команд:")
//    val teams = CsvParser.loadTeams(players)
//    teams.forEach { team ->
//        println("${team.name} (${team.city})")
//    }
}