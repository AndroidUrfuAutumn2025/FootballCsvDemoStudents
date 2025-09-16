package visualization

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.StandardChartTheme
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.*
import java.awt.geom.Ellipse2D
import javax.swing.JFrame

object ChartGenerator {
    
    fun createGoalsVsTransferCostChart(data: List<Pair<Long, Int>>): JFreeChart {
        val series = XYSeries("Нападающие")
        
        // Сортируем данные по трансферной стоимости для красивой линии
        val sortedData = data.sortedBy { it.first }
        
        sortedData.forEach { (transferCost, goals) ->
            series.add(transferCost.toDouble() / 1_000_000.0, goals.toDouble())
        }
        
        val dataset = XYSeriesCollection(series)
        
        val chart = ChartFactory.createXYLineChart(
            "Зависимость количества голов от трансферной стоимости (Нападающие)",
            "Трансферная стоимость (млн)",
            "Количество голов",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )
        
        // Простое оформление
        val plot = chart.xyPlot
        plot.backgroundPaint = Color.WHITE
        plot.domainGridlinePaint = Color.LIGHT_GRAY
        plot.rangeGridlinePaint = Color.LIGHT_GRAY
        
        val renderer = XYLineAndShapeRenderer(true, false)
        renderer.setSeriesPaint(0, Color(70, 130, 180))
        renderer.setSeriesStroke(0, BasicStroke(2.0f))
        plot.renderer = renderer
        
        return chart
    }
    
    fun showChart(chart: JFreeChart) {
        val frame = JFrame("Анализ футбольных данных")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        
        val chartPanel = ChartPanel(chart)
        chartPanel.preferredSize = Dimension(800, 600)
        
        frame.contentPane = chartPanel
        frame.pack()
        frame.isVisible = true
    }
    
    fun saveChartAsPNG(chart: JFreeChart, filename: String) {
        val file = java.io.File(filename)
        org.jfree.chart.ChartUtils.saveChartAsPNG(file, chart, 800, 600)
    }
}
