package chart

import model.Player
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartFrame
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection

class ChartBuilder {
    companion object {
        fun createScatterPlot(
            data: List<Pair<Int, Int>>,
            title: String,
            labelX: String,
            labelY: String,
            pointLabel: String
        ) {
            val series = XYSeries(pointLabel)

            data.forEach { item ->
                series.add(item.first, item.second)
            }

            val dataset = XYSeriesCollection(series)

            val chart = ChartFactory.createScatterPlot(
                title,
                labelX,
                labelY,
                dataset
            )

            val frame = ChartFrame("Точечная диаграмма", chart)
            frame.pack()
            frame.isVisible = true
        }
    }
}