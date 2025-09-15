import model.Team
import parser.CsvParser
import resolver.Resolver
import org.knowm.xchart.CategoryChartBuilder
import org.knowm.xchart.BitmapEncoder

fun main() {
    val players = CsvParser.parsePlayers("src/main/resources/fakePlayers.csv")
    val teams = players.groupBy { Pair(it.team, it.city) }
        .map { Team(it.key.first, it.key.second, it.value) }
    val resolver = Resolver()

    println("1. Количество игроков без агентства: ${resolver.getCountWithoutAgency(players)}")

    resolver.getBestScorerDefender(players)?.let { (name, goals) ->
        println("2. Лучший защитник по голам: $name ($goals)")
    } ?: println("2. Нет защитников")

    println("3. Русское название позиции самого дорогого немецкого игрока: ${resolver.getTheExpensiveGermanPlayerPosition(players) ?: "Нет немецких игроков"}")

    println("4. Команда с наибольшим средним числом красных карточек: ${resolver.getTheRudestTeam(teams) ?: "Нет команд"}")

    // Топ-10 команд по суммарной стоимости
    val topTeams = teams
        .map { it.name to it.players.sumOf { p -> p.transferCost } }
        .sortedByDescending { it.second }
        .take(10)

    val names = topTeams.map { it.first }
    val values = topTeams.map { it.second.toDouble() }

    val chart = CategoryChartBuilder()
        .width(1920)
        .height(1080)
        .title("Топ-10 команд по суммарной трансферной стоимости")
        .xAxisTitle("Команда")
        .yAxisTitle("Сумма трансферов (млн евро)")
        .build()

    chart.styler.isLegendVisible = false
    chart.styler.isPlotGridLinesVisible = true

    chart.addSeries("Сумма трансферов", names.toMutableList(), values.toMutableList())

    BitmapEncoder.saveBitmap(chart, "top_teams_chart.png", BitmapEncoder.BitmapFormat.PNG)

    println("5. График сохранен в top_teams_chart.png")
}