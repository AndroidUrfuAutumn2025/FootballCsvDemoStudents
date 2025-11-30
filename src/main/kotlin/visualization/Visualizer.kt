package visualization

import model.Player
import org.jetbrains.kotlinx.kandy.dsl.*
import org.jetbrains.kotlinx.kandy.letsplot.*
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars
import org.jetbrains.kotlinx.kandy.util.color.Color

object Visualizer {

    fun visualizePositions(players: List<Player>) {
        val positionCounts = players.groupingBy { it.position }.eachCount()

        val positions = positionCounts.keys.toList()
        val counts = positionCounts.values.toList()

        val myPlot = plot {
            x(positions) { axis.name = "Позиция" }
            y(counts) { axis.name = "Количество игроков" }

            bars {
                fillColor = Color.BLUE
                alpha = 0.7
            }

            layout {
                title = "Распределение игроков по позициям"
                size = 800 to 600
            }
        }

        val fileName = "positions_chart.png"

        myPlot.save(fileName, scale = 2.0)

        println("График успешно сохранен в файл: $fileName")
    }
}