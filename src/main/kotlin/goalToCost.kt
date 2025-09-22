import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.data.statistics.Regression
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Dimension
import javax.swing.JFrame

object goalToCost {

    fun visualize(goalToCost: List<Pair<Int, Long>>) {
        try {
            if (goalToCost.isEmpty()) {
                println("Нет данных для визуализации")
                return
            }

            val sortedData = goalToCost.sortedBy { it.first }

            val mainSeries = XYSeries("Игроки")
            sortedData.forEach { (goals, cost) ->
                mainSeries.add(goals.toDouble(), cost.toDouble())
            }

            val trendSeries = XYSeries("Линия тренда")
            val regressionParams = Regression.getPowerRegression(XYSeriesCollection(mainSeries), 0)

            val minGoals = sortedData.minOf { it.first }
            val maxGoals = sortedData.maxOf { it.first }

            for (goals in minGoals..maxGoals step 1) {
                val predictedCost = regressionParams[0] * Math.pow(goals.toDouble(), regressionParams[1])
                trendSeries.add(goals.toDouble(), predictedCost)
            }

            val dataset = XYSeriesCollection().apply {
                addSeries(mainSeries)
                addSeries(trendSeries)
            }

            val chart: JFreeChart = ChartFactory.createScatterPlot(
                "📊 Зависимость стоимости игрока от количества голов",
                "⚽ Количество голов",
                "💰 Стоимость (\$)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
            )

            val plot = chart.xyPlot
            plot.backgroundPaint = Color.WHITE
            plot.isDomainGridlinesVisible = true
            plot.isRangeGridlinesVisible = true
            plot.domainGridlinePaint = Color.LIGHT_GRAY
            plot.rangeGridlinePaint = Color.LIGHT_GRAY

            val renderer = XYLineAndShapeRenderer(true, true)
            renderer.setSeriesPaint(0, Color.BLUE)
            renderer.setSeriesPaint(1, Color.RED)
            renderer.setSeriesStroke(1, BasicStroke(2.0f))
            renderer.setSeriesShapesVisible(1, false)
            renderer.setSeriesShape(0, java.awt.geom.Ellipse2D.Double(-3.0, -3.0, 6.0, 6.0))
            plot.renderer = renderer

            val rangeAxis = plot.rangeAxis as NumberAxis
            rangeAxis.numberFormatOverride = java.text.NumberFormat.getInstance().apply {
                maximumFractionDigits = 0
            }

            val frame = JFrame("⚽ Анализ стоимости форвардов").apply {
                defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
                contentPane.add(ChartPanel(chart).apply {
                    preferredSize = Dimension(800, 600)
                })
                pack()
                setLocationRelativeTo(null)
                isVisible = true
            }
        } catch (e: Exception) {
            println("Ошибка при визуализации: ${e.message}")
            e.printStackTrace()
        }
    }
}