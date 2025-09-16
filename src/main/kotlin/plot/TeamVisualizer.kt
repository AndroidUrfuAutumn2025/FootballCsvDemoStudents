package plot

import model.Team
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartFrame
import org.jfree.chart.axis.CategoryLabelPositions
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator
import org.jfree.chart.plot.CategoryPlot
import org.jfree.chart.renderer.category.BarRenderer
import org.jfree.chart.renderer.category.StandardBarPainter
import org.jfree.chart.ui.RectangleInsets
import org.jfree.data.category.DefaultCategoryDataset
import java.awt.Color
import java.awt.Font
import java.awt.GradientPaint
import java.awt.Paint

object TeamVisualizer {
    fun visualizeTop10Teams(teamsData: List<Pair<Team, Long>>) {
        val dataset = DefaultCategoryDataset()

        teamsData.forEachIndexed { index, (team, cost) ->
            val costInMillions = cost.toDouble() / 1_000_000
            val teamLabel = formatTeamName(team.name, team.city)
            dataset.addValue(costInMillions, "Команда ${index + 1}", teamLabel)
        }

        val chart = ChartFactory.createBarChart(
            "ТОП-10 КОМАНД ПО ТРАНСФЕРНОЙ СТОИМОСТИ",
            "",
            "Стоимость (млн €)",
            dataset
        )

        chart.backgroundPaint = Color(240, 245, 255)
        chart.setPadding(RectangleInsets(20.0, 20.0, 20.0, 20.0))

        val plot = chart.plot as CategoryPlot
        plot.backgroundPaint = Color(250, 250, 255)
        plot.isOutlineVisible = false

        // Градиентные цвета от золотого к синему
        val gradientColors = listOf(
            Color(255, 215, 0),    // золотой
            Color(255, 193, 37),   // золотой светлый
            Color(255, 171, 64),   // оранжево-золотой
            Color(255, 140, 0),    // темно-оранжевый
            Color(255, 110, 0),    // красно-оранжевый
            Color(70, 130, 180),   // steel blue
            Color(65, 105, 225),   // royal blue
            Color(30, 144, 255),   // dodger blue
            Color(0, 191, 255),    // deep sky blue
            Color(135, 206, 250)   // light sky blue
        )

        val customRenderer = object : BarRenderer() {
            override fun getItemPaint(row: Int, column: Int): Paint {
                val baseColor = gradientColors.getOrElse(column) { Color.LIGHT_GRAY }

                return GradientPaint(
                    0f, 0f, baseColor.darker(),
                    0f, 100f, baseColor.brighter(),
                    true
                )
            }
        }

        plot.renderer = customRenderer

        customRenderer.setShadowVisible(true)
        customRenderer.setShadowPaint(Color(0, 0, 0, 80))
        customRenderer.setBarPainter(StandardBarPainter())
        customRenderer.setSeriesItemLabelsVisible(0, true)

        val domainAxis = plot.domainAxis
        domainAxis.labelPaint = Color(50, 50, 50)
        domainAxis.tickLabelPaint = Color(80, 80, 80)
        domainAxis.tickLabelFont = Font("Arial", Font.PLAIN, 10)
        domainAxis.setCategoryLabelPositions(
            CategoryLabelPositions.createUpRotationLabelPositions(
                Math.PI / 2.8
            )
        )

        domainAxis.maximumCategoryLabelWidthRatio = 0.8f
        domainAxis.maximumCategoryLabelLines = 3

        val rangeAxis = plot.rangeAxis
        rangeAxis.labelPaint = Color(50, 50, 50)
        rangeAxis.tickLabelPaint = Color(80, 80, 80)

        val itemLabelGenerator = StandardCategoryItemLabelGenerator()
        customRenderer.setDefaultItemLabelGenerator(itemLabelGenerator)
        customRenderer.setDefaultItemLabelsVisible(true)
        customRenderer.setDefaultItemLabelPaint(Color.WHITE)
        customRenderer.setDefaultItemLabelFont(Font("Arial", Font.BOLD, 12))

        chart.legend.isVisible = false

        val backgroundGradient = GradientPaint(
            0f, 0f, Color(240, 245, 255),
            0f, 600f, Color(220, 230, 255),
            true
        )
        plot.backgroundPaint = backgroundGradient

        val frame = ChartFrame("Трансферная стоимость команд", chart)
        frame.background = Color(240, 245, 255)

        frame.pack()
        frame.setSize(1200, 700)
        frame.setLocationRelativeTo(null)
        frame.isVisible = true

        chart.title.apply {
            paint = Color(50, 50, 50)
            font = Font("Arial", Font.BOLD, 18)
        }
    }

    private fun formatTeamName(name: String, city: String): String {
        val maxLineLength = 15
        val fullText = "$name ($city)"

        return if (fullText.length <= maxLineLength) {
            fullText
        } else {
            val words = fullText.split(" ")
            val lines = mutableListOf<String>()
            var currentLine = ""

            for (word in words) {
                if (currentLine.length + word.length + 1 <= maxLineLength) {
                    currentLine += if (currentLine.isEmpty()) word else " $word"
                } else {
                    lines.add(currentLine)
                    currentLine = word
                }
            }
            if (currentLine.isNotEmpty()) {
                lines.add(currentLine)
            }

            lines.joinToString("\n")
        }
    }
}