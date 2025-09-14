package visualization

import model.Player
import java.io.File

import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars

object Visualizer {

    fun plotTop10TeamsByTransferCost(players: List<Player>, outFileName: String = "top10.png") {
        if (players.isEmpty()) {
            println("Сингулярность")
            return
        }

        val teamSums: List<Pair<String, Long>> = players
            .groupBy { it.teamName }
            .mapValues { (_, members) -> members.sumOf { it.transferCost } }
            .map { (team, sum) -> team to sum }
            .sortedByDescending { it.second }
            .take(10)


        val teams = teamSums.map { it.first }
        val costs = teamSums.map { it.second }

        val dataset: Map<String, List<*>> = mapOf(
            "team" to teams,
            "cost" to costs
        )

        val chart = plot(dataset) {
            bars {
                x("cost")
                y("team")
                alpha = 0.9
                width = 0.8
            }

            layout {
                title = "Top-10 команд по суммарной трансферной стоимости"
                size = 1000 to 600
            }
        }

        try {
            val path = chart.save(outFileName)
            println("График сохранён: $path")
            println("Если картинка не отображается в IDE, откройте файл вручную.")
        } catch (ex: Throwable) {
            println("Ошибка при сохранении графика: ${ex.message}")
            ex.printStackTrace()
        }
    }
}
