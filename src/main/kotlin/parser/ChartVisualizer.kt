package parser

import model.Player
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartFrame
import org.jfree.chart.JFreeChart
import org.jfree.data.general.DefaultPieDataset
import java.awt.Dimension

object ChartVisualizer {

    // Общая функция для отображения графика
    fun showChart(chart: JFreeChart, title: String) {
        val frame = ChartFrame(title, chart)
        frame.preferredSize = Dimension(800, 600)
        frame.pack()
        frame.isVisible = true
    }
    // Вариант 4: Доля игроков по странам
    fun showNationalityDistribution(players: List<Player>) {
        val dataset = DefaultPieDataset<String>()

        val countryCount = players.groupingBy { it.nationality }.eachCount()
        val topCountries = countryCount.toList()
            .sortedByDescending { it.second }
            .take(10)

        topCountries.forEach { (country, count) ->
            val displayName = country.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase() else it.toString()
            }
            dataset.setValue(displayName, count.toDouble())
        }

        val otherCount = countryCount.values.sum() - topCountries.sumOf { it.second }
        if (otherCount > 0) {
            dataset.setValue("Другие", otherCount.toDouble())
        }

        val chart = ChartFactory.createPieChart(
            "Распределение игроков по странам (топ-10)",
            dataset,
            true, true, false
        )

        showChart(chart, "Распределение по странам")
    }
}
