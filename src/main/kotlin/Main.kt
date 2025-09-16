import model.Position
import model.Team
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.layers.points
import parser.CsvParser
import resolver.Resolver

fun main(args: Array<String>) {
    val filePath = "src/main/resources/fakePlayers.csv"

    val players = CsvParser.parsePlayers(filePath)

    val resolver = Resolver(players)

    val countWithoutAgency = resolver.getCountWithoutAgency()
    println("Игроков без агенства: $countWithoutAgency")

    val (defenderName, goals) = resolver.getBestScorerDefender()
    println("Лучший защитник по голам: $defenderName, голов: $goals")

    val expensiveGermanPosition = resolver.getTheExpensiveGermanPlayerPosition()
    println("Позиция самого дорогого немецкого игрока: $expensiveGermanPosition")

    val rudestTeam: Team = resolver.getTheRudestTeam()
    println("Команда с наибольшим средним числом красных карточек: ${rudestTeam.name} (${rudestTeam.city})")

    val forwards = players.filter { it.position == Position.FORWARD }
    val dataset: Map<String, List<*>> = mapOf(
        "Количество голов" to forwards.map { it.goals },
        "Трансферная стоимость" to forwards.map { it.transferCost }
    )

    val plot = plot(dataset) {
        points {
            x("Количество голов")
            y("Трансферная стоимость")
        }
    }
    val path = plot.save("grafic.png")
    println("График сохранен в $path")

}