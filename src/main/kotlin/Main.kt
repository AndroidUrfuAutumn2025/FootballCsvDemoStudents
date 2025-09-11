import java.io.File
import parser.CsvParser
import resolver.Resolver
import diagram.DiagramCreator

fun main() {
    val players = CsvParser.parse(File("src/main/resources/fakePlayers.csv"))
    val resolver = Resolver(players)
    val diagramCreator = DiagramCreator(players)

    println("Количество игроков без агентства: ${resolver.getCountWithoutAgency()}")

    val (name, goals) = resolver.getBestScorerDefender()
    println("Лучший защитник: $name с $goals голами")

    val expensiveGermanPosition = resolver.getTheExpensiveGermanPlayerPosition()
    println("Позиция самого дорогого немецкого игрока: $expensiveGermanPosition")

    val rudestTeam = resolver.getTheRudestTeam()
    println("Команда с наибольшим средним числом красных карточек: ${rudestTeam.name}")

    diagramCreator.saveCountryDistributionChart()
}