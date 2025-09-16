import org.apache.commons.lang3.mutable.Mutable
import parser.CsvParser
import resolver.Resolver

import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.continuous
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.*
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.bars

fun main(args: Array<String>) {
    val resolver = Resolver(CsvParser.myList)
    println("Task1: " + resolver.getCountWithoutAgency())
    println("Task2: " + resolver.getBestScorerDefender())
    println("Task3: " + resolver.getTheExpensiveGermanPlayerPosition())
    println("Task4: " + resolver.getTheRudestTeam().name)

    val nationalitiesDataMap = CsvParser.myList.groupBy { it.nationality }.mapValues { it.value.size }
    val nationalitiesData = mapOf(
        "Nationality" to nationalitiesDataMap.keys.toList(),
        "Quantity" to nationalitiesDataMap.values.toList())

    plot(nationalitiesData) {
        bars {
            x("Nationality")
            y("Quantity") { scale=continuous(0..40) }
            fillColor("Quantity")
        }

        layout {
            title = "Task5 Var.4: Player Distribution by Country"
            size = 1750 to 800
        }
    }.save("/home/user/study/Kotlin/KotlinHomework-1/src/main/resources/plot.png")
}