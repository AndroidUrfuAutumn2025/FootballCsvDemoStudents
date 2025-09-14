import parser.CsvParser
import resolver.Resolver

fun main() {
    val players = CsvParser.parsePlayers("src/main/resources/fakePlayers.csv")
    val resolver = Resolver(players)

    println("Игроков без агентства: ${resolver.getCountWithoutAgency()}")

    val bestDefender = resolver.getBestScorerDefender()
    println("Лучший защитник: ${bestDefender.first} с ${bestDefender.second} голами")

    val germanPosition = resolver.getTheExpensiveGermanPlayerPosition()
    println("Позиция самого дорогого немецкого игрока: $germanPosition")

    val rudestTeam = resolver.getTheRudestTeam()
    println("Самая грубая команда: ${rudestTeam.name} из города ${rudestTeam.city}")

    DiagramCreator().createChart(players)
}