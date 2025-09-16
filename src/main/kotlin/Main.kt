import parser.CsvParser
import resolver.Resolver
import visualizator.Visualizer

fun main(args: Array<String>) {
    println("Соломеин Михаил. Вариант 2")
    val players = CsvParser.parsePlayers("fakePlayers.csv")
    val resolver = Resolver(players)
    println(resolver.getCountWithoutAgency())
    println(resolver.getBestScorerDefender())
    println(resolver.getTheExpensiveGermanPlayerPosition())
    println(resolver.getTheRudestTeam().name)
    val visualizer = Visualizer()
    visualizer.drawHighestTransferCostTeams(resolver.getTopHighestTransferCostTeams(10))
}
