import parser.CsvParser
import resolver.Resolver
import visualizer.ChartGenerator

fun main(args: Array<String>) {
    val pathName = "src/main/resources/fakePlayers.csv"
    val players = CsvParser.parseFile(pathName)

    val resolver = Resolver(players)
    val playersWithoutAgencyAmount = resolver.getCountWithoutAgency()
    val bestDefender = resolver.getBestScorerDefender()
    val rudestTeam = resolver.getTheRudestTeam()
    val mostExpensiveGermanPlayer = resolver.getTheExpensiveGermanPlayerPosition()

    println("Игроков без агентства: $playersWithoutAgencyAmount")
    println("Лучший защитник-бомбардир это ${bestDefender.first} с кол-вом голов ${bestDefender.second}")
    println("Позиция самого дорогого немца: $mostExpensiveGermanPlayer")
    println("Самая грубая команда: ${rudestTeam.name}")

    ChartGenerator.createPositionPieChart(players, "img-output/players_positions.png")
    println("Диаграмма сохранена в файл players_positions.png")
}








