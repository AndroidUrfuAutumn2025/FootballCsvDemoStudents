import model.Team
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars

class KandyPlot {
    fun main(teams: List<Team>) {
        val sortedTeams = teams.sortedByDescending { team ->
            team.players.sumOf { it.transferCost?: 0 }
        }
        val teamNames = sortedTeams.map { it.name }
        val transferCosts = sortedTeams.map { team ->
            team.players.sumOf{ it.transferCost?: 0 }
        }

        val df = dataFrameOf(
            "teams" to teamNames,
            "transfer_cost" to transferCosts
        )

        val plot = plot(df) {
            layout {
                title = "The most expensive teams"
            }

            bars {
                x("teams")
                y("transfer_cost")
            }
        }

        plot.save("The most Expensive Teams.png")
    }
}