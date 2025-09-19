import KandyPlot
import parser.CsvParser
import resolver.Resolver
fun main(args: Array<String>) {
    val data = parser.CsvParser.data
    val resolver = Resolver(players = data.second, teams = data.first)
    println("getTheRudestTeam:" + resolver.getTheRudestTeam().name)
    println("getBestScorerDefender: " + resolver.getBestScorerDefender())
    println("CountWithoutAgency: " + resolver.getCountWithoutAgency())
    println("getTheExpensiveGermanPlayerPosition: " + resolver.getTheExpensiveGermanPlayerPosition())
    println("График 10 самых дорогих команд в папке 'lets-plot-images'")
    val kandy = KandyPlot()
    kandy.main(data.first)
}