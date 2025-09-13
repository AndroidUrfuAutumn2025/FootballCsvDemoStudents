package drawers

import model.Player
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.ir.Plot
import org.jetbrains.kotlinx.kandy.letsplot.export.toBufferedImage
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.pie
import org.jetbrains.kotlinx.kandy.letsplot.scales.BrewerPalette
import org.jetbrains.kotlinx.kandy.letsplot.scales.categoricalColorBrewer
import org.jetbrains.kotlinx.kandy.letsplot.style.Style
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingUtilities

object ChartDrawer {
    // Вариант 1.
    // Покажите, какую долю от всех игроков занимают игроки каждой из позиций (защитник, нападающий и тд).
    fun drawPositionsPieChart(players: List<Player>) {
        val positionsToCounts = players.groupingBy { it.position }.eachCount()
        val total = players.size.toDouble()

        val positions = positionsToCounts.map {
            "${it.key.displayName} (${(it.value / total * 100)}%)"
        }
        val counts = positionsToCounts.values.toList()

        val dataset = dataFrameOf(
            "Позиция" to positions,
            "Количество" to counts
        )

        dataset.plot {
            pie {
                slice("Количество")
                fillColor("Позиция") {
                    scale = categoricalColorBrewer(BrewerPalette.Qualitative.Set1)
                }

                size = 30.0
            }
            layout {
                style(Style.Void)
            }
        }.show("Доля игроков по позициям")
    }

    private fun Plot.show(title: String) {
        val image = this.toBufferedImage()

        SwingUtilities.invokeLater {
            val frame = JFrame(title)
            frame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
            frame.contentPane.add(JLabel(ImageIcon(image)))
            frame.pack()
            frame.setLocationRelativeTo(null)
            frame.isVisible = true
        }
    }
}