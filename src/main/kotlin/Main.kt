import csv.reader.CsvReader
import resolver.Resolver

fun main(args: Array<String>) {
    try {
        val csvReader = CsvReader()

        val players = csvReader.readLines(
            filePath = "src/main/resources/fakePlayers.csv",
            parser = PlayerCsvParser()
        )

        val resolver = Resolver(players)

        val countWithoutAgency = resolver.getCountWithoutAgency()
        val bestScorerDefender = resolver.getBestScorerDefender()
        val germanPlayerPosition = resolver.getTheExpensiveGermanPlayerPosition()
        val rudestTeam = resolver.getTheRudestTeam()


        println("""
            1. Игроков без агенства: ${countWithoutAgency}
            2. Лучший бомбардиро среди защитников: ${bestScorerDefender.first} c ${bestScorerDefender.second} голами
            3. Позиция самого дорогого немецкого игрока: $germanPlayerPosition
            4. Команда с наибольшим средним числом красных карточек: ${rudestTeam.name} из города ${rudestTeam.city}
        """.trimIndent())


        val goalToCost = resolver.getForwardsGoalToCost()

        println("\nЗависимость забитых голов к стоимости: ")

        goalToCost.forEach {
            (goals, cost) -> println("$goals --> ${"%,d".format(cost)} $")
        }

        GoalsToCostVizualizer.visualize(goalToCost)

    } catch (e: Exception) {
        println("Ошибка: ${e.message}")
        e.printStackTrace()
    }
}