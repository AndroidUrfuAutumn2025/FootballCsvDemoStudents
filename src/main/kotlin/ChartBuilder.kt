package utils

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.category.DefaultCategoryDataset
import model.Team
import java.io.File

object ChartBuilder {

    fun createTopTeamsChart(topTeams: List<Pair<Team, Double>>, filename: String = "top_teams_chart.png") {
        // 1. Создаем и наполняем dataset
        val dataset = DefaultCategoryDataset()
        for ((team, cost) in topTeams) {
            dataset.addValue(cost / 1_000_000.0, "Стоимость (в млн \$)", team.name)
        }

        // 2. Создаем объект диаграммы
        val chart: JFreeChart = ChartFactory.createBarChart(
            "Топ-10 команд по суммарной трансферной стоимости",
            "Команды",
            "Суммарная стоимость (в млн \$)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )

        // 3. Сохраняем диаграмму в файл
        val outputFile = File(filename)
        ChartUtils.saveChartAsPNG(outputFile, chart, 800, 600)

        println("Диаграмма сохранена в файл: ${outputFile.absolutePath}")
    }
}