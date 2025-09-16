package visualization

import model.Player
import model.Position
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.xy.DefaultXYDataset
import java.awt.Dimension
import javax.swing.JFrame

object ChartGenerator {

    // Вариант 1. Покажите, какую долю от всех игроков занимают игроки каждой из позиций (защитник, нападающий и тд).
    fun createPositionDistributionChart(players: List<Player>): JFreeChart {
        val positionCounts = players.groupBy { it.position }
            .mapValues { it.value.size }

        val dataset = DefaultPieDataset<String>()
        positionCounts.forEach { (position, count) ->
            dataset.setValue(position.russianName, count)
        }

        val chart = ChartFactory.createPieChart(
            "Распределение игроков по позициям",
            dataset,
            true,
            true,
            false
        )

        return chart
    }

    // Вариант 2: Топ-10 команд с наивысшей суммарной трансферной стоимостью
    fun createTopTeamsChart(players: List<Player>): JFreeChart {
        val teamCosts = players.groupBy { it.team }
            .mapValues { it.value.sumOf { player -> player.transferCost } }
            .toList()
            .sortedByDescending { it.second }
            .take(10)

        val dataset = DefaultCategoryDataset()
        teamCosts.forEach { (team, cost) ->
            dataset.addValue(cost.toDouble(), "Стоимость", team)
        }

        return ChartFactory.createBarChart(
            "Топ-10 команд по трансферной стоимости",
            "Команды",
            "Стоимость (у.е.)",
            dataset,
            PlotOrientation.VERTICAL,
            true, true, false
        )
    }

    // Вариант 3: Зависимость голов от трансферной стоимости для нападающих
    fun createGoalsVsCostChart(players: List<Player>): JFreeChart {
        val forwards = players.filter { it.position == Position.FORWARD }

        val dataset = DefaultXYDataset()
        val data = Array(2) { DoubleArray(forwards.size) }

        forwards.forEachIndexed { index, player ->
            data[0][index] = player.transferCost.toDouble()
            data[1][index] = player.goals.toDouble()
        }

        dataset.addSeries("Нападающие", data)

        return ChartFactory.createScatterPlot(
            "Зависимость голов от трансферной стоимости (Нападающие)",
            "Трансферная стоимость (у.е.)",
            "Количество голов",
            dataset
        )
    }

    // Вариант 4: Доля игроков из разных стран
    fun createNationalityChart(players: List<Player>): JFreeChart {
        val nationalityCounts = players.groupBy { it.nationality }
            .mapValues { it.value.size }

        val dataset = DefaultPieDataset<String>()
        nationalityCounts.forEach { (nationality, count) ->
            dataset.setValue(nationality, count)
        }

        return ChartFactory.createPieChart(
            "Распределение игроков по странам",
            dataset,
            true, true, false
        )
    }

    fun showChart(chart: JFreeChart) {
        val frame = JFrame("Визуализация данных футболистов")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

        val chartPanel = ChartPanel(chart)
        chartPanel.preferredSize = Dimension(800, 600)
        frame.contentPane = chartPanel

        frame.pack()
        frame.isVisible = true
    }
}
