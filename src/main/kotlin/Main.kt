import parser.CsvParser
import resolver.Resolver
import plot.VisualizerJFreeChart
import java.io.File

fun main() {
    val players = CsvParser.parse(File("src/main/resources/fakePlayers.csv"))

    val resolver = Resolver(players)

    println("1)Количество Игроков без агентства: ${resolver.countWithoutAgency()}")

    val (defender, goals) = resolver.bestDefenderByGoals()
    println("2)Лучший защитник-бомбардир: $defender ($goals голов)")

    println("3)Самый дорогой немец играет на позиции: ${resolver.mostExpensiveGermanPosition()}")

    val rudestTeam = resolver.teamWithMostRedCards()
    println("4)Команда с наибольшим средним числом красных карточек: ${rudestTeam.name} (${rudestTeam.city})")

    println("\n5)Визуализация распределения игроков по странам:\n")
    val distribution = resolver.nationalityDistribution()
    VisualizerJFreeChart.visualize(distribution, players.size)
}