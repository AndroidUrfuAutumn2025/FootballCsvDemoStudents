import model.Team
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartPanel
import org.jfree.chart.JFreeChart
import org.jfree.chart.axis.NumberAxis
import org.jfree.data.category.DefaultCategoryDataset
import org.jfree.data.general.DefaultPieDataset
import parser.CsvParser
import java.text.NumberFormat
import javax.swing.JFrame
import javax.swing.SwingUtilities


fun main(args: Array<String>) {
    val parser = CsvParser("/fakePlayers.csv")
    val players = parser.parseCSV()

    val playersWithoutTeam = players.filter{it.agency.isNullOrBlank()}.count()
    println("Игроков без агенства: $playersWithoutTeam")

    val defenders = players.filter { it.position.equals("DEFENDER") }
    val topDefender = defenders.maxByOrNull { it.goals  }
    if (topDefender != null) {
        println("Лучший бомбардир среди защитников: ${topDefender.name}")
    } else {
        println("Нет лучших защитников")
    }

    val germanPlayers = players.filter { it.nationality.equals("Germany") }
    val germanTopPlayer = germanPlayers.maxByOrNull { it.transferCost }
    val germanTopPlayerPosition = translateToRus(germanTopPlayer?.position ?: "")
    if (germanTopPlayer != null) {
        println("Лучший немецкий игрок: ${germanTopPlayer.name} ($germanTopPlayerPosition)")
    } else {
        println("Нет немецких игроков")
    }

    val teamWithHighestAverageRedCards = players.groupBy { it.team }
        .mapValues { (_, playersInTeam) ->
            playersInTeam.sumOf { it.redCards }.toDouble() / playersInTeam.size
        }
        .maxByOrNull { it.value }?.key

    println("Команда с наибольшим средним числом красных карточек на одного игрока: ${teamWithHighestAverageRedCards?.name}")

    val goalkeepers = players.filter { it.position.equals("GOALKEEPER") }
    val defender_s = players.filter { it.position.equals("DEFENDER") }
    val midfielders = players.filter { it.position.equals("MIDFIELD") }
    val forwards = players.filter { it.position.equals("FORWARD") }
    positionPieChart(goalkeepers.size, defender_s.size, midfielders.size, forwards.size)

    val mostTransferTeamCost = players
        .groupBy { it.team }
        .mapValues { (_, teamPlayers) ->
            teamPlayers.sumOf { it.transferCost }
        }
        .toList()
        .sortedByDescending { (_, cost) -> cost }
        .take(10)

    createTeamCostBarChart(mostTransferTeamCost)
}

fun createTeamCostBarChart(teamCosts: List<Pair<Team?, Int>>) {
    val dataset = DefaultCategoryDataset()
    teamCosts.forEach { (team, cost) ->
        dataset.addValue(cost.toDouble(), "Transfer Cost", team?.name ?: "No Team")
    }

    val chart = ChartFactory.createBarChart(
        "Топ 10 команд по суммарной трансферной стоимости",
        "Команда",
        "Суммарная стоимость",
        dataset
    )

    val plot = chart.categoryPlot
    val rangeAxis = plot.rangeAxis as NumberAxis
    rangeAxis.numberFormatOverride = NumberFormat.getNumberInstance()

    SwingUtilities.invokeLater {
        JFrame("Столбчатая диаграмма").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            contentPane.add(ChartPanel(chart))
            pack()
            setLocationRelativeTo(null)
            isVisible = true
        }
    }
}

fun positionPieChart(goalkeepers: Int, defender_s: Int, midfielders: Int, forwards: Int) {
    val dataset = DefaultPieDataset<String>().apply {
        setValue("Вратари", goalkeepers.toDouble())
        setValue("Защитники", defender_s.toDouble())
        setValue("Полузащитники", midfielders.toDouble())
        setValue("Нападающие", forwards.toDouble())
    }

    val chart: JFreeChart = ChartFactory.createPieChart(
        "Количество игроков по позициям",
        dataset,
        false,
        true,
        false
    )

    SwingUtilities.invokeLater {
        JFrame("Круговая диаграмма").apply {
            defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            contentPane.add(ChartPanel(chart))
            pack()
            setLocationRelativeTo(null)
            isVisible = true
        }
    }
}
fun translateToRus(position: String): String {
    return when (position) {
        "GOALKEEPER" -> "Вратарь"
        "DEFENDER" -> "Защитник"
        "MIDFIELD" -> "Полузащитник"
        "FORWARD" -> "Нападающий"
        else -> position
    }
}
