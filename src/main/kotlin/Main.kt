import model.Team
import model.readCsv
import model.readCSV
import resolver.IResolver

fun main(args: Array<String>) {
    //object {} - создание анонимного объекта
    //.javaClass - получение объекта Class для этого объекта
    //.getResourceAsStream("/fakePlayers.csv") - метод для получения потока данных
    val inputStream = object {}.javaClass.getResourceAsStream("/fakePlayers.csv")
    if (inputStream != null) {
        val players = readCSV(inputStream)
        players.forEach { println("Игрок: ${it.name}") }
    } else {
        println("Файл fakePlayers.csv не найден в resources!")

    }
    print("------------------------------------------------------------------------------")
    val inputStream2 = object {}.javaClass.getResourceAsStream("/fakePlayers.csv")
    if (inputStream2 != null){
        val teams = readCsv(inputStream2)
        teams.forEach{ println("Команда игрока ${it.name} это ${it.teamName}")}
    } else {
        println("Файл fakePlayers.csv не найден в resources!")
    }
    print("------------------------------------------------------------------------------")
    val resolver = object : IResolver {

    }
    val res1 = resolver.getCountWithoutAgency()
    println("Количество игроков без агентства: $res1")

    println("------------------------------------------------------------------------------")

    val res2 = resolver.getBestScorerDefender()
    println("Защитник с наибольшими забитыми голами - : $res2")

    println("------------------------------------------------------------------------------")

    val res3 = resolver.getTheExpensiveGermanPlayerInfo()
    println(res3)

    println("------------------------------------------------------------------------------")

    val res4 = resolver.getTheRudestTeam()
    println("команда с наибольшими средними удалениями на игрока - : $res4")


}