package resolver

import model.Player
import model.Team
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.data.general.DefaultPieDataset
import java.awt.Color
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities

class Resolver(private val players: List<Player>) : IResolver {

    private enum class Position(val english: String, val russian: String) {
        GOALKEEPER("GOALKEEPER", "Вратарь"),
        DEFENDER("DEFENDER", "Защитник"),
        MIDFIELD("MIDFIELD", "Полузащитник"),
        FORWARD("FORWARD", "Нападающий"),
        UNKNOWN("UNKNOWN", "Неизвестно");

        companion object {
            fun fromEnglish(englishPosition: String): Position {
                return values().find { it.english.equals(englishPosition, ignoreCase = true) } ?: UNKNOWN
            }
        }
    }

    override fun getCountWithoutAgency(): Int {
        return players.count {
            it.agency.isBlank() || it.agency == "N/A" || it.agency == "-" || it.agency == "null"
        }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val defenders = players.filter {
            it.position.equals("DEFENDER", ignoreCase = true) ||
                    it.position.contains("DEFENDER", ignoreCase = true)
        }

        if (defenders.isEmpty()) return Pair("Защитники не найдены", 0)

        val bestDefender = defenders.maxByOrNull { it.goals }
        return Pair(bestDefender?.name ?: "Unknown", bestDefender?.goals ?: 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter {
            it.nationality.equals("Germany", ignoreCase = true) ||
                    it.nationality.contains("Germany", ignoreCase = true)
        }

        if (germanPlayers.isEmpty()) return "Нет немецких игроков"

        val mostExpensive = germanPlayers.maxByOrNull { it.transferCost }
        return translatePositionToRussian(mostExpensive?.position ?: "Unknown")
    }

    override fun getTheRudestTeam(): Team {
        val teamRedCards = players.groupBy { it.team }
            .filter { it.value.isNotEmpty() }
            .mapValues { (_, players) ->
                val totalRedCards = players.sumOf { it.redCards }
                totalRedCards.toDouble() / players.size
            }

        return teamRedCards.maxByOrNull { it.value }?.key ?: Team("Unknown", "Unknown")
    }

    private fun translatePositionToRussian(position: String): String {
        return Position.fromEnglish(position).russian
    }

    fun showNationalityDistributionChart() {
        SwingUtilities.invokeLater {
            val distribution = getPlayersByNationalityDistribution()
            val dataset = createPieDataset(distribution)
            val chart = createPieChart(dataset)
            displayChart(chart)
        }
    }

    private fun getPlayersByNationalityDistribution(): Map<String, Int> {
        return players.groupBy { it.nationality }
            .mapValues { it.value.size }
            .toList()
            .sortedByDescending { it.second }
            .toMap()
    }

    private fun createPieDataset(data: Map<String, Int>): DefaultPieDataset<String> {
        val dataset = DefaultPieDataset<String>()

        val sortedData = data.toList().sortedByDescending { it.second }
        val topCountries = sortedData.take(10)

        topCountries.forEach { (country, count) ->
            dataset.setValue("$country ($count)", count.toDouble())
        }

        return dataset
    }

    private fun createPieChart(dataset: DefaultPieDataset<String>): JFreeChart {
        return ChartFactory.createPieChart(
            "Распределение игроков по странам",
            dataset,
            true,
            true,
            false
        ).apply {
            setBackgroundPaint(Color.white)

            val plot = this.plot as org.jfree.chart.plot.PiePlot<*>
            plot.setBackgroundPaint(Color.white)
            plot.setOutlinePaint(Color.white)
            plot.setLabelBackgroundPaint(Color.white)
            plot.setShadowPaint(null)
            plot.isCircular = true
            plot.setInteriorGap(0.02)
            plot.labelGap = 0.02
        }
    }

    private fun displayChart(chart: JFreeChart) {
        val frame = JFrame("Распределение игроков по странам")
        frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        frame.size = Dimension(800, 600)

        val chartPanel = ChartPanel(chart).apply {
            preferredSize = Dimension(700, 500)
            setMouseZoomable(true, false)
        }

        val panel = JPanel()
        panel.add(chartPanel)
        frame.contentPane = panel

        frame.pack()
        frame.setLocationRelativeTo(null)
        frame.isVisible = true
    }
}