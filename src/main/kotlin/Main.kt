import model.Player
import parser.CsvParser
import resolver.Resolver

fun main() {
    try {
        val filePath = "src/main/resources/fakePlayers.csv"
        val players: List<Player> = CsvParser.parsePlayers(filePath)

        val resolver = Resolver(players)

        println("1. Игроков без агентства: ${resolver.getCountWithoutAgency()}")

        val bestDefender = resolver.getBestScorerDefender()
        println("2. Лучший бомбардир среди защитников: ${bestDefender.first} (голов: ${bestDefender.second})")

        val germanPosition = resolver.getTheExpensiveGermanPlayerPosition()
        println("3. Позиция самого дорогого немецкого игрока: $germanPosition")

        val rudestTeam = resolver.getTheRudestTeam()
        println("4. Самая грубая команда: ${rudestTeam.name}")

        resolver.showNationalityDistributionChart()
        readLine()

    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
        e.printStackTrace()
    }
}