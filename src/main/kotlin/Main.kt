import parser.DataLoader
import resolver.Resolver
import vizualization.BarChart

fun main(args: Array<String>) {
    val res = Resolver(DataLoader.loadData())
    try {
        println(res.getCountWithoutAgency())
        println(res.getBestScorerDefender())
        println(res.getTheExpensiveGermanPlayerPosition())
        println(res.getTheRudestTeam().name)
    } catch (e: NoSuchElementException) {
        println(e.message)
    }

    BarChart.visualizePositionDistribution(res.getDistributionByPositions())
}