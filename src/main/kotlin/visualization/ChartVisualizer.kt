package visualization

import model.Player
import model.Team
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PiePlot
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.xy.DefaultXYDataset
import java.awt.Color
import javax.swing.JFrame

object ChartVisualizer {
    
    private val positionTranslations = mapOf(
        "DEFENDER" to "Защитник",
        "FORWARD" to "Нападающий",
        "MIDFIELD" to "Полузащитник",
        "GOALKEEPER" to "Вратарь"
    )
    
    /**
     * Вариант 1: Показывает, какую долю от всех игроков занимают игроки каждой из позиций
     */
    fun showPositionDistribution(players: List<Player>) {
        val positionCounts = players.groupingBy { it.position }.eachCount()
        val totalPlayers = players.size
        
        val dataset = DefaultPieDataset<String>()
        positionCounts.forEach { (position, count) ->
            val russianPosition = positionTranslations[position] ?: position
            val percentage = (count.toDouble() / totalPlayers * 100)
            dataset.setValue("$russianPosition ($count игроков, ${String.format("%.1f", percentage)}%)", count)
        }
        
        val chart: JFreeChart = ChartFactory.createPieChart(
            "Распределение игроков по позициям",
            dataset,
            true,
            true,
            false
        )
        
        val plot = chart.plot as PiePlot<Comparable<*>>
        plot.backgroundPaint = Color.WHITE
        plot.labelBackgroundPaint = Color.WHITE
        
        val frame = JFrame("Распределение игроков по позициям")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(ChartPanel(chart))
        frame.pack()
        frame.isVisible = true
    }
    
    /**
     * Вариант 2: Топ-10 команд с наивысшей суммарной трансферной стоимостью
     */
    fun showTopTeamsByTransferValue(teams: List<Team>) {
        val topTeams = teams.sortedByDescending { it.totalTransferValue }.take(10)
        
        val dataset = DefaultCategoryDataset()
        topTeams.forEach { team ->
            dataset.addValue(team.totalTransferValue.toDouble() / 1_000_000.0, "Стоимость (млн)", team.name)
        }
        
        val chart: JFreeChart = ChartFactory.createBarChart(
            "Топ-10 команд по суммарной трансферной стоимости",
            "Команда",
            "Стоимость (млн)",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )
        
        val frame = JFrame("Топ-10 команд по трансферной стоимости")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(ChartPanel(chart))
        frame.pack()
        frame.isVisible = true
    }
    
    /**
     * Вариант 3: Зависимость количества забитых голов от трансферной стоимости для нападающих
     * Используется гистограмма с группировкой по диапазонам стоимости
     */
    fun showGoalsVsTransferCostForForwards(players: List<Player>) {
        val forwards = players.filter { it.position == "FORWARD" }
        
        if (forwards.isEmpty()) {
            println("Нет данных о нападающих для визуализации")
            return
        }
        
        // Группируем нападающих по диапазонам трансферной стоимости
        val costRanges = listOf(
            "0-20 млн" to (0L..20_000_000L),
            "20-40 млн" to (20_000_001L..40_000_000L),
            "40-60 млн" to (40_000_001L..60_000_000L),
            "60-80 млн" to (60_000_001L..80_000_000L),
            "80-100 млн" to (80_000_001L..100_000_000L),
            "100+ млн" to (100_000_001L..Long.MAX_VALUE)
        )
        
        val dataset = DefaultCategoryDataset()
        
        costRanges.forEach { (rangeName, range) ->
            val playersInRange = forwards.filter { it.transferCost in range }
            if (playersInRange.isNotEmpty()) {
                val avgGoals = playersInRange.map { it.goals }.average()
                val totalGoals = playersInRange.sumOf { it.goals }
                val count = playersInRange.size
                
                // Добавляем среднее количество голов и общее количество голов
                dataset.addValue(avgGoals, "Среднее количество голов", rangeName)
                dataset.addValue(totalGoals.toDouble(), "Общее количество голов", rangeName)
            }
        }
        
        val chart: JFreeChart = ChartFactory.createBarChart(
            "Зависимость количества забитых голов от трансферной стоимости (Нападающие)",
            "Диапазон трансферной стоимости",
            "Количество голов",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        )
        
        // Настройка цветов
        val plot = chart.categoryPlot
        plot.backgroundPaint = java.awt.Color.WHITE
        plot.rangeGridlinePaint = java.awt.Color.GRAY
        
        val renderer = plot.renderer as org.jfree.chart.renderer.category.BarRenderer
        renderer.setSeriesPaint(0, java.awt.Color(65, 105, 225)) // Синий для среднего
        renderer.setSeriesPaint(1, java.awt.Color(255, 140, 0))  // Оранжевый для общего
        
        val frame = JFrame("Зависимость голов от стоимости (Нападающие)")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(ChartPanel(chart))
        frame.pack()
        frame.isVisible = true
    }
    
    /**
     * Вариант 4: Доля игроков из разных стран
     */
    fun showPlayersByCountry(players: List<Player>) {
        val countryCounts = players.groupingBy { it.nationality }.eachCount()
        val totalPlayers = players.size
        
        // Берем топ-15 стран по количеству игроков
        val topCountries = countryCounts.toList().sortedByDescending { it.second }.take(15)
        
        val dataset = DefaultPieDataset<String>()
        topCountries.forEach { (country, count) ->
            val percentage = (count.toDouble() / totalPlayers * 100)
            dataset.setValue("$country ($count игроков, ${String.format("%.1f", percentage)}%)", count)
        }
        
        val chart: JFreeChart = ChartFactory.createPieChart(
            "Распределение игроков по странам (Топ-15)",
            dataset,
            true,
            true,
            false
        )
        
        val plot = chart.plot as PiePlot<Comparable<*>>
        plot.backgroundPaint = Color.WHITE
        plot.labelBackgroundPaint = Color.WHITE
        
        val frame = JFrame("Распределение игроков по странам")
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.add(ChartPanel(chart))
        frame.pack()
        frame.isVisible = true
    }
}

