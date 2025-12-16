import parser.CsvParser
import resolver.Resolver
import visualization.Visualizer

fun main() {
    try {
        val players = CsvParser.readCsv("fakePlayers.csv")
        println("Загружено игроков: ${players.size}")

        val resolver = Resolver(players)

        val countNoAgency = resolver.getCountWithoutAgency()
        println("1. Игроков без агентства: $countNoAgency")

        val bestDefender = resolver.getBestScorerDefender()
        println("2. Лучший бомбардир-защитник: ${bestDefender?.first} (${bestDefender?.second} голов)")

        val germanPos = resolver.getTheExpensiveGermanPlayerPosition()
        println("3. Позиция самого дорогого немца: $germanPos")

        val rudestTeam = resolver.getTheRudestTeam()
        if (rudestTeam != null) {
            println("4. Самая грубая команда: ${rudestTeam.name}")
        }

        Visualizer.visualizePositions(players)

    } catch (e: Exception) {
        println("Ошибка выполнения: ${e.message}")
        e.printStackTrace()
    }
}

