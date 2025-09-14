package vizualization

import model.PositionData
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars

object BarChart {

    fun visualizePositionDistribution(positionData: List<PositionData>) {
        val dataFrame = dataFrameOf(
            "Позиция" to positionData.map { it.positionName },
            "Количество" to positionData.map { it.count },
            "Процент" to positionData.map { it.percentage }
        )

        dataFrame.plot {
            bars {
                x("Позиция")
                y("Количество") {
                    axis.name = "Количество игроков"
                }
                alpha = 0.7
            }

            layout {
                title = "Распределение игроков по позициям"
                xAxisLabel = "Позиции"
            }
        }.save("player_positions.png")
    }
}