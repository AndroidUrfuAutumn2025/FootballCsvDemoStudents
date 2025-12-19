package resolver

import model.Player
import model.Position
import model.Team

class Resolver(private val players: List<Player>) : IResolver {

    // 1. Считаем игроков, у которых agency == null
    override fun getCountWithoutAgency(): Int = players.count { it.agency == null }

    // 2. Находим защитника с максимальным количеством голов
    override fun getBestScorerDefender(): Pair<String, Int> = players
        .asSequence()
        .filter { it.position == Position.DEFENDER }
        .maxByOrNull { it.goals }
        ?.let { it.name to it.goals }  // Возвращаем пару (имя, голы)
        ?: ("" to 0)  // Если защитников нет

    // 3. Находим позицию самого дорогого немецкого игрока
    override fun getTheExpensiveGermanPlayerPosition(): String {
        val pos = players
            .asSequence()
            .filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost }
            ?.position
            ?: return ""  // Если немецких игроков нет

        // Преобразуем enum в русское название
        return when (pos) {
            Position.GOALKEEPER -> "вратарь"
            Position.DEFENDER -> "защитник"
            Position.MIDFIELD -> "полузащитник"
            Position.FORWARD -> "нападающий"
        }
    }

    // 4. Находим команду с наибольшим средним числом красных карточек
    override fun getTheRudestTeam(): Team = players
        .groupBy { it.team }  // Группируем игроков по командам
        .mapValues { (_, teamPlayers) ->
            val totalReds = teamPlayers.sumOf { it.redCards }
            totalReds.toDouble() / teamPlayers.size  // Среднее значение
        }
        .maxByOrNull { it.value }  // Находим максимальное среднее
        ?.key  // Возвращаем команду (ключ мапы)
        ?: Team("", "")  // Если игроков нет

    // Дополнительно: считаем доли позиций
    override fun getPositionsShare(): Map<Position, Double> {
        val total = players.size.takeIf { it > 0 } ?: return emptyMap()
        return players
            .groupBy { it.position }  // Группируем по позициям
            .mapValues { (_, list) -> list.size.toDouble() / total }
    }
}