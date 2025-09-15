import model.Player
import parser.CsvParser
import resolver.Resolver

fun main(args: Array<String>) {
    try {
        val players: List<Player> = CsvParser.readPlayersFromCsv("src/main/resources/fakePlayers.csv")

        val resolver = Resolver(players)

        println("1. Количество игроков без агентства: ${resolver.getCountWithoutAgency()}")

        val bestScorer = resolver.getBestScorerDefender()
        println("2. Лучший бомбардир среди защитников: ${bestScorer.first} (голов: ${bestScorer.second})")

        val germanPosition = resolver.getTheExpensiveGermanPlayerPosition()
        println("3. Позиция самого дорогого немецкого игрока: $germanPosition")

        val rudestTeam = resolver.getTheRudestTeam()
        println("4. Самая грубая команда: ${rudestTeam.name} из ${rudestTeam.city}")

        println("\n5. Топ-10 команд по суммарной трансферной стоимости:")
        val topTeams = resolver.getTop10TeamsByTransferValue()
        if (topTeams.isEmpty()) {
            println("   Нет данных о командах")
        } else {
            topTeams.forEachIndexed { index, (team, totalValue) ->
                println("   ${index + 1}. ${team.name} (${team.city}): ${formatCurrency(totalValue)}")
            }
        }
    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
        e.printStackTrace()
    }

}
fun formatCurrency(amount: Long): String {
    return "%,d".format(amount)
}