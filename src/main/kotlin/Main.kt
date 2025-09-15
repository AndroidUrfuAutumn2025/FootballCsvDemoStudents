import parser.CsvParser
import resolver.Resolver
import visualization.ChartBuilder

fun main() {
    println("Загрузка данных...")

    val players = CsvParser.parsePlayers("src/main/resources/fakePlayers.csv")
    println("Загружено ${players.size} игроков")

    val resolver = Resolver(players)

    // Задания
    println("\n1. Игроков без агентства: ${resolver.getCountWithoutAgency()}")

    val bestDefender = resolver.getBestScorerDefender()
    println("2. Лучший защитник: ${bestDefender.first} (голов: ${bestDefender.second})")

    val germanPosition = resolver.getTheExpensiveGermanPlayerPosition()
    println("3. Позиция самого дорогого немецкого игрока: $germanPosition")

    val rudestTeam = resolver.getTheRudestTeam()
    println("4. Самая грубая команда: ${rudestTeam.name}")

    // Создаем диаграмму
    println("\nСоздание диаграммы...")
    ChartBuilder.createNationalityPieChart(players)

    println("Готово!")
}