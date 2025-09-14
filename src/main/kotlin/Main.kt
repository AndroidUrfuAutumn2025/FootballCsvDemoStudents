import KandyPlot
import parser.CsvParser
import resolver.Resolver
fun main(args: Array<String>) {
    val parser = CsvParser
    val data = parser.data
    val resolver = Resolver(players = data.second, teams = data.first)
    println("getTheRudestTeam:" + resolver.getTheRudestTeam())
    println("getBestScorerDefender: " + resolver.getBestScorerDefender())
    println("CountWithoutAgency: " + resolver.getCountWithoutAgency())
    println("getTheExpensiveGermanPlayerPosition: " + resolver.getTheExpensiveGermanPlayerPosition())
    println("График: зависимость количества голов от трансферной стоимости в папке 'lets-plot-images'")
    val kandy = KandyPlot()
    kandy.main(data.first)
}