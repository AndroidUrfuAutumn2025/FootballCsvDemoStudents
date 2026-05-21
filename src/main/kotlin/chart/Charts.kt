package chart

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.ChartUtils
import org.jfree.data.general.DefaultPieDataset
import java.awt.Dimension
import javax.swing.JFrame
import java.io.File

fun showCountryPieChart(data: Map<String, Double>) {
    val dataset = DefaultPieDataset<String>()
    data.forEach { (country, percent) ->
        dataset.setValue(country, percent)
    }

    val chart = ChartFactory.createPieChart(
        "Доля игроков по национальностям",
        dataset,
        true, true, false
    )

    val frame = JFrame("Национальности")
    val panel = ChartPanel(chart)
    panel.preferredSize = Dimension(800, 600)
    frame.contentPane.add(panel)
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    frame.isVisible = true
}