import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import model.Player
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.JFrame


object DataVisualizer {

    fun showGoalsVsCostChart(players: List<Player>) {
        // Фильтруем только нападающих
        val forwards = players.filter { it.position.equals("FORWARD", ignoreCase = true) }

        // dataset для графика
        val dataset = XYSeriesCollection()
        val series = XYSeries("Нападающие")

        forwards.forEach { player ->
            series.add(player.transferCost.toDouble(), player.goals.toDouble())
        }

        dataset.addSeries(series)

        // график
        val chart: JFreeChart = ChartFactory.createScatterPlot(
            "Зависимость голов от трансферной стоимости (Нападающие)", // заголовок
            "Трансферная стоимость (€)", // ось X
            "Количество голов", // ось Y
            dataset, // данные
            PlotOrientation.VERTICAL,
            true, // показывать легенду
            true, // tooltips
            false // URLs
        )

        // внешний вид графика
        val plot = chart.xyPlot
        plot.backgroundAlpha = 0.5f

        // панель для графика
        val chartPanel = ChartPanel(chart)
        chartPanel.preferredSize = Dimension(800, 600)

        // фрейм для отображения
        val frame = JFrame("Визуализация: Голы vs Стоимость")
        frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        frame.contentPane.add(chartPanel, BorderLayout.CENTER)
        frame.pack()
        frame.setLocationRelativeTo(null) // Центрируем окно
        frame.isVisible = true
    }
}