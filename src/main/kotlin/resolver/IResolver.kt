package resolver

import model.Team
import model.Person
import javax.lang.model.type.NullType
import kotlin.math.max

interface IResolver {

    // Выведите количество игроков, интересы которых не представляет агенство.
    fun getCountWithoutAgency(): Int


    // Выведите автора наибольшего числа голов из числа защитников и их количество.
    fun getBestScorerDefender(): Pair<String, Int>


    // Выведите русское название позиции самого дорогого немецкого игрока.
    fun getTheExpensiveGermanPlayerPosition(): String



    // Выведите название команды с наибольшим количеством красных карточек на одного игрока
    fun getTheRudestTeam(): Team

}