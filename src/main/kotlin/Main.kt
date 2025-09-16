import parser.CsvParser

fun main() {
    val players = CsvParser.parsePlayersCsv("src/main/resources/fakePlayers.csv")
    val teams = CsvParser.groupPlayersByTeam(players)
    val resolver = resolver.Resolver()

    println("Количество игроков, интересы которых не представляет агенство = ${resolver.getCountWithoutAgency(players)} ")
    val bestDefender = resolver.getBestScorerDefender(players)
    println("Автор наибольшего числа голов из числа защитников ${bestDefender.first} и их количество ${bestDefender.second} ")
    println("Русское название позиции самого дорогого немецкого игрока = ${resolver.getTheExpensiveGermanPlayerPosition(players)} ")
    println("Команда с наибольшим числом удалений на одного игрока = ${resolver.getTheRudestTeam(teams).name} ")
    resolver.createGistogram(teams)
}

