package visualization

import model.Player
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.data.general.DefaultPieDataset
import java.io.File

object ChartBuilder {
    fun createNationalityPieChart(players: List<Player>, outputPath: String = "nationality_chart.png") {
        if (players.isEmpty()) {
            println("Нет данных для создания диаграммы")
            return
        }

        val nationalityCount = mutableMapOf<String, Int>()

        for (player in players) {
            val country = player.nationality
            // Пропускаем пустые названия стран
            if (country.isNotBlank()) {
                nationalityCount[country] = nationalityCount.getOrDefault(country, 0) + 1
            }
        }

        val dataset = DefaultPieDataset<String>()

        for ((country, count) in nationalityCount) {
            dataset.setValue("$country ($count)", count)
        }

        // Создаем круговую диаграмму
        val chart: JFreeChart = ChartFactory.createPieChart(
            "Распределение игроков по странам", // Заголовок
            dataset,          // Данные
            true,     // Показывать легенду
            true,    // Показывать подсказки
            false       // Не использовать URL
        )

        // Сохраняем в файл в папке проекта
        ChartUtils.saveChartAsPNG(File(outputPath), chart, 800, 600)
        println("Диаграмма сохранена в файл: $outputPath")
    }
}