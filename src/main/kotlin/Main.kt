import parser.DataLoader
import resolver.IResolver
import resolver.Resolver

fun main() {
    val players = DataLoader.loadPlayers()
    val resolver: IResolver = Resolver(players)

    println("Количество игроков, интересы которых не представляет агенство: ${resolver.getCountWithoutAgency()}")
    val (bestDefender, goals) = resolver.getBestScorerDefender()
    println("Автор наибольшего числа голов среди защитников: $bestDefender, количество голов: $goals")
    println("Русское название позиции самого дорогого немецкого игрока: ${resolver.getTheExpensiveGermanPlayerPosition()}")
    println("Команда с наибольшим средним числом красных карточек на одного игрока: ${resolver.getTheRudestTeam().name}")

    // Вариант 1
    if (resolver is Resolver) {
        val positionDistribution = resolver.getPositionDistribution()
        println("Доля игроков по позициям:")
        positionDistribution.forEach { (positionRus, percent) ->
            println("$positionRus: ${"%.2f".format(percent)}%")
        }
    }
}