import resolver.Resolver
import chart.createTopTeamsDataset
import chart.createBarChart
import chart.showChartInWindow

fun main() {
    val resolver = Resolver.createFromFile("fakePlayers.csv")
    val topTeams = resolver.getTop10TeamsByTransferValue()
    val dataset = createTopTeamsDataset(topTeams, inMillions = true)
    val chart = createBarChart(dataset, "ТОП-10 команд по суммарной трансферной стоимости", "Млн €")
    showChartInWindow(chart, "ТОП-10 команд")
}
