package graphics

import model.Player
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.pie
import org.jetbrains.kotlinx.kandy.letsplot.scales.BrewerPalette
import org.jetbrains.kotlinx.kandy.letsplot.scales.categoricalColorBrewer
import org.jetbrains.kotlinx.kandy.letsplot.style.Style
import org.jetbrains.kotlinx.kandy.util.color.Color

object Plot {
    fun showPlayerSharesByPosition(players: ArrayList<Player>, savePath: String) {
        val goalkeepersNumber = players.count { it.position == "GOALKEEPER" }
        val forwardsNumber = players.count { it.position == "FORWARD" }
        val defendersNumber = players.count { it.position == "DEFENDER" }
        val midfieldersNumber = players.count { it.position == "MIDFIELD" }
        val dataset = dataFrameOf(
            "position" to listOf("goalkeeper", "forward", "defender", "midfielder"),
            "count" to listOf(goalkeepersNumber, forwardsNumber, defendersNumber, midfieldersNumber)
        )
        val plot = dataset.plot {
            pie {
                slice("count")
                fillColor("position") {
                    scale = categoricalColorBrewer(BrewerPalette.Qualitative.Set1)
                }
                size = 20.0
                stroke = 1.0
                strokeColor = Color.WHITE
                hole = 0.5
            }
            layout {
                style(Style.Void)
            }
        }
        plot.save(savePath)
    }
}
