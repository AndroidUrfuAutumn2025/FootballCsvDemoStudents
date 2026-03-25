package chart

import model.Player
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.plot.PiePlot
import org.jfree.data.general.DefaultPieDataset
import java.awt.Color
import java.awt.Font
import javax.swing.JFrame
import javax.swing.SwingUtilities

class PositionChartGenerator {
    
    fun showPositionDistributionChart(players: List<Player>) {
        SwingUtilities.invokeLater {
            val playersByPosition = players.groupBy { it.position }
            val totalPlayers = players.size
            
            val dataset = DefaultPieDataset<String>()
            
            playersByPosition.forEach { (position, positionPlayers) ->
                val percentage = (positionPlayers.size.toDouble() / totalPlayers * 100).let { 
                    String.format("%.1f", it) 
                }
                dataset.setValue("${position.displayName}\n(${positionPlayers.size} игроков, $percentage%)", positionPlayers.size)
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
            plot.setOutlinePaint(Color.BLACK)
            plot.setLabelFont(Font("Arial", Font.PLAIN, 12))
            plot.setLabelBackgroundPaint(Color.WHITE)
            plot.setLabelOutlinePaint(Color.BLACK)
            plot.setLabelShadowPaint(Color.LIGHT_GRAY)
            
            
            val colors = arrayOf(
                Color(255, 99, 132),   
                Color(54, 162, 235),   
                Color(255, 205, 86),   
                Color(75, 192, 192)    
            )
            
            var colorIndex = 0
            playersByPosition.keys.forEach { position ->
                plot.setSectionPaint(position.displayName, colors[colorIndex % colors.size])
                colorIndex++
            }
            
            chart.title.paint = Color.BLACK
            chart.title.font = Font("Arial", Font.BOLD, 16)
            
            val chartPanel = ChartPanel(chart)
            chartPanel.preferredSize = java.awt.Dimension(800, 600)
            
            val frame = JFrame("Статистика игроков по позициям")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.contentPane = chartPanel
            frame.pack()
            frame.setLocationRelativeTo(null) 
            frame.isVisible = true
            
        }
    }
}

