import parser.CsvParser
import resolver.Resolver
import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.JFreeChart
import org.jfree.data.general.DefaultPieDataset
import java.io.File

fun main() {
    println("=".repeat(60))
    println(" ФУТБОЛЬНАЯ АНАЛИТИКА")
    println("=".repeat(60))

    // 1. ЗАГРУЖАЕМ ДАННЫЕ
    println(" Загрузка данных из CSV файла...")

    val resource = Thread.currentThread().contextClassLoader.getResource("fakePlayers.csv")
        ?: error(" Файл fakePlayers.csv не найден в папке resources!")

    val lines = resource.readText().lines()
    val players = CsvParser.parsePlayers(lines)

    if (players.isEmpty()) {
        println(" Не удалось загрузить игроков. Проверь CSV файл.")
        return
    }

    println("✅ Успешно загружено ${players.size} игроков")
    println("-".repeat(40))

    // 2. РЕШАЕМ ЗАДАЧИ
    val resolver = Resolver(players)

    println("🎯 РЕЗУЛЬТАТЫ:")
    println("1. Игроков без агентства: ${resolver.getCountWithoutAgency()}")

    val (defenderName, defenderGoals) = resolver.getBestScorerDefender()
    println("2. Лучший защитник: $defenderName ($defenderGoals голов)")

    val germanPosition = resolver.getTheExpensiveGermanPlayerPosition()
    println("3. Позиция дорогого немца: $germanPosition")

    val rudestTeam = resolver.getTheRudestTeam()
    println("4. Самая грубая команда: ${rudestTeam.name} (${rudestTeam.city})")

    println("-".repeat(40))

    // 3. ВИЗУАЛИЗАЦИЯ - создаём PNG файл
    println("📊 СОЗДАНИЕ ГРАФИКА...")

    try {
        // Создаём данные для круговой диаграммы
        val dataset = DefaultPieDataset<String>()

        // Получаем доли позиций из Resolver
        val positionsShare = resolver.getPositionsShare()

        // Добавляем данные в dataset
        positionsShare.forEach { (position, share) ->
            val percentage = (share * 100).toInt()
            val russianName = when (position) {
                model.Position.GOALKEEPER -> "Вратари"
                model.Position.DEFENDER -> "Защитники"
                model.Position.MIDFIELD -> "Полузащитники"
                model.Position.FORWARD -> "Нападающие"
            }
            dataset.setValue("$russianName ($percentage%)", share)
        }

        // Создаём круговую диаграмму
        val chart: JFreeChart = ChartFactory.createPieChart(
            "Распределение игроков по позициям",
            dataset,
            true,
            true,
            false
        )

        // Сохраняем в PNG файл
        val outputFile = File("src/main/resources/football_chart.png")
        ChartUtils.saveChartAsPNG(outputFile, chart, 800, 600)

        if (outputFile.exists()) {
            println("\n PNG ФАЙЛ УСПЕШНО СОЗДАН!")
            println(" Имя файла: football_chart.png")
            println(" Путь: ${outputFile.absolutePath}")
            println(" Размер: ${outputFile.length()} байт")
        } else {
            println(" Файл не создан (но ошибки не было)")
        }

    } catch (e: Exception) {
        println(" ОШИБКА ПРИ СОЗДАНИИ ГРАФИКА:")
        println("   ${e.message}")
    }

    // 4. Дополнительная информация для скриншота
    println("\n" + "=".repeat(60))
    println(" ДОЛИ ПОЗИЦИЙ:")
    resolver.getPositionsShare().forEach { (pos, share) ->
        val percentage = (share * 100)
        val russianName = when (pos) {
            model.Position.GOALKEEPER -> "Вратари"
            model.Position.DEFENDER -> "Защитники"
            model.Position.MIDFIELD -> "Полузащитники"
            model.Position.FORWARD -> "Нападающие"
        }
        println("   $russianName: ${"%.1f".format(percentage)}%")
    }

    println("\n Готово! Для сдачи задания:")
    println("=".repeat(60))
}