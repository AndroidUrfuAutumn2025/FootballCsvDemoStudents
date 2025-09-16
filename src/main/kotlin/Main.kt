import model.Team
import model.Player
import parser.CsvParser
import resolver.Resolver
import visualization.ChartBuilder

fun main(args: Array<String>) {
    try {
        println("Загрузка и анализ футбольных данных")
        println("=".repeat(60))

        val filePath = "C:/Users/Flaim/StudioProjects/FootballCsvDemoStudents/src/main/resources/fakePlayers.csv"
        println("Загрузка файла: $filePath")

        val teams = CsvParser.parseCsv(filePath)

        println("Файл успешно загружен:")
        println("Команд: ${teams.size}")
        println("Игроков: ${teams.sumOf { it.player.size }}")
        println("=".repeat(60))

        val resolver = Resolver(teams)

        println("Перевод позиций (тест):")
        val testPlayers = listOf("Iva Streich", "Miss Buck Bradtke", "Ms. Adolph Hartmann", "Kenyatta Emard")
        testPlayers.forEach { playerName ->
            val position = resolver.getTranslatedPosition(playerName)
            println("$playerName: $position")
        }

        println("=".repeat(60))
        println("1. Распределение игроков по позициям")
        println("=".repeat(60))
        println(resolver.getPositionDistributionFormatted())

        println("=".repeat(60))
        println("2. Топ-10 команд по трансферной стоимости")
        println("=".repeat(60))
        println(resolver.getTopTeamsByTransferValueFormatted())

        println("=".repeat(60))
        println("3. Зависимость голов от стоимости (нападающие)")
        println("=".repeat(60))
        println(resolver.getGoalsVsCostForForwardsFormatted())

        println("=".repeat(60))
        println("4. Распределение игроков по странам")
        println("=".repeat(60))
        println(resolver.getNationalityDistributionFormatted())

        println("=".repeat(60))
        println("Оригинальные методы интерфейса")
        println("=".repeat(60))

        val withoutAgency = resolver.getCountWithoutAgency()
        val totalPlayers = teams.sumOf { it.player.size }
        println("Игроков без агентства: $withoutAgency")

        val bestDefender = resolver.getBestScorerDefender()
        println("Лучший бомбардир среди защитников: ${bestDefender.first} (${bestDefender.second} голов)")

        val germanPosition = resolver.getTheExpensiveGermanPlayerPosition()
        println("Позиция самого дорогого немецкого игрока: $germanPosition")

        val rudestTeam = resolver.getTheRudestTeam()
        val avgRedCards = if (rudestTeam.player.isNotEmpty()) {
            rudestTeam.player.sumOf { it.red_cards }.toDouble() / rudestTeam.player.size
        } else 0.0
        println("Самая грубая команда: ${rudestTeam.name}")

        println("=".repeat(60))
        println("Создание визуализаций")
        println("=".repeat(60))

        ChartBuilder.saveAllCharts(resolver)

        println("Созданные графики:")
        println("position_distribution.png - Круговая диаграмма распределения по позициям")
        println("team_values.png - Столбчатая диаграмма топ команд по стоимости")
        println("goals_vs_cost.png - Точечная диаграмма зависимости голов от стоимости")
        println("nationality_distribution.png - Столбчатая диаграмма распределения по странам")

        println("=".repeat(60))
        println("Анализ данных и визуализация завершены!")
        println("=".repeat(60))

    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
        e.printStackTrace()

        println("Проверьте:")
        println("Файл data.csv находится в папке src/main/resources/")
        println("Добавлены зависимости JFreeChart в build.gradle.kts")
        println("Файл имеет правильный формат CSV с разделителем ';'")
    }
}