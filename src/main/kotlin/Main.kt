import parser.CsvParser
import resolver.Resolver
import infographics.Visualizer

fun main() {
    val parser = CsvParser("src/main/resources/fakePlayers.csv")
    val players = parser.parse()
    val resolver = Resolver(players)

    println("Количество игроков без агенства: ${resolver.getCountWithoutAgency()}")

    val (name, count) = resolver.getBestScorerDefender()
    println("Лучший защитник, который забил $count голов это $name")
    println("Самый дорогой немецкий игрок: ${resolver.getTheExpensiveGermanPlayerPosition()}")
    println("Команда c наибольшим средним числом красных карточек на одного игрока: ${resolver.getTheRudestTeam().name}")

    val vis = Visualizer(players)
    vis.top10TeamsByTransferCost()
}
