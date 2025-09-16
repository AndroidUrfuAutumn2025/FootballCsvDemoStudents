import model.Player
import parser.CsvParser
import resolver.Resolver
import visualizer.ChartVisualizer

fun main() {
    println("=== Football CSV Demo ===")

    val parser = CsvParser()
    val players: List<Player> = try {
        parser.parseCsv("src/main/resources/fakePlayers.csv")
    } catch (e: Exception) {
        println("Error reading file: ${e.message}")
        emptyList()
    }

    println("Total players loaded: ${players.size}")

    val resolver = Resolver(players)

    println("\n=== Task Results ===")
    println("1. Players without agency: ${resolver.getCountWithoutAgency()}")

    val bestDefender = resolver.getBestScoreDefender()
    println("2. Top defender: ${bestDefender.first} with ${bestDefender.second} goals")

    val germanPosition = resolver.getTheExpensiveGermanPlayerPosition()
    println("3. Most expensive German player position: $germanPosition")

    val rudestTeam = resolver.getTheRudestTeam()
    println("4. Team with highest average red cards: ${rudestTeam.name} from ${rudestTeam.city}")

    println("\n=== Position Distribution ===")
    val distribution = resolver.getPositionDistribution()

    val chartDistribution = distribution.mapKeys { (position, percentage) ->
        when (position.uppercase()) {
            "DEFENDER" -> "Defenders (${"%.1f".format(percentage)}%)"
            "FORWARD" -> "Forwards (${"%.1f".format(percentage)}%)"
            "MIDFIELD" -> "Midfielders (${"%.1f".format(percentage)}%)"
            "GOALKEEPER" -> "Goalkeepers (${"%.1f".format(percentage)}%)"
            else -> "$position (${"%.1f".format(percentage)}%)"
        }
    }

    // Вывод распределения
    distribution.forEach { (position, percentage) ->
        val engPosition = when (position.uppercase()) {
            "DEFENDER" -> "Defenders"
            "FORWARD" -> "Forwards"
            "MIDFIELD" -> "Midfielders"
            "GOALKEEPER" -> "Goalkeepers"
            else -> position
        }
        println("$engPosition: ${"%.2f".format(percentage)}%")
    }

    println("\n=== Creating Quality Pie Chart ===")
    val visualizer = ChartVisualizer()

    // Создаем качественную диаграмму
    visualizer.createQualityPieChart(chartDistribution)
    visualizer.createSimplePieChart(chartDistribution)

    println("Quality charts created successfully!")
    println("Check the project folder for 'player_positions_chart.png' and 'simple_pie_chart.png'")
}