package graphics

import model.Player
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.points
import org.jetbrains.kotlinx.kandy.util.color.Color

object Plot {
    fun showDependenceOfGoalsScoredOnTransferCost(players: ArrayList<Player>, savePath: String) {
        val forwards = players.filter { it.position == "FORWARD" }
        val goalsScored = forwards.map { it.goals }
        val transferCosts = forwards.map { it.transferCost }
        val dataset = dataFrameOf(
            "goals" to goalsScored,
            "costs" to transferCosts
        )
        val plot = dataset.plot {
            points {
                x(transferCosts, "Transfer Costs")
                y(goalsScored, "Goals Scored")
                size = 2.5
                color = Color.GREEN
            }
            layout.title = "The dependence of scored goals on transfer costs"
            layout.size = 900 to 600
        }
        plot.save(savePath)
    }
}
