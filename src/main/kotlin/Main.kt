import model.Player
import model.Team
import parser.CsvParser
import analyzer.StatisticsCalculator
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
    
    // Создание калькулятора статистики
    val statisticsCalculator = StatisticsCalculator(playerList, teamList)
    
    // Выполнение анализа данных
    println("╔════════════════════════════════════════════════════════════════╗")
    println("║              РЕЗУЛЬТАТЫ АНАЛИЗА ФУТБОЛЬНЫХ ДАННЫХ            ║")
    println("╚════════════════════════════════════════════════════════════════╝")
    println()
    
    // Задача 1: Игроки без агентства
    println("┌────────────────────────────────────────────────────────────────┐")
    println("│ Задача 1: Игроки без агентства                                 │")
    println("├────────────────────────────────────────────────────────────────┤")
    val playersWithoutAgencyCount = statisticsCalculator.calculatePlayersWithoutAgency()
    println("│ Количество игроков без агентства: $playersWithoutAgencyCount")
    println("└────────────────────────────────────────────────────────────────┘")
    println()
    
    // Задача 2: Лучший бомбардир среди защитников
    println("┌────────────────────────────────────────────────────────────────┐")
    println("│ Задача 2: Лучший бомбардир среди защитников                  │")
    println("├────────────────────────────────────────────────────────────────┤")
    val topScoringDefenderResult = statisticsCalculator.findTopScoringDefender()
    if (topScoringDefenderResult.second > 0) {
        println("│ Имя игрока: ${topScoringDefenderResult.first}")
        println("│ Количество голов: ${topScoringDefenderResult.second}")
    } else {
        println("│ Результат: ${topScoringDefenderResult.first}")
    }
    println("└────────────────────────────────────────────────────────────────┘")
    println()
    
    // Задача 3: Позиция самого дорогого немецкого игрока
    println("┌────────────────────────────────────────────────────────────────┐")
    println("│ Задача 3: Позиция самого дорогого немецкого игрока            │")
    println("├────────────────────────────────────────────────────────────────┤")
    val germanPlayerPosition = statisticsCalculator.getMostExpensiveGermanPlayerPosition()
    val germanPlayersFiltered = playerList.filter { 
        it.nationality.lowercase().trim() == "germany".lowercase() 
    }
    val mostExpensiveGerman = germanPlayersFiltered
        .sortedByDescending { it.transferCost }
        .firstOrNull()
    
    if (mostExpensiveGerman != null) {
        println("│ Имя игрока: ${mostExpensiveGerman.name}")
        println("│ Позиция (на русском): $germanPlayerPosition")
    } else {
        println("│ Результат: $germanPlayerPosition")
    }
    println("└────────────────────────────────────────────────────────────────┘")
    println()
    
    // Задача 4: Команда с наибольшим средним числом красных карточек
    println("┌────────────────────────────────────────────────────────────────┐")
    println("│ Задача 4: Команда с наибольшим средним числом красных карточек│")
    println("├────────────────────────────────────────────────────────────────┤")
    val teamWithHighestRedCardAverage = statisticsCalculator.findTeamWithHighestRedCardAverage()
    println("│ Название команды: ${teamWithHighestRedCardAverage.name}")
    println("│ Среднее число красных карточек на игрока: ${"%.2f".format(teamWithHighestRedCardAverage.averageRedCardsPerPlayer)}")
    var redCardsTotal = 0
    for (player in teamWithHighestRedCardAverage.players) {
        redCardsTotal += player.redCards
    }
    println("│ Общее количество красных карточек: $redCardsTotal")
    println("└────────────────────────────────────────────────────────────────┘")
    println()
    
    // Визуализация - Зависимость голов от трансферной стоимости для нападающих
    println("╔════════════════════════════════════════════════════════════════╗")
    println("║                    ВИЗУАЛИЗАЦИЯ ДАННЫХ                        ║")
    println("╚════════════════════════════════════════════════════════════════╝")
    println()
    println("Открывается график: Зависимость количества забитых голов")
    println("от трансферной стоимости для нападающих...")
    println()
    ChartVisualizer.showGoalsVsTransferCostForForwards(playerList)
    
    // Можно также показать другие варианты визуализации (раскомментируйте нужный):
    // ChartVisualizer.showPositionDistribution(players)
    // ChartVisualizer.showTopTeamsByTransferValue(teams)
    // ChartVisualizer.showPlayersByCountry(players)
}