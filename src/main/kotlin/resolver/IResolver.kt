package resolver

import model.Team

interface IResolver {
    // Выведите количество игроков, интересы которых не представляет агенство.
    fun getCountWithoutAgency(): Int

    // Выведите автора наибольшего числа голов из числа защитников и их количество.
    fun getBestScorerDefender(): Pair<String, Int>

    // Выведите русское название позиции самого дорогого немецкого игрока.
    fun getTheExpensiveGermanPlayerPosition(): String

    // Выберите команду с наибольшим средним числом красных карточек на одного игрока.
    fun getTheRudestTeam(): Team

    // Вариант 2: Топ-10 команд по суммарной трансферной стоимости
    fun getTopTeamsByTransferCost(limit: Int = 10): List<Pair<Team, Double>>
}