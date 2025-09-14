package plot

import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartFrame
import org.jfree.data.category.DefaultCategoryDataset

object VisualizerJFreeChart {
    fun visualize(distribution: Map<String, Int>, totalPlayers: Int) {
        val dataset = DefaultCategoryDataset()

        // добавляем данные в график
        distribution.forEach { (nationality, count) ->
            val percent = (count.toDouble() / totalPlayers) * 100
            dataset.addValue(percent, "Игроки (%)", nationality)
        }

        val chart = ChartFactory.createBarChart(
            "Распределение игроков по странам",
            "Страна",
            "Процент игроков",
            dataset
        )

        // показываем график в отдельном окне
        val frame = ChartFrame("Распределение игроков по странам", chart)
        frame.pack()
        frame.isVisible = true
    }
}
