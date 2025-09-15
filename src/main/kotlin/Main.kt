import model.Roles
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.line
import org.jetbrains.kotlinx.kandy.letsplot.layers.points
import org.jetbrains.kotlinx.kandy.letsplot.x
import org.jetbrains.kotlinx.kandy.letsplot.y
import parser.CsvParser
import resolver.Resolver


fun main(args: Array<String>) {

    val list = CsvParser.parseFile("src/main/resources/fakePlayers.csv")

    val resolver = Resolver(list)
    println(resolver.getTheRudestTeam().Name)
    println(resolver.getBestScorerDefender())
    println(resolver.getCountWithoutAgency())
    println((resolver.getTheExpensiveGermanPlayerPosition()))

    val rgfegt = list.groupBy { it.team }.map { Pair(it.key, it.value.map { it.RedCards }.average())}

    val pl = list.filter { it.Role == Roles.FORWARD }.map { Pair(it.Goals, it.Cost) }.sortedBy { it.first }

    val data = dataFrameOf(
        "Goals" to pl.sortedBy { it.second }.map { it.first },
        "Cost" to pl.sortedBy { it.second }.map { it.second }
    )

    plot(data){
        x("Cost")
        y("Goals")

        points {
            size = 4.0
        }
        line {
            width = 1.0
        }
        layout{
            size = 700 to 500
        }
    }.save("C:\\Users\\user\\Documents\\GitHub\\FootballCsvDemoStudents\\Result.png")

}