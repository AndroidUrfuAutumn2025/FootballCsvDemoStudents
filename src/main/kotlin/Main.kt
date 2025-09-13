/**
 * Главная функция приложения для анализа данных о футболистах
 */
fun main(args: Array<String>) {
    println("Yeah rock!")

    try {
        val players = parser.CsvParser.parsePlayers("src/main/resources/fakePlayers.csv")
        println("Успешно загружено ${players.size} игроков")

        val resolver = resolver.Resolver(players)

        println("\n=== РЕЗУЛЬТАТЫ ВЫПОЛНЕНИЯ ЗАДАНИЙ ===")

        val withoutAgency = resolver.getCountWithoutAgency()
        println("1. Игроков без агентства: $withoutAgency")

        val bestScorer = resolver.getBestScorerDefender()
        println("2. Лучший бомбардир среди защитников: ${bestScorer.first} (голов: ${bestScorer.second})")

        val germanPosition = resolver.getTheExpensiveGermanPlayerPosition()
        println("3. Позиция самого дорогого немецкого игрока: $germanPosition")

        val rudestTeam = resolver.getTheRudestTeam()
        println("4. Команда с наибольшим средним числом красных карточек: ${rudestTeam.name}")
        println("   Среднее количество красных карточек: ${"%.2f".format(rudestTeam.averageRedCards)}")
        println("   Город: ${rudestTeam.city}")
        println("   Количество игроков: ${rudestTeam.players.size}")

        val visualizer = visualization.Visualizer(resolver)
        visualizer.printNationalityDistribution()
        visualizer.createNationalityPieChart()

    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
        e.printStackTrace()
    }
}