package chart

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.chart.ChartUtils
import java.awt.Dimension
import javax.swing.JFrame
import java.io.File

fun createTopTeamsDataset(topTeams: Map<String, Double>, inMillions: Boolean = true): DefaultCategoryDataset {
    val ds = DefaultCategoryDataset()
    for ((team, value) in topTeams) {
        val v = if (inMillions) value / 1_000_000.0 else value
        ds.addValue(v, "Value", team)
    }
    return ds
}

fun createBarChart(dataset: DefaultCategoryDataset, title: String, valueLabel: String = "Млн €"): JFreeChart {
    return ChartFactory.createBarChart(title, "Team", valueLabel, dataset)
}

fun showChartInWindow(chart: JFreeChart, title: String = "Chart", width: Int = 1000, height: Int = 600) {
    val frame = JFrame(title)
    val panel = ChartPanel(chart)
    panel.preferredSize = Dimension(width, height)
    frame.contentPane.add(panel)
    frame.pack()
    frame.setLocationRelativeTo(null)
    frame.isVisible = true
}

fun saveChartAsPng(chart: JFreeChart, file: File, w: Int = 1200, h: Int = 800) {
    ChartUtils.saveChartAsPNG(file, chart, w, h)
}
