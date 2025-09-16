package visualisation

import model.Player
import model.Position
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.DefaultXYDataset
import javax.swing.JFrame


object ChartService {
    fun createGoalsVsTransferCostChart(players: List<Player>): JFreeChart {
        val forwardsData = players
            .filter { it.position == Position.FORWARD }
        val dataset = DefaultXYDataset()
        val data = Array(2) { DoubleArray(forwardsData.size) }

        forwardsData
            .forEachIndexed { index, player ->
                data[0][index] = player.transferCost.toDouble()
                data[1][index] = player.goals.toDouble()
            }


        dataset.addSeries("Нападающие", data)

        return ChartFactory.createScatterPlot(
            "Зависимость голов от стоимости",
            "Трансферная стоимость",
            "Количество голов",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        )
    }

    fun showChart(chart: JFreeChart) {
        val frame = JFrame("Зависимость голов от стоимости")
        frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        frame.contentPane.add(ChartPanel(chart))
        frame.pack()
        frame.isVisible = true
    }
}