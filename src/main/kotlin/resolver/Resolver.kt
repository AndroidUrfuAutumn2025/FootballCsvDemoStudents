package resolver

import model.Player
import model.Team
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
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

        if (defenders.isEmpty()) return Pair("Защитники не обнаружены", 0)

        val bestDefender = defenders.maxByOrNull { it.goals }
        return Pair(bestDefender?.name ?: "Unknown", bestDefender?.goals ?: 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter {
            it.nationality.equals("Germany", ignoreCase = true) ||
                    it.nationality.contains("Germany", ignoreCase = true)
        }

        if (germanPlayers.isEmpty()) return "Немецкие игроки не обнаружены"

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

    data class TeamTransferStats(
        val team: Team,
        val totalTransferCost: Long,
        val playerCount: Int,
        val averageTransferCost: Double
    )

    fun getTopTeamsByTransferCost(limit: Int = 10): List<TeamTransferStats> {
        return players.groupBy { it.team }
            .map { (team, players) ->
                val totalCost = players.sumOf { it.transferCost.toLong() }
                val count = players.size
                val averageCost = if (count > 0) totalCost.toDouble() / count else 0.0
                TeamTransferStats(team, totalCost, count, averageCost)
            }
            .sortedByDescending { it.totalTransferCost }
            .take(limit)
    }

    fun showTopTeamsTransferCostChart(limit: Int = 10) {
        SwingUtilities.invokeLater {
            val topTeams = getTopTeamsByTransferCost(limit)
            val dataset = createBarDataset(topTeams)
            val chart = createBarChart(dataset, limit)
            displayBarChart(chart)
        }
    }

    private fun createBarDataset(topTeams: List<TeamTransferStats>): DefaultCategoryDataset {
        val dataset = DefaultCategoryDataset()

        topTeams.reversed().forEachIndexed { index, stats ->
            dataset.addValue(
                stats.totalTransferCost.toDouble() / 1_000_000.0,
                "Трансферная стоимость",
                "${topTeams.size - index}. ${stats.team.name}"
            )
        }

        return dataset
    }

    private fun createBarChart(dataset: DefaultCategoryDataset, limit: Int): JFreeChart {
        val chart = ChartFactory.createBarChart(
            null,
            "Команды",
            "Трансферная стоимость (млн €)",
            dataset,
            org.jfree.chart.plot.PlotOrientation.HORIZONTAL,
            true,
            true,
            false
        )

        chart.title = org.jfree.chart.title.TextTitle(
            "ТОП-$limit КОМАНД ПО СУММАРНОЙ ТРАНСФЕРНОЙ СТОИМОСТИ",
            org.jfree.chart.axis.Axis.DEFAULT_TICK_LABEL_FONT.deriveFont(16f)
        )

        chart.setBackgroundPaint(Color.white)

        val plot = chart.categoryPlot
        plot.setBackgroundPaint(Color.lightGray)
        plot.setOutlinePaint(null)

        val domainAxis = plot.domainAxis
        domainAxis.setTickLabelFont(org.jfree.chart.axis.Axis.DEFAULT_TICK_LABEL_FONT.deriveFont(10f))

        val rangeAxis = plot.rangeAxis
        rangeAxis.setStandardTickUnits(org.jfree.chart.axis.NumberAxis.createStandardTickUnits())

        val renderer = plot.renderer as org.jfree.chart.renderer.category.BarRenderer
        renderer.setSeriesPaint(0, Color(65, 105, 225))
        renderer.setDrawBarOutline(false)
        renderer.setShadowVisible(false)

        renderer.setDefaultItemLabelsVisible(true)

        renderer.setDefaultItemLabelFont(org.jfree.chart.axis.Axis.DEFAULT_TICK_LABEL_FONT.deriveFont(10f))
        renderer.setDefaultItemLabelPaint(Color.BLACK)

        return chart
    }

    private fun displayBarChart(chart: JFreeChart) {
        val frame = JFrame("Топ команд по трансферной стоимости")
        frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        frame.size = Dimension(1200, 800)

        val chartPanel = ChartPanel(chart).apply {
            preferredSize = Dimension(1100, 700)
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