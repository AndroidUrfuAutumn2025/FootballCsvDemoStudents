package visualization

import resolver.Resolver
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PiePlot
import org.jfree.data.general.DefaultPieDataset
import java.awt.BasicStroke
import java.awt.Color
import java.io.File

/**
 * Визуализатор данных для создания графиков и диаграмм
 */
class Visualizer(private val resolver: Resolver) {

    /**
     * Создает круговую диаграмму распределения игроков по национальностям
     */
    fun createNationalityPieChart(outputPath: String = "nationality_chart.png") {
        val distribution = resolver.getNationalityDistribution()

        val topNationalities = distribution.toList()
            .sortedByDescending { it.second }
            .take(10)

        val dataset = DefaultPieDataset<String>()

        topNationalities.forEach { (nationality, count) ->
            dataset.setValue("$nationality ($count)", count)
        }

        val othersCount = distribution.values.sum() - topNationalities.sumOf { it.second }
        if (othersCount > 0) {
            dataset.setValue("Другие ($othersCount)", othersCount)
        }

        val chart: JFreeChart = ChartFactory.createPieChart(
            "Распределение игроков по национальностям",
            dataset,
            true,
            true,
            false
        )

        val plot = chart.plot as PiePlot<String>
        plot.backgroundPaint = Color.WHITE
        plot.outlineStroke = BasicStroke(0f)
        plot.labelBackgroundPaint = Color.WHITE
        plot.shadowPaint = null

        ChartUtils.saveChartAsPNG(File(outputPath), chart, 800, 600)

        println("Диаграмма сохранена в файл: $outputPath")
    }

    /**
     * Выводит в консоль распределение игроков по национальностям
     */
    fun printNationalityDistribution() {
        val distribution = resolver.getNationalityDistribution()

        println("\n=== РАСПРЕДЕЛЕНИЕ ИГРОКОВ ПО НАЦИОНАЛЬНОСТЯМ ===")
        println("Всего национальностей: ${distribution.size}")
        println("Всего игроков: ${distribution.values.sum()}")
        println("\nТоп-10 национальностей:")

        distribution.toList()
            .sortedByDescending { it.second }
            .take(10)
            .forEachIndexed { index, (nationality, count) ->
                val percentage = "%.1f".format(count * 100.0 / distribution.values.sum())
                println("${index + 1}. $nationality: $count игроков ($percentage%)")
            }
    }
}
