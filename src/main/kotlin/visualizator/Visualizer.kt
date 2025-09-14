package visualizator

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.labels.StandardPieSectionLabelGenerator
import org.jfree.chart.plot.PiePlot
import org.jfree.data.general.DefaultPieDataset
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities

class Visualizer {
    fun drawHighestTransferCostTeams(topTeamsAndCosts : List<Pair<String, Double>>) {
        val dataset = DefaultPieDataset<String>()
        topTeamsAndCosts.forEach { (teamName, cost) ->
            dataset.setValue(teamName, cost.toDouble())
        }

        val chart: JFreeChart = ChartFactory.createPieChart(
            "Топ-10 команд по трансферной стоимости",
            dataset,
            false,
            true,
            false
        )

        val plot = chart.plot as PiePlot<*>
        plot.labelGenerator = StandardPieSectionLabelGenerator("{0}: {1} € ({2})")


        SwingUtilities.invokeLater {
            val frame = JFrame("Трансферная стоимость команд")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.contentPane.add(ChartPanel(chart).apply {
                preferredSize = Dimension(800, 600)
            })
            frame.pack()
            frame.setLocationRelativeTo(null)
            frame.isVisible = true
        }
    }
}