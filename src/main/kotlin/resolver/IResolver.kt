package resolver

import model.Team
import model.Position

interface IResolver {

    // Выведите количество игроков, интересы которых не представляет агенство.
    fun getCountWithoutAgency(): Int

    // Выведите автора наибольшего числа голов из числа защитников и их количество.
    fun getBestScorerDefender(): Pair<String, Int>

    // Выведите русское название позиции самого дорогого немецкого игрока.
    fun getTheExpensiveGermanPlayerPosition(): String

    // Выберите команду с наибольшим числом удалений на одного игрока.
    fun getTheRudestTeam(): Team

    // Покажите доли игроков по позициям (0.0..1.0) по отношению ко всем игрокам
    fun getPositionsShare(): Map<Position, Double>
}