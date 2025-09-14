package resolver

import model.Team

/**
 * Интерфейса IResolver, который показывает поставленные задачи
 */
interface IResolver {

    /**
     * Вывод количества игроков, интересы которых не представляет агентство.
     */
    fun getCountWithoutAgency(): Int

    /**
     * Вывод автора наибольшего числа голов из числа защитников и их количество.
     */
    fun getBestScorerDefender(): Pair<String, Int>

    /**
     * Вывод русского названия позиции самого дорогого немецкого игрока
     */
    fun getTheExpensiveGermanPlayerPosition(): String

    /**
     * Вывод команды с наибольшим средним числом красных карточек на одного игрока
     */
    fun getTheRudestTeam(): Team
}