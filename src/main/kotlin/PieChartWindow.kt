import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.data.general.PieDataset
import resolver.Resolver
import java.awt.Dimension
import javax.swing.JFrame

class PieChartWindow : JFrame("Трансферная стоимость команд") {

    val method = Resolver()

    init {
        createAndShowChart()
    }

    private fun createAndShowChart() {
        val mapTeamByTransferCost = method.createDataset()
        val chart = createChart(mapTeamByTransferCost)
        val chartPanel = ChartPanel(chart)
        chartPanel.preferredSize = Dimension(800, 600)
        contentPane = chartPanel

        pack()
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        setLocationRelativeTo(null)
        isVisible = true
    }

    private fun createChart(dataset: PieDataset<String>) : JFreeChart{
        val chart = ChartFactory.createPieChart(
            "Трансферная стоимость команд",
            dataset,
            true,
            true,
            false
        )
        return chart
    }

}