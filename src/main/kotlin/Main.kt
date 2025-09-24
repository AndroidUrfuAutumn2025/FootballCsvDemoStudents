import java.io.File
import parser.CsvParser
import resolver.Resolver


fun main() {
    val players = CsvParser.parse(File("src/main/resources/fakePlayers.csv"))
    val resolver = Resolver(players)


    println("Игроки без агентства: ${resolver.getCountWithoutAgency()}")

    val (name, goals) = resolver.getBestScorerDefender()
    println("Защитник: $name с $goals голами")

    val expensiveGermanPosition = resolver.getTheExpensiveGermanPlayerPosition()
    println("Позиция самого дорогого немецкого игрока: $expensiveGermanPosition")

    val rudestTeam = resolver.getTheRudestTeam()
    println("Команда с наибольшим средним числом красных карточек: ${rudestTeam.name}")


}
