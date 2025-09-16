package diagram

import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.layers.pie
import model.Player

class DiagramCreator(private val players: List<Player>) {
    fun saveCountryDistributionChart(
        outputPath: String = "CountryDistribution.png"
    ) {
        val countryCounts = players.groupingBy { it.nationality }
            .eachCount()
            .toList()

        val dataset = dataFrameOf(
            "country" to countryCounts.map { it.first },
            "count" to countryCounts.map { it.second }
        )

        dataset.plot {
            pie {
                slice("count")
                fillColor("country")
                size = 20.0
            }
        }.save(outputPath)
    }
}