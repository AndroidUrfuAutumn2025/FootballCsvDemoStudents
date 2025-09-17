package plot
import model.Player
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.pie
import org.jetbrains.kotlinx.kandy.letsplot.style.Style

class KandyPlot {
    fun showCountryDistribution(players: List<Player>) {
        val countryDistribution = players
            .groupBy { it.nationality }
            .map { (country, playersInCountry) -> country to playersInCountry.size}
        val df = dataFrameOf(
            "country" to countryDistribution.map { it.first },
            "count" to countryDistribution.map { it.second }
        )
        val plot = plot(df) {
            layout {
                title = "Доля игроков по странам"
                style( Style.Void )
            }
            pie {
                slice("count")
                fillColor("country")
                size = 33.0
                hole = 0.8
                alpha = 0.8
            }

        }
        plot.save("country_distribution.png")
        println("Круговая диаграмма сохранена в файл 'country_distribution.png'")
    }
}