package visualizer

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PiePlot
import org.jfree.chart.title.TextTitle
import org.jfree.data.general.DefaultPieDataset
import java.awt.*
import java.io.File

class ChartVisualizer {
    fun createQualityPieChart(distribution: Map<String, Double>, outputPath: String = "player_positions_chart.png") {
        // Создаем dataset
        val dataset = DefaultPieDataset<String>()
        distribution.forEach { (position, percentage) ->
            dataset.setValue("$position (${"%.1f".format(percentage)}%)", percentage)
        }

        // Создаем круговую диаграмму без легенды (будем свою)
        val chart: JFreeChart = ChartFactory.createPieChart(
            null,  // без заголовка вверху
            dataset,
            false, // без легенды
            true,  // tooltips
            false
        )

        // Настраиваем plot
        val plot = chart.plot as PiePlot<*>

        // Красивые цвета
        plot.setSectionPaint("Defenders (28.4%)", Color(65, 105, 225))   // Royal Blue
        plot.setSectionPaint("Midfielders (24.4%)", Color(50, 205, 50))  // Lime Green
        plot.setSectionPaint("Forwards (22.0%)", Color(255, 99, 71))     // Tomato Red
        plot.setSectionPaint("Goalkeepers (25.2%)", Color(255, 215, 0))  // Gold

        // Убираем контуры
        plot.setOutlinePaint(null)

        // Процентные метки
        plot.setLabelFont(Font("Arial", Font.BOLD, 14))
        plot.setLabelPaint(Color.WHITE)
        plot.setLabelBackgroundPaint(null)
        plot.setLabelOutlinePaint(null)
        plot.setLabelShadowPaint(null)

        // Фон
        plot.setBackgroundPaint(Color.WHITE)
        chart.backgroundPaint = Color.WHITE

        // Добавляем красивый заголовок
        val title = TextTitle(
            "Distribution of Football Players by Position",
            Font("Arial", Font.BOLD, 20)
        )
        chart.title = title

        // Сохраняем в высоком качестве
        ChartUtils.saveChartAsPNG(File(outputPath), chart, 1000, 800)
        println("Quality pie chart saved to: $outputPath")
    }

    fun createSimplePieChart(distribution: Map<String, Double>, outputPath: String = "simple_pie_chart.png") {
        val dataset = DefaultPieDataset<String>()
        distribution.forEach { (position, percentage) ->
            dataset.setValue(position, percentage)
        }

        val chart: JFreeChart = ChartFactory.createPieChart(
            "Player Position Distribution",
            dataset,
            true,  // с легендой
            true,
            false
        )

        val plot = chart.plot as PiePlot<*>

        // Простые цвета
        plot.setSectionPaint("Defenders", Color.BLUE)
        plot.setSectionPaint("Midfielders", Color.GREEN)
        plot.setSectionPaint("Forwards", Color.RED)
        plot.setSectionPaint("Goalkeepers", Color.ORANGE)

        // Улучшаем читаемость
        plot.setLabelFont(Font("Arial", Font.PLAIN, 12))
        plot.setBackgroundPaint(Color.WHITE)

        ChartUtils.saveChartAsPNG(File(outputPath), chart, 800, 600)
        println("Simple pie chart saved to: $outputPath")
    }
}