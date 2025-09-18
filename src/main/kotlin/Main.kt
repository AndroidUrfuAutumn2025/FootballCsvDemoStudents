import graphics.Plot
import parser.CsvParser
import resolver.Resolver

fun main(args: Array<String>) {
    val filePath = "src/main/resources/fakePlayers.csv"
    val players = CsvParser.parsePlayers(filePath, ';', true)
    val teams = CsvParser.parseTeams(filePath, ';', true)

    val resolver = Resolver(players, teams)
    val countWithoutAgency = resolver.getCountWithoutAgency()
    val bestScorerDefender = resolver.getBestScorerDefender()
    val expensiveGermanPlayerPosition = resolver.getTheExpensiveGermanPlayerPosition()
    val rudestTeam = resolver.getTheRudestTeam()

    println("Count without agency: $countWithoutAgency")
    println("Best scorer defender: $bestScorerDefender")
    println("The expensive german player position: $expensiveGermanPlayerPosition")
    println("The rudest team name: ${rudestTeam.name} (${rudestTeam.city})")

    Plot.showDependenceOfGoalsScoredOnTransferCost(players, "dependence.png")
}
