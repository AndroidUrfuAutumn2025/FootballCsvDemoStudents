import model.Player
import model.Team
import parser.CsvParser
import resolver.Resolver
import visualization.ChartGenerator

fun main(args: Array<String>) {
    val players = loadPlayers()
    val teams = CsvParser.parseTeams(players)
    
    solveMainTasks(players, teams)
    createVisualizations(players)
}

// Загружаем данные
fun loadPlayers(): List<Player> {
    val csvPath = "src/main/resources/fakePlayers.csv"
    return CsvParser.parsePlayers(csvPath)
}

fun solveMainTasks(players: List<Player>, teams: List<Team>) {
    val resolver = Resolver(players, teams)
    
    val playersWithoutAgency = resolver.getCountWithoutAgency()
    println("Количество игроков без агентства: $playersWithoutAgency")
    
    val bestDefenderScorer = resolver.getBestScorerDefender()
    println("Лучший бомбардир среди защитников: ${bestDefenderScorer.first} (${bestDefenderScorer.second} голов)")
    
    val expensiveGermanPosition = resolver.getTheExpensiveGermanPlayerPosition()
    println("Позиция самого дорогого немецкого игрока: $expensiveGermanPosition")
    
    val rudestTeam = resolver.getTheRudestTeam()
    println("Команда с наибольшим средним числом красных карточек: ${rudestTeam.name} (${rudestTeam.city})")
}

// Визуализация данных
fun createVisualizations(players: List<Player>) {
    // Вариант 1: Распределение по позициям
    val positionChart = ChartGenerator.createPositionDistributionChart(players)
    ChartGenerator.showChart(positionChart)
    
    // Вариант 2: Топ-10 команд по стоимости
    val teamsChart = ChartGenerator.createTopTeamsChart(players)
    ChartGenerator.showChart(teamsChart)
    
    // Вариант 3: Зависимость голов от стоимости для нападающих
    val goalsChart = ChartGenerator.createGoalsVsCostChart(players)
    ChartGenerator.showChart(goalsChart)
    
    // Вариант 4: Распределение по странам
    val nationalityChart = ChartGenerator.createNationalityChart(players)
    ChartGenerator.showChart(nationalityChart)
}