package visualization

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.xy.DefaultXYDataset
import java.io.File

object ChartBuilder {

    fun createPositionPieChart(data: Map<String, Double>, title: String): JFreeChart {
        val dataset = DefaultPieDataset<String>()
        data.forEach { (position, percentage) ->
            dataset.setValue("$position (${"%.1f".format(percentage)}%)", percentage)
        }

        return ChartFactory.createPieChart(
            title,
            dataset,
            true,
            true,
            false
        )
    }

    fun createTeamValueBarChart(data: List<Pair<String, Long>>, title: String): JFreeChart {
        val dataset = DefaultCategoryDataset()

        data.forEachIndexed { index, (team, value) ->
            dataset.addValue(value / 1_000_000.0, "Стоимость", team)
        }

        return ChartFactory.createBarChart(
            title,
            "Команды",
            "Трансферная стоимость (млн €)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )
    }

    fun createGoalsVsCostScatterPlot(data: List<Triple<String, Int, Int>>, title: String): JFreeChart {
        val dataset = DefaultXYDataset()
        val values = Array(2) { DoubleArray(data.size) }

        data.forEachIndexed { index, (_, cost, goals) ->
            values[0][index] = cost / 1_000_000.0
            values[1][index] = goals.toDouble()
        }

        dataset.addSeries("Нападающие", values)

        return ChartFactory.createScatterPlot(
            title,
            "Трансферная стоимость (млн €)",
            "Количество голов",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )
    }

    fun createNationalityBarChart(data: Map<String, Double>, title: String): JFreeChart {
        val dataset = DefaultCategoryDataset()
        val top10 = data.entries.sortedByDescending { it.value }.take(10)

        top10.forEach { (country, percentage) ->
            dataset.addValue(percentage, "Доля", country)
        }

        return ChartFactory.createBarChart(
            title,
            "Страны",
            "Доля игроков (%)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )
    }

    fun saveChart(chart: JFreeChart, filename: String, width: Int = 800, height: Int = 600) {
        val outputFile = File("charts/$filename")
        outputFile.parentFile.mkdirs()

        ChartUtils.saveChartAsPNG(outputFile, chart, width, height)
        println("График сохранен: ${outputFile.absolutePath}")
    }

    fun saveAllCharts(resolver: resolver.Resolver) {
        println("Создание графиков...")

        val positionData = resolver.getPositionDistribution()
        val positionChart = createPositionPieChart(positionData, "Распределение игроков по позициям")
        saveChart(positionChart, "position_distribution.png")

        val teamValueData = resolver.getTopTeamsByTransferValue()
        val teamValueChart = createTeamValueBarChart(teamValueData, "Топ команд по трансферной стоимости")
        saveChart(teamValueChart, "team_values.png")

        val goalsCostData = resolver.getGoalsVsCostForForwards()
        val goalsCostChart = createGoalsVsCostScatterPlot(goalsCostData, "Зависимость голов от стоимости (нападающие)")
        saveChart(goalsCostChart, "goals_vs_cost.png")

        val nationalityData = resolver.getNationalityDistribution()
        val nationalityChart = createNationalityBarChart(nationalityData, "Распределение игроков по странам (топ-10)")
        saveChart(nationalityChart, "nationality_distribution.png")

        println("Все графики сохранены в папке charts/")
    }
}