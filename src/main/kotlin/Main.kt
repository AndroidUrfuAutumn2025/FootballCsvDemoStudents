import resolver.Resolver
import utils.ChartBuilder

fun main() {
    val resolver = Resolver()

    println("1. Количество игроков без агентства: ${resolver.getCountWithoutAgency()}")

    val bestDefender = resolver.getBestScorerDefender()
    println("2. Лучший защитник: ${bestDefender.first} с ${bestDefender.second} голами")

    val germanPosition = resolver.getTheExpensiveGermanPlayerPosition()
    println("3. Позиция самого дорогого немецкого игрока: $germanPosition")

    val rudestTeam = resolver.getTheRudestTeam()
    println("4. Команда с наибольшим средним числом красных карточек: ${rudestTeam.name}")

    // Вариант 2: Топ-10 команд по трансферной стоимости
    println("\n=== Вариант 2: Топ-10 команд по суммарной трансферной стоимости ===")
    val topTeams = resolver.getTopTeamsByTransferCost()
    topTeams.forEachIndexed { index, (team, totalCost) ->
        println("${index + 1}. ${team.name} (${team.city}): $${"%.2f".format(totalCost)}")
    }

    // Создаем и сохраняем диаграмму
    println("\n=== Создание диаграммы... ===")
    ChartBuilder.createTopTeamsChart(topTeams)
}