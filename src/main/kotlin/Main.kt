import parser.CsvParser
import resolver.Resolver

fun main() {
    val resource = Thread.currentThread().contextClassLoader.getResource("fakePlayers.csv")
        ?: error("Не найден ресурс fakePlayers.csv")

    val lines = resource.readText().lines()
    val players = CsvParser.parsePlayers(lines)

    val resolver = Resolver(players)

    println("Игроков без агентства: ${resolver.getCountWithoutAgency()}")
    val (name, goals) = resolver.getBestScorerDefender()
    println("Лучший бомбардир среди защитников: $name ($goals)")
    println("Позиция самого дорогого немецкого игрока: ${resolver.getTheExpensiveGermanPlayerPosition()}")
    println("Самая грубая команда: ${resolver.getTheRudestTeam()}")
    println("Доли игроков по позициям:")
    resolver.getPositionsShare().forEach { (pos, share) ->
        println(" - $pos: ${"%.2f".format(share * 100)}%")
    }
}