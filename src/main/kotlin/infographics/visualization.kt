package infographics

import model.Player
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars


class Visualizer(private val players: List<Player>) {


    fun top10TeamsByTransferCost(outputFile: String = "top10_teams.png") {

        val teams = players
            .groupBy { it.team }
            .mapValues { (_, teamPlayers) -> teamPlayers.sumOf { it.transferValue ?: 0 } }
            .toList().sortedBy { (_, cost) -> cost }.take(10)

        val df = dataFrameOf("TeamName" to teams.map {it.first.name}, "SumOfTransfer" to teams.map {it.second})

        df.plot {
            layout {
                title ="Топ 10 команд по стоимости трансферов"
                size = 1000 to 1000
            }
            bars {
                x("TeamName")
                y("SumOfTransfer")
            }
        }.save(outputFile)
    }
}