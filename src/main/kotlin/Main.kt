import model.Player
import model.Team
import parser.CsvParser
import visualization.ChartVisualizer

fun main(args: Array<String>) {
    // Загрузка данных из CSV
    val inputStream = object {}.javaClass.classLoader.getResourceAsStream("fakePlayers.csv")
        ?: throw IllegalStateException("Файл fakePlayers.csv не найден в ресурсах")

    val playerList: List<Player> = inputStream.use { stream ->
        CsvParser.parsePlayers(stream)
    }

    val teamList: List<Team> = CsvParser.parseTeams(playerList)

    println("Загружено игроков: ${playerList.size}")
    println("Загружено команд: ${teamList.size}")
    println()
    println("Топ 10 команд по стоимости")
    ChartVisualizer.showTopTeamsByTransferValue(teamList)

}
