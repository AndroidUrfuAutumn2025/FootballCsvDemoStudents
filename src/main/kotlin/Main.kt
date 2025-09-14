import parser.CsvParser
import resolver.Resolver

fun main() {
    val players = CsvParser.parsePlayers("src/main/resources/fakePlayers.csv")
    val resolver = Resolver(players)

    visualization.Visualizer.plotTop10TeamsByTransferCost(players, "top10.png")


    println("Игроков без агентства: ${resolver.getCountWithoutAgency()}")
    println("Лучший защитник по голам: ${resolver.getBestScorerDefender()}")
    println("Позиция самого дорогого немца: ${resolver.getTheExpensiveGermanPlayerPosition()}")
    println("Самая грубая команда: ${resolver.getTheRudestTeam()}")
}