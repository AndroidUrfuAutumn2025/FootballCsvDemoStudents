import parser.CsvParser
import resolver.PlayerStatsResolver
import java.io.File
import java.io.IOException
import java.text.DecimalFormat
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.plot.PiePlot
import org.jfree.data.general.DefaultPieDataset
import java.awt.Font

fun main() {
    val csvFilePath = "src/main/resources/fakePlayers.csv"
    val parser = CsvParser()
    val resolver = PlayerStatsResolver()

    try {
        val players = parser.parsePlayers(csvFilePath)
        println("Всего игроков: ${players.size}\n")

        // 1. Количество игроков, интересы которых не представляет агентство
        val playersWithoutAgency = resolver.countPlayersWithoutAgency(players)
        println("1. Количество игроков без агентства: $playersWithoutAgency")

        // 2. Автор наибольшего числа голов из числа защитников и их количество
        val topDefender = resolver.getTopScoringDefender(players)
        if (topDefender != null) {
            println("2. Лучший бомбардир среди защитников: ${topDefender.first}, Голы: ${topDefender.second}")
        } else {
            println("2. Защитники с голами не найдены.")
        }

        // 3. Самый дорогой немецкий игрока
        val positionMostExpensiveGerman = resolver.getPositionOfMostExpensiveGermanPlayer(players)
        if (positionMostExpensiveGerman != null) {
            println("3. Позиция самого дорогого немецкого игрока: $positionMostExpensiveGerman")
        } else {
            println("3. Немецкие игроки не найдены.")
        }

        // 4. Команда с наибольшим средним числом красных карточек
        val teamHighestAvgRedCards = resolver.getTeamWithHighestAverageRedCards(players)
        if (teamHighestAvgRedCards != null) {
            println("4. Команда с наибольшим средним числом красных карточек: ${teamHighestAvgRedCards.name} (${teamHighestAvgRedCards.city})")
        } else {
            println("4. Команды с красными карточками не найдены или нет данных.")
        }

        // 5. Распределение игроков по странам
        val nationalityDistribution = resolver.getPlayerDistributionByNationality(players)
        println("\n5. Распределение игроков по странам (доля %):")
        if (nationalityDistribution.isNotEmpty()) {
            val df = DecimalFormat("#.##")
            nationalityDistribution.forEach { (nationality, percentage) ->
                println("- ${nationality}: ${df.format(percentage)}%")
            }
            try {
                val dataset = DefaultPieDataset<String>()
                nationalityDistribution.forEach { (nationality, percentage) ->
                    dataset.setValue(nationality as String, percentage as Number)
                }

                val chart = ChartFactory.createPieChart(
                    "Распределение игроков по странам (%)",
                    dataset,
                    true,
                    true,
                    false
                )

                val plot = chart.plot as PiePlot<*>
                plot.labelFont = Font("SansSerif", Font.PLAIN, 10)
                plot.labelGap = 0.02

                val fileName = "nationality_distribution_jfreechart.png"
                val chartFile = File(fileName)
                ChartUtils.saveChartAsPNG(chartFile, chart, 800, 600)

                println("\nГрафик сохранен в файл: ${chartFile.absolutePath}")

            } catch (e: Exception) {
                println("\nНе удалось создать или сохранить график (JFreeChart): ${e.message}")
                e.printStackTrace()
            }

        } else {
            println("Данные по национальностям отсутствуют или не удалось рассчитать.")
        }

    } catch (e: IOException) {
        println("Ошибка при чтении файла: ${e.message}")
    } catch (e: Exception) {
        println("Произошла непредвиденная ошибка: ${e.message}")
        e.printStackTrace()
    }
}