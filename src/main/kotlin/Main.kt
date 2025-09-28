import model.Player
import parser.CsvParser
import resolver.Resolver
import visualization.ChartVisualizer

fun main() {
    try {
        // Читаем данные из CSV файла
        val players: List<Player> = CsvParser.readPlayersFromCsv("src/main/resources/fakePlayers.csv")

        // Создаем резолвер и визуализатор
        val resolver = Resolver(players)
        val chartVisualizer = ChartVisualizer()

        // Выполняем основные задания
        println("=== ОСНОВНЫЕ ЗАДАЧИ ===")
        println("1. Количество игроков без агентства: ${resolver.getCountWithoutAgency()}")

        val bestScorer = resolver.getBestScorerDefender()
        println("2. Лучший бомбардир среди защитников: ${bestScorer.first} (голов: ${bestScorer.second})")

        val germanPosition = resolver.getTheExpensiveGermanPlayerPosition()
        println("3. Позиция самого дорогого немецкого игрока: $germanPosition")

        val rudestTeam = resolver.getTheRudestTeam()
        println("4. Самая грубая команда: ${rudestTeam.name} из ${rudestTeam.city}")

        // Топ-10 команд по трансферной стоимости
        println("\n5. Топ-10 команд по суммарной трансферной стоимости:")
        val topTeams = resolver.getTop10TeamsByTransferValue()
        if (topTeams.isEmpty()) {
            println("   Нет данных о командах")
        } else {
            topTeams.forEachIndexed { index, (team, totalValue) ->
                println("   ${index + 1}. ${team.name} (${team.city}): ${formatCurrency(totalValue)}")
            }
        }

        // ВИЗУАЛИЗАЦИЯ
        println("\n=== СОЗДАНИЕ ГРАФИКОВ ===")

        // Вариант 1: Доля позиций игроков
        chartVisualizer.createPositionDistributionChart(players)

        // Вариант 2: Топ-10 команд по стоимости
        chartVisualizer.createTopTeamsChart(topTeams)

        // Вариант 3: Зависимость голов от стоимости для нападающих
        chartVisualizer.createGoalsVsCostChart(players)

        // Вариант 4: Доля игроков по странам
        chartVisualizer.createNationalityDistributionChart(players)

        println("\n=== ВСЕ ГРАФИКИ СОХРАНЕНЫ В ПАПКУ 'charts' ===")

    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
        e.printStackTrace()
    }
}

// Вспомогательная функция для форматирования валюты
fun formatCurrency(amount: Long): String {
    return "%,d".format(amount)
}