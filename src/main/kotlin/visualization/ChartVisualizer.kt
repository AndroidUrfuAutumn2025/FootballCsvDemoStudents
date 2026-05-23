package visualization

import model.Player
import model.Team
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PiePlot
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.xy.DefaultXYDataset
import java.awt.Color
import javax.swing.JFrame

object ChartVisualizer {

    private val positionTranslations = mapOf(
        "DEFENDER" to "Защитник",
        "FORWARD" to "Нападающий",
        "MIDFIELD" to "Полузащитник",
        "GOALKEEPER" to "Вратарь"
    )
    fun showTopTeamsByTransferValue(teams: List<Team>) {
        val topTeams = teams.sortedByDescending { it.totalTransferValue }.take(10)

        val dataset = DefaultCategoryDataset()
        topTeams.forEach { team ->
            dataset.addValue(team.totalTransferValue.toDouble() / 1_000_000.0, "Стоимость (млн)", team.name)
        }

        val chart: JFreeChart = ChartFactory.createBarChart(
            "Топ-10 команд по суммарной трансферной стоимости",
            "Команда",
            "Стоимость (млн)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )

        val frame = JFrame("Топ-10 команд по трансферной стоимости")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(ChartPanel(chart))
        frame.pack()
        frame.isVisible = true
    }
}
