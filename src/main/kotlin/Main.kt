import chart.ChartBuilder
import model.Position
import model.Team
import parser.CsvParser
import resolver.Resolver

fun main(args: Array<String>) {
    val filePath = "src/main/resources/fakePlayers.csv"

    val players = CsvParser.parsePlayers(filePath)
    val teams = Team.extractTeamsFromPlayers(players)

    val resolver = Resolver(players, teams)

    val playersWithoutAgency = resolver.getCountWithoutAgency()
    val bestScorerDefender = resolver.getBestScorerDefender()
    val mostExpensiveGermanPlayerPosition = resolver.getTheExpensiveGermanPlayerPosition()
    val rudestTeam = resolver.getTheRudestTeam()
    val chartData = players.filter { it.position == Position.FORWARD }
        .map { it.goals to it.transferCost }

    println("Количество игроков, интересы которых не представляет агенство: " +
            "$playersWithoutAgency")
    println("Автор наибольшего числа голов из числа защитников и их количество: " +
            "${bestScorerDefender.first} забил ${bestScorerDefender.second} голов")
    println("Русское название позиции самого дорогого немецкого игрока: " +
            mostExpensiveGermanPlayerPosition)
    println("Команда с наибольшим средним числом красных карточек на одного игрока: " +
            rudestTeam.name)

    ChartBuilder.createScatterPlot(
        data = chartData,
        title = "Зависимость количества забитых голов от трансферной стоимости для нападающих",
        labelX = "Голы",
        labelY = "Трансферная стоимость",
        pointLabel = "Игроки"
    )
}