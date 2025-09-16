import parser.CsvParser
import resolver.Resolver

fun main(args: Array<String>) {
    CsvParser.readCSV()

    val resolver = Resolver(CsvParser.playersList)

    println(resolver.getCountWithoutAgency())
    println(resolver.getBestScorerDefender())
    println(resolver.getTheExpensiveGermanPlayerPosition())
    println(resolver.getTheRudestTeam().name)
    resolver.showPositionProportion()
}