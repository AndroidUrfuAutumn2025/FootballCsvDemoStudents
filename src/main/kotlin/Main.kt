import parser.CsvParser
import resolver.PieChart
import resolver.Resolver

fun main(args: Array<String>) {
    val playersData = CsvParser.playersParsing("src/main/resources/fakePlayers.csv")

    println("Игроков без агенства: ${Resolver(playersData).getCountWithoutAgency()}")
    val topPlayer = Resolver(playersData).getBestScorerDefender()
    print("Автор наибольшего числа голов из числа защитников: ${topPlayer.first}. ")
    println("Количесво голов: ${topPlayer.second}")
    println("Название позиции самого дорогого немецкого игрока: ${Resolver(playersData).getTheExpensiveGermanPlayerPosition()}")
    println("Команда с наибольшим средним числом красных карточек на одного игрока: ${Resolver(playersData).getTheRudestTeam().name}")

    PieChart().showTopTeamsChart(Resolver(playersData).getTop10TeamsByTransferCost())
}