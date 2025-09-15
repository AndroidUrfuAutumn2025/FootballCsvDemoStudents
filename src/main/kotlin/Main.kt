import model.Player
import model.Team
import parser.CsvParser
import resolver.Resolver
import visualization.ChartGenerator

fun main(args: Array<String>) {
    val players = CsvParser.parsePlayers("src/main/resources/fakePlayers.csv")
    val teams = CsvParser.parseTeams(players)
    val resolver = Resolver(players, teams)
    println("1. Количество игроков без агентства: ${resolver.getCountWithoutAgency()}")
    val bestDefender = resolver.getBestScorerDefender()
    println("2. Лучший бомбардир среди защитников: ${bestDefender.first} (${bestDefender.second} голов)")
    println("3. Позиция самого дорогого немецкого игрока: ${resolver.getTheExpensiveGermanPlayerPosition()}")
    val rudestTeam = resolver.getTheRudestTeam()
    println("4. Команда с наибольшим средним числом красных карточек: ${rudestTeam.name}")
    val forwardsData = resolver.getForwardsGoalsVsTransferCost()
    val chart = ChartGenerator.createGoalsVsTransferCostChart(forwardsData)
    ChartGenerator.saveChartAsPNG(chart, "forwards_goals_vs_transfer_cost.png")
}