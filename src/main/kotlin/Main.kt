import parser.CsvParser
import resolver.Resolver
import visualization.Visualizer

fun main() {
    try {
        val players = CsvParser.readCsv("fakePlayers.csv")
        println("Загружено игроков: ${players.size}")

        val resolver = Resolver(players)

        // Задача 1
        val countNoAgency = resolver.getCountWithoutAgency()
        println("1. Игроков без агентства: $countNoAgency")

        // Задача 2
        val bestDefender = resolver.getBestScorerDefender()
        println("2. Лучший бомбардир-защитник: ${bestDefender?.first} (${bestDefender?.second} голов)")

        // Задача 3
        val germanPos = resolver.getTheExpensiveGermanPlayerPosition()
        println("3. Позиция самого дорогого немца: $germanPos")

        // Задача 4
        val rudestTeam = resolver.getTheRudestTeam()
        if (rudestTeam != null) {
            println("4. Самая грубая команда: ${rudestTeam.name}")
        }

        // График соотношения позиций игроков
        Visualizer.visualizePositions(players)

    } catch (e: Exception) {
        println("Ошибка выполнения: ${e.message}")
        e.printStackTrace()
    }
}