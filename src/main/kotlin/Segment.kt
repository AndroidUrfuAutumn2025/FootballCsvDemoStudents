import model.Player
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.data.general.DefaultPieDataset
import java.io.File

class Segment {
    fun showCountryDistribution(players: List<Player>) {
        val countryDistribution = players
            .groupBy { it.nationality }
            .map { (country, playersInCountry) -> country to playersInCountry.size }

        val dataset = DefaultPieDataset<String>()
        countryDistribution.forEach { (country, count) ->
            dataset.setValue("$country ($count)", count)
        }

        val chart = ChartFactory.createPieChart(
            "Доля игроков по странам",
            dataset,
            true,
            true,
            false
        )

        ChartUtils.saveChartAsPNG(File("country_distribution.png"), chart, 800, 600)
        println("Круговая диаграмма сохранена в файл 'country_distribution.png'")
    }
}