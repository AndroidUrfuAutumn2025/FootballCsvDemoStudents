import model.Player
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.data.general.DefaultPieDataset
import java.io.File

class DiagramCreator {

    fun createChart(players: List<Player>) {
        val countryMap = mutableMapOf<String, Int>()
        for (player in players) {
            countryMap[player.nationality] = countryMap.getOrDefault(player.nationality, 0) + 1
        }

        val topCountries = countryMap.toList().sortedByDescending { it.second }

        val dataset = DefaultPieDataset<String>()
        topCountries.forEach { (country, count) ->
            dataset.setValue("$country ($count)", count)
        }

        val chart = ChartFactory.createPieChart(
            "Доля игроков по странам",
            dataset,
            true, true, false
        )

        ChartUtils.saveChartAsPNG(File("players_chart.png"), chart, 800, 600)
    }
}