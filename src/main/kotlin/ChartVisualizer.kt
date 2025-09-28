package visualization

import model.Player
import model.Team
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.general.DefaultPieDataset
import java.io.File

class ChartVisualizer {

    // Создаем папку charts если не существует
    private fun ensureChartsDirectory() {
        val chartsDir = File("charts")
        if (!chartsDir.exists()) {
            chartsDir.mkdirs()
        }
    }

    // Вариант 1: Доля позиций игроков
    fun createPositionDistributionChart(players: List<Player>) {
        ensureChartsDirectory()

        val positionCounts = players.groupBy { it.position }
            .mapValues { it.value.size }

        val dataset = DefaultPieDataset<String>()
        positionCounts.forEach { (position, count) ->
            dataset.setValue("$position ($count)", count)
        }

        val chart: JFreeChart = ChartFactory.createPieChart(
            "Распределение игроков по позициям",
            dataset,
            true,
            true,
            false
        )

        ChartUtils.saveChartAsPNG(File("charts/position_distribution.png"), chart, 800, 600)
        println("✓ Создана круговая диаграмма распределения позиций")
    }

    // Вариант 2: Топ-10 команд по трансферной стоимости
    fun createTopTeamsChart(topTeams: List<Pair<Team, Long>>) {
        ensureChartsDirectory()

        val dataset = DefaultCategoryDataset()
        topTeams.forEachIndexed { index, (team, value) ->
            val teamLabel = "${team.name}\n(${team.city})"
            dataset.addValue(value / 1_000_000.0, "Стоимость", teamLabel)
        }

        val chart: JFreeChart = ChartFactory.createBarChart(
            "Топ-10 команд по трансферной стоимости",
            "Команды",
            "Стоимость (млн €)",
            dataset
        )

        ChartUtils.saveChartAsPNG(File("charts/top_teams_value.png"), chart, 1000, 600)
        println("✓ Создана столбчатая диаграмма топ-10 команд")
    }

    // Вариант 3: Зависимость голов от стоимости для нападающих
    fun createGoalsVsCostChart(players: List<Player>) {
        ensureChartsDirectory()

        val forwards = players.filter {
            it.position.equals("FORWARD", ignoreCase = true) ||
                    it.position.equals("STRIKER", ignoreCase = true)
        }

        val dataset = DefaultCategoryDataset()
        forwards.forEachIndexed { index, player ->
            dataset.addValue(player.transferCost / 1_000_000.0, "Стоимость", "${player.name}\n(${player.goals} голов)")
        }

        val chart: JFreeChart = ChartFactory.createBarChart(
            "Трансферная стоимость нападающих в зависимости от голов",
            "Нападающие (количество голов)",
            "Трансферная стоимость (млн €)",
            dataset
        )

        ChartUtils.saveChartAsPNG(File("charts/goals_vs_cost.png"), chart, 1200, 600)
        println("✓ Создана диаграмма зависимости голов от стоимости")
    }

    // Вариант 4: Доля игроков по странам
    fun createNationalityDistributionChart(players: List<Player>) {
        ensureChartsDirectory()

        val nationalityCounts = players.groupBy { it.nationality }
            .mapValues { it.value.size }
            .toList()
            .sortedByDescending { it.second }
            .take(10) // Топ-10 стран
            .toMap()

        val dataset = DefaultPieDataset<String>()
        nationalityCounts.forEach { (country, count) ->
            dataset.setValue("$country ($count)", count)
        }

        val chart: JFreeChart = ChartFactory.createPieChart(
            "Распределение игроков по странам (Топ-10)",
            dataset,
            true,
            true,
            false
        )

        ChartUtils.saveChartAsPNG(File("charts/nationality_distribution.png"), chart, 800, 600)
        println("✓ Создана круговая диаграмма распределения по странам")
    }
}