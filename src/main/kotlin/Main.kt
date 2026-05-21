import resolver.Resolver
import chart.showCountryPieChart

fun main() {
    val resolver = Resolver.createFromFile("fakePlayers.csv")

    println("1. Игроков без агентства: ${resolver.getCountWithoutAgency()}")

    val (name, goals) = resolver.getBestScorerDefender()
    println("2. Лучший бомбардир-защитник: $name — $goals голов")

    println("3. Позиция дорогого немца: ${resolver.getTheExpensiveGermanPlayerPosition()}")

    val rudest = resolver.getTheRudestTeam()
    println("4. Самая грубая команда: ${rudest.name} (${rudest.city})")

    showCountryPieChart(resolver.playersFromDifferentCountries())
}