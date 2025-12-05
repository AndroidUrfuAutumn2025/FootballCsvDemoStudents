import resolver.Resolver

fun main() {
    val filePath = "src/main/resources/fakePlayers.csv" // путь к CSV
    val resolver = Resolver(filePath)

    println("Игроки без агенства: ${resolver.getCountWithoutAgency()}")
    println("Лучший защитник: ${resolver.getBestScorerDefender()}")
    println("Позиция самого дорогого немецкого игрока: ${resolver.getTheExpensiveGermanPlayerPosition()}")
    println("Самая грубая команда: ${resolver.getTheRudestTeam().name}")

    val topTeams = resolver.getTeamTotalCosts()
    println("Топ-10 команд по суммарной стоимости:")
    topTeams.forEach { (team, cost) ->
        println("$team: $cost")
    }
}
