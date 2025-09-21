import model.Player
import model.Team
import model.readCsv
import resolver.Resolver

fun main(args: Array<String>) {
    val resolver = Resolver()

    // Чтение игроков через CSVParser
    val inputStream = object {}.javaClass.getResourceAsStream("/fakePlayers.csv")
    if (inputStream != null) {
        val players = resolver.readCSVFile(inputStream)
        players.forEach { println("Игрок: ${it[0]}") }
    } else {
        println("Файл fakePlayers.csv не найден в resources!")
    }

    print("------------------------------------------------------------------------------")

    // Чтение команд (предполагаю, что это отдельная функция)
    val inputStream2 = object {}.javaClass.getResourceAsStream("/fakePlayers.csv")
    if (inputStream2 != null){
        val teams = readCsv(inputStream2)
        teams.forEach{ println("Страна команды ${it.teamName} это ${it.city}")}
    } else {
        println("Файл fakePlayers.csv не найден в resources!")
    }

    print("------------------------------------------------------------------------------")

    val res1 = resolver.getCountWithoutAgency()
    println("Количество игроков без агентства: $res1")

    println("------------------------------------------------------------------------------")

    val res2 = resolver.getBestScorerDefender()
    println("Защитник с наибольшими забитыми голами - ${res2.first}: ${res2.second} голов")

    println("------------------------------------------------------------------------------")

    val res3 = resolver.getTheExpensiveGermanPlayerInfo()
    println(res3)

    println("------------------------------------------------------------------------------")

    val res4 = resolver.getTheRudestTeam()
    println("Команда с наибольшими средними удалениями на игрока - : $res4")

    println("------------------------------------------------------------------------------")

    val goalTo = resolver.getForwardsGoalToCost()

    println("\nЗависимость забитых голов к стоимости: ")

    goalTo.forEach {
            (goals, cost) -> println("$goals --> ${"%,d".format(cost)} $")
    }

    goalToCost.visualize(goalTo)

}