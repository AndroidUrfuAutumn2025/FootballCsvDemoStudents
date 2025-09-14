package plot

import model.Team
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartFrame
import org.jfree.chart.axis.CategoryAxis
import org.jfree.chart.axis.CategoryLabelPositions
import org.jfree.chart.axis.NumberAxis
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator
import org.jfree.chart.plot.CategoryPlot
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.category.BarRenderer
import org.jfree.data.category.DefaultCategoryDataset
import java.awt.Color
import java.awt.Font

/**
 * Визуализация данных команд
 */
object PlotUtils {

    private val barColors = listOf(
        Color(255, 215, 0),
        Color(255, 193, 37),
        Color(255, 171, 64),
        Color(255, 140, 0),
        Color(255, 110, 0),
        Color(70, 130, 180),
        Color(65, 105, 225),
        Color(30, 144, 255),
        Color(0, 191, 255),
        Color(135, 206, 250)
    )

    fun visualizeTop10Teams(teamsData: List<Pair<Team, Long>>) {
        val top10Teams = teamsData.take(10)

        val dataset = DefaultCategoryDataset()
        top10Teams.forEachIndexed { index, (team, cost) ->
            val shortName = getShortTeamName(team.name)
            dataset.addValue(cost.toDouble() / 1_000_000, "Стоимость", shortName)
        }

        val chart = ChartFactory.createBarChart(
            "ТОП-10 КОМАНД ПО ТРАНСФЕРНОЙ СТОИМОСТИ",
            "Команды",
            "Стоимость (млн €)",
            dataset,
            PlotOrientation.VERTICAL,
            false,
            true,
            false
        )

        chart.backgroundPaint = Color(240, 245, 255)
        val plot = chart.plot as CategoryPlot
        plot.backgroundPaint = Color(250, 250, 255)
        plot.isOutlineVisible = false
        plot.renderer = createCustomRenderer()

        setupAxis(plot)

        val frame = ChartFrame("Трансферная стоимость команд", chart)
        frame.setSize(1600, 900)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true

        chart.title.apply {
            paint = Color.BLACK
            font = Font("Arial", Font.BOLD, 20)
        }
    }


    private fun createCustomRenderer(): BarRenderer {
        return object : BarRenderer() {
            override fun getItemPaint(row: Int, column: Int): Color {
                return barColors.getOrElse(column) { Color.GRAY }
            }
        }.apply {
            setShadowVisible(false)
            setDrawBarOutline(false)
            setMaximumBarWidth(0.06) // тонкие столбцы
            setDefaultItemLabelGenerator(StandardCategoryItemLabelGenerator())
            setDefaultItemLabelsVisible(true)
            setDefaultItemLabelFont(Font("Arial", Font.BOLD, 8))
            setDefaultItemLabelPaint(Color.BLACK)
        }
    }

    private fun setupAxis(plot: CategoryPlot) {
        val domainAxis: CategoryAxis = plot.domainAxis
        domainAxis.labelPaint = Color.BLACK
        domainAxis.tickLabelPaint = Color.DARK_GRAY
        domainAxis.tickLabelFont = Font("Arial", Font.BOLD, 8)
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90)
        domainAxis.lowerMargin = 0.01
        domainAxis.upperMargin = 0.01
        domainAxis.categoryMargin = 0.1

        val rangeAxis: NumberAxis = plot.rangeAxis as NumberAxis
        rangeAxis.labelPaint = Color.BLACK
        rangeAxis.tickLabelPaint = Color.BLACK
        rangeAxis.tickLabelFont = Font("Arial", Font.BOLD, 8)
        rangeAxis.standardTickUnits = NumberAxis.createIntegerTickUnits()
    }

    private fun getShortTeamName(fullName: String): String {
        return if (fullName.length > 15) fullName.take(10) + "..." else fullName
    }
}
