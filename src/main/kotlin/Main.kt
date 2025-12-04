import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars
import resolver.Resolver

fun main() {
    val resolver = Resolver()

    val share: Map<String, Double> = resolver.getNationalityShare()

    val df = dataFrameOf(
        "национальность" to share.keys.toList(),
        "доля" to share.values.toList()
    )

    val plot = df.plot {
        bars {
            x("национальность")
            y("доля")
        }
        layout.title = "Доля игроков по странам"
    }

    plot.save("nationality_share.png")
    println("График сохранён")
}