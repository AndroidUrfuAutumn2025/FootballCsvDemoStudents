package resolver

import model.Player
import model.PlayerPosition
import model.Team
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.pie
import org.jetbrains.kotlinx.kandy.letsplot.scales.BrewerPalette
import org.jetbrains.kotlinx.kandy.letsplot.scales.categoricalColorBrewer
import org.jetbrains.kotlinx.kandy.letsplot.style.Style

class Resolver(val playersList: List<Player>): IResolver {
    override fun getCountWithoutAgency(): Int {
        return playersList.count { it.agency.isNullOrBlank() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val bestDefender = playersList.filter { it.position == PlayerPosition.DEFENDER }
            .maxBy { it.goals }
        return Pair( bestDefender.name, bestDefender.goals)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        return positionTranslation(playersList.filter { it.nationality == "Germany" }
            .maxBy { it.transferCost })
    }

    override fun getTheRudestTeam(): Team {
        val teamMap = playersList.groupBy { it.team.name }
        lateinit var rudestTeam: Team
        var maxRedCardsByPlayer = 0F
        for (team in teamMap) {
            val redCardsByPlayer: Float = team.value.sumOf { it.redCards }.toFloat() / team.value.count()
            if (redCardsByPlayer > maxRedCardsByPlayer)
            {
                maxRedCardsByPlayer = redCardsByPlayer
                rudestTeam = team.value.first().team
            }
        }
        return rudestTeam
    }

    fun showPositionProportion() {
        val positionsMap = playersList.groupBy { it.position }
        val data = mapOf(
            "position" to positionsMap.keys.toList(),
            "players count" to positionsMap.values.toList().map { it -> it.count() }//listOf(positionsMap.values.forEach { it -> it.count() })
        )
        data.plot {
            pie {
                slice("players count")
                fillColor("position") {
                    scale = categoricalColorBrewer(BrewerPalette.Qualitative.Set1)
                }
                size = 25.0
            }
            layout {
                style(Style.Void)
            }
        }.save("/home/artem/StudioProjects/FootballCsvDemoStudents/src/main/resources/plot.jpg")
    }
}

    fun positionTranslation(player: Player): String {
        return when(player.position) {
            PlayerPosition.DEFENDER -> "ЗАЩИТНИК"
            PlayerPosition.FORWARD -> "НАПАДАЮЩИЙ"
            PlayerPosition.MIDFIELD -> "ПОЛУЗАЩИТНИК"
            PlayerPosition.GOALKEEPER -> "ВРАТАРЬ"
            else -> "НЕИЗВЕСТНАЯ ПОЗИЦИЯ"
        }
    }
