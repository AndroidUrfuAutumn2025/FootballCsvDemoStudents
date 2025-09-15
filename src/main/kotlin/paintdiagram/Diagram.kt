package paintdiagram

import model.Player
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.ChartUtils
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.ui.ApplicationFrame
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.io.File
import javax.swing.WindowConstants

fun plotWithJFreeChart(players: List<Player>) {
    val forwards = players.filter { it.position.equals("FORWARD", ignoreCase = true) }
    if (forwards.isEmpty()) {
        println("No forwards to plot")
        return
    }

    val series = XYSeries("Forwards")
    forwards.forEach { p ->
        series.add(p.transferValue.toDouble(), p.heads.toDouble())
    }
    val dataset = XYSeriesCollection()
    dataset.addSeries(series)

    val chart = ChartFactory.createScatterPlot(
        "Goals vs Transfer Cost for Forwards",
        "Transfer Cost",
        "Goals Scored",
        dataset,
        PlotOrientation.VERTICAL,
        true, true, false
    )

    val file = File("goals_vs_transfer_scatter.png")
    ChartUtils.saveChartAsPNG(file, chart, 800, 600)

    val frame = object : ApplicationFrame("Player Statistics") {}
    frame.contentPane = ChartPanel(chart)
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    frame.setSize(800, 600)
    frame.isVisible = true
}