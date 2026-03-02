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

    // Визуализация — только вариант 2: Топ‑10 команд с наивысшей суммарной трансферной стоимостью
    println("╔════════════════════════════════════════════════════════════════╗")
    println("║              ВИЗУАЛИЗАЦИЯ: ТОП‑10 КОМАНД ПО СТОИМОСТИ       ║")
    println("╚════════════════════════════════════════════════════════════════╝")
    println()
    println("Открывается график: Топ‑10 команд по суммарной трансферной стоимости...")
    println()

    ChartVisualizer.showTopTeamsByTransferValue(teamList)
    //ВАРИАНТ2
    // Программа не завершается сразу — окно с графиком остаётся открытым
    // (JFrame настроен на EXIT_ON_CLOSE, поэтому закрытие окна завершит программу)
}
