import parser.ChartVisualizer
import parser.CsvParser
import resolver.Resolver

fun main() {
    val players = CsvParser.parse("src/main/resources/fakePlayers.csv")
    val resolver = Resolver(players)

    println("Количество игроков, интересы которых не представляет агенство: ${resolver.getCountWithoutAgency()}")
    val bestDefender = resolver.getBestScorerDefender()
    println("Автор наибольшего числа голов из числа защитников: ${bestDefender.first}, количество голов: ${bestDefender.second}")
    println("Русское название позиции самого дорогого немецкого игрока: ${resolver.getTheExpensiveGermanPlayerPosition()}")
    println("Команда с наибольшим средним числом красных карточек на одного игрока: ${resolver.getTheRudestTeam().name}")

    ChartVisualizer.showNationalityDistribution(players)
}