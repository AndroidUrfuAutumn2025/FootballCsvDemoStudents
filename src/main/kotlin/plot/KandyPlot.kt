import model.Team
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars
import org.jetbrains.kotlinx.kandy.letsplot.layers.points

class KandyPlot {
    fun main(teams: List<Team>) {
        val forward_players = teams.flatMap { it.players }
            .filter { it.position == "FORWARD" }
        val transferCosts = forward_players.map { it.transfer_cost }
        val goals = forward_players.map { it.goals }

        val df = dataFrameOf(
            "transfer_cost" to transferCosts,
            "goals" to goals
        )

        val plot = plot(df) {
            layout {
                title = " relationship between the number of goals scored and the transfer value of forwards"
            }

            points {
                x("goals")
                y("transfer_cost")
            }
        }

        plot.save("Var2.png")
    }
}