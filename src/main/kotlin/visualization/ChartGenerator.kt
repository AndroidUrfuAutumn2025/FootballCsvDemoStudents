package visualization

import model.Player
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PiePlot
import org.jfree.data.general.DefaultPieDataset
import java.awt.Color
import java.awt.Dimension
import javax.swing.JFrame
import javax.swing.SwingUtilities

class ChartGenerator {
    
    fun createPositionDistributionChart(players: List<Player>): JFreeChart {
        val positionCounts = players.groupingBy { it.position }.eachCount()
        
        val dataset = DefaultPieDataset<String>()
        
        positionCounts.forEach { (position, count) ->
            val russianPosition = positionToRussian(position)
            dataset.setValue(russianPosition, count)
        }
        
        val chart = ChartFactory.createPieChart(
            "Распределение игроков по позициям",
            dataset,
            true,
            true,
            false
        )
        
        val plot = chart.plot as PiePlot<*>
        plot.setBackgroundPaint(Color.WHITE)
        plot.setOutlineVisible(false)
        
        plot.setLabelGenerator(org.jfree.chart.labels.StandardPieSectionLabelGenerator("{0} ({2})"))
        
        plot.setLabelFont(java.awt.Font("Arial", java.awt.Font.BOLD, 12))
        plot.setLabelBackgroundPaint(Color.WHITE)
        plot.setLabelOutlinePaint(Color.BLACK)
        plot.setLabelOutlineStroke(java.awt.BasicStroke(1.0f))
        plot.setLabelShadowPaint(Color.LIGHT_GRAY)
        
        val colors = arrayOf(
            Color(255, 99, 132),
            Color(54, 162, 235),
            Color(255, 205, 86),
            Color(75, 192, 192),
            Color(153, 102, 255),
            Color(255, 159, 64)
        )
        
        var colorIndex = 0
        positionCounts.keys.forEach { position ->
            val russianPosition = positionToRussian(position)
            plot.setSectionPaint(russianPosition, colors[colorIndex % colors.size])
            colorIndex++
        }
        
        return chart
    }
    
    fun showChart(chart: JFreeChart) {
        SwingUtilities.invokeLater {
            val frame = JFrame("Футбольная статистика")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            
            val chartPanel = ChartPanel(chart)
            chartPanel.preferredSize = Dimension(800, 600)
            chartPanel.setMouseWheelEnabled(true)
            
            frame.contentPane.add(chartPanel)
            frame.pack()
            frame.setLocationRelativeTo(null)
            frame.isVisible = true
        }
    }
    
    private fun positionToRussian(position: String): String = when (position.uppercase()) {
        "DEFENDER" -> "Защитник"
        "FORWARD" -> "Нападающий"
        "MIDFIELD" -> "Полузащитник"
        "GOALKEEPER" -> "Вратарь"
        else -> position
    }
}
