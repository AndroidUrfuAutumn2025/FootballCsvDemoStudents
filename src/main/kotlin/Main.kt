import resolver.Resolver

fun main(args: Array<String>) {
    val method = Resolver()
    println("Колличество игроков без агества: " + method.getCountWithoutAgency())
    println("Человек с большим числом голов в роли защитника: " + method.getBestScorerDefender().first +
            "; Колличество голов этого игрока: " + method.getBestScorerDefender().second)
    println("Название позиции самого дорогого немецкого игрока: " + method.getTheExpensiveGermanPlayerPosition())
    println("Команда с наибольшим средним числом красных карточек на одного игрока: " + method.getTheRudestTeam().nameTeam)
    PieChartWindow()
}