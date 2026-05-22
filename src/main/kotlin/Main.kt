
import parser.CsvParser
import resolver.Resolver
import model.Team
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.data.category.DefaultCategoryDataset
import javax.swing.JFrame
import java.awt.Color

fun main(args: Array<String>) {
    val players = CsvParser.parsePlayers("fakePlayers.csv")

    val resolver = Resolver(players)

    println("1. Количество игроков, интересы которых не представляет агенство: " + resolver.getCountWithoutAgency())
    println("2. Автор наибольшего числа голов из числа защитников и их количество: " + resolver.getBestScorerDefender())
    println("3. Позиция самого дорогого немецкого игрока: " + resolver.getTheExpensiveGermanPlayerPosition())
    println("4. Команда с наибольшим средним числом красных карточек на одного игрока: " + resolver.getTheRudestTeam())

    showTopTeamsChart(resolver.getTop10TeamsByCost())
}

fun showTopTeamsChart(topTeams: List<Pair<Team, Double>>) {
    val dataset = DefaultCategoryDataset()

    for ((team, cost) in topTeams) {
        dataset.addValue(cost / 1_000_000, "Стоимость (млн €)", team.name)
    }

    val chart: JFreeChart = ChartFactory.createBarChart(
        "Топ-10 команд по суммарной трансферной стоимости",
        "Команда",
        "Стоимость (млн €)",
        dataset
    )

    val plot = chart.categoryPlot
    plot.renderer.setSeriesPaint(0, Color(65, 105, 225))
    val frame = JFrame("Трансферная стоимость команд")
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.add(ChartPanel(chart))
    frame.setSize(1000, 600)
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}