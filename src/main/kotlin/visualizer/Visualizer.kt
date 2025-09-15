package visualizer

import model.Player
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.data.general.DefaultPieDataset
import java.io.File

object ChartGenerator {

    fun createPositionPieChart(players: List<Player>, outputPath: String) {
        val dataset = DefaultPieDataset<String>()

        val grouped = players.groupingBy { it.position }.eachCount()
        val total = players.size.toDouble()

        grouped.forEach { (position, count) ->
            val share = count / total * 100
            dataset.setValue(position, share)
        }

        val chart = ChartFactory.createPieChart(
            "Распределение игроков по позициям",
            dataset,
            true,
            true,
            false
        )

        ChartUtils.saveChartAsPNG(File(outputPath), chart, 1600, 900)
    }
}
