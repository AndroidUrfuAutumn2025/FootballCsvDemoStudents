import parser.CsvParser
import resolver.Resolver
import visualization.ChartGenerator
import java.nio.file.Paths

fun main(args: Array<String>) {
    val csvPath = Paths.get("src/main/resources/fakePlayers.csv").toString()
    val players = CsvParser.parsePlayers(csvPath)
    val resolver = Resolver(players)

    println("Количество игроков без агентства: ${resolver.getCountWithoutAgency()}")
    val (defender, goals) = resolver.getBestScorerDefender()
    println("Лучший защитник по голам: $defender ($goals голов)")
    println("Позиция самого дорогого немецкого игрока: ${resolver.getTheExpensiveGermanPlayerPosition()}")
    val rudestTeam = resolver.getTheRudestTeam()
    println("Самая грубая команда: ${rudestTeam.name} (${rudestTeam.city})")

    val chartGenerator = ChartGenerator()
    val chart = chartGenerator.createPositionDistributionChart(players)
    chartGenerator.showChart(chart)
}

private fun positionToRussian(position: String): String = when (position.uppercase()) {
    "DEFENDER" -> "Защитник"
    "FORWARD" -> "Нападающий"
    "MIDFIELD" -> "Полузащитник"
    "GOALKEEPER" -> "Вратарь"
    else -> position
}