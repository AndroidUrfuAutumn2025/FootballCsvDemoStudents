import parser.CsvParser
import plot.KandyPlot
import resolver.Resolver

fun main() {
    val players = CsvParser.readPlayersFromCsv("src/main/resources/fakePlayers.csv")
    val resolver = Resolver(players)

    println("1. Игроков без агенства: ${resolver.getCountWithoutAgency()}")

    val bestDefender = resolver.getBestScorerDefender()
    println("2. Лучший защитник: ${bestDefender.first}, ${bestDefender.second} голов")

    println("3. Позиция самого дорогого немца: ${resolver.getTheExpensiveGermanPlayerPosition()}")

    println("4. Команда с наибольшим количеством красных карточек: ${resolver.getTheRudestTeam().name}")

    // визуализация вариант 4
    KandyPlot().showCountryDistribution(players)

}