import parser.CsvParser
import java.io.File
import model.enums.TeamName
import resolver.Resolver
import chart.PositionChartGenerator

fun main(args: Array<String>) {
    val csvFile = File("src/main/resources/fakePlayers.csv")
    val players = CsvParser.parsePlayers(csvFile)
    val resolver = Resolver(players)

    // Выведите количество игроков,
    // интересы которых не представляет агенство.
     println(resolver.getCountWithoutAgency())

    // Выведите автора наибольшего числа
    // голов из числа защитников и их количество.
    val (author, amount) = resolver.getBestScorerDefender()
    println("$author, $amount")

    // Выведите русское название
    // позиции самого дорогого немецкого игрока.
    println(resolver.getTheExpensiveGermanPlayerPosition())

    // Выберите команду с наибольшим
    // средним числом красных карточек на одного игрока.
    val team = resolver.getTheRudestTeam()
    println(team.name.displayName)
    
    // Генерируем диаграмму распределения игроков по позициям
    val chartGenerator = PositionChartGenerator()
    chartGenerator.showPositionDistributionChart(players)
}