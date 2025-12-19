package resolver

import model.Position
import model.Team

interface IResolver {
    // 1. Количество игроков без агентства
    fun getCountWithoutAgency(): Int

    // 2. Имя и голы лучшего защитника
    fun getBestScorerDefender(): Pair<String, Int>

    // 3. Позиция самого дорогого немецкого игрока
    fun getTheExpensiveGermanPlayerPosition(): String

    // 4. Команда с наибольшим средним числом красных карточек
    fun getTheRudestTeam(): Team

    // ДОПОЛНИТЕЛЬНО: Доли игроков по позициям (для визуализации)
    fun getPositionsShare(): Map<Position, Double>
}