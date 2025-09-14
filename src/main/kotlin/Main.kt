import resolver.Resolver
import visualisation.ChartService

fun main() {
    val resolver  = Resolver("src/main/resources/fakePlayers.csv")

    println("Количество игроков, интересы которых не представляет агенство: ${resolver.getCountWithoutAgency()}")

    val bestDefender = resolver.getBestScorerDefender()
    println("Лучший защитник: ${bestDefender.first} - ${bestDefender.second} голов")

    println("Русское название позиции самого дорогого немецкого игрока: ${resolver.getTheExpensiveGermanPlayerPosition()}")

    val rudestTeam = resolver.getTheRudestTeam()
    println("Команда с наибольшим числом удалений на одного игрока: ${rudestTeam.name} (${rudestTeam.city})")

    val goalsChart = ChartService.createGoalsVsTransferCostChart(resolver.players)
    ChartService.showChart(goalsChart)
}
