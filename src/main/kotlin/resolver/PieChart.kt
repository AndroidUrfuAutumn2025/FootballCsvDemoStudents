package resolver
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.data.category.DefaultCategoryDataset
import javax.swing.JFrame

class PieChart {
    fun showTopTeamsChart(topTeams: List<Pair<String, Int>>) {
        val dataset = DefaultCategoryDataset()

        for ((team, cost) in topTeams) {
            dataset.addValue(cost/1000000, "Transfer Cost", team)
        }

        val chart: JFreeChart = ChartFactory.createBarChart(
            "топ-10 команд с наивысшей суммарной трансферной стоимостью",
            "Команда",
            "суммарноая трансферная стоимость, млн.",
            dataset
        )

        val frame = JFrame("Статистика")
        frame.contentPane = ChartPanel(chart)
        frame.setSize(1600, 600)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.isVisible = true
    }
}