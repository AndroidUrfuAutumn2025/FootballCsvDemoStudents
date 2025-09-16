package resolver

import model.Player
import model.Position
import model.Team

class Resolver(private val players: List<Player>) : IResolver {
    // Выведите количество игроков, интересы которых не представляет агенство.
    override fun getCountWithoutAgency(): Int {
        return players.count() {
            it.agency.isBlank()
        }
    }

    // Выведите автора наибольшего числа голов из числа защитников и их количество.
    override fun getBestScorerDefender(): Pair<String, Int> {
        val best = players.filter { it.position == Position.DEFENDER }.maxByOrNull { it.goals ?: 0 }
        return best?.let { it.fullName to (it.goals ?: 0) } ?: ("" to 0)
    }

    // Выведите русское название позиции самого дорогого немецкого игрока.
    override fun getTheExpensiveGermanPlayerPosition(): String {
        val mostExpensive = players.filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost ?: 0 }
        return mostExpensive?.position?.russian ?: "Нет данных"
    }

    // Выберите команду с наибольшим числом удалений на одного игрока.
    override fun getTheRudestTeam(): Team {
        val teamsMap: Map<Team, List<Player>> = players.groupBy { it.team }

        val teamWithMaxRedCards = teamsMap.maxByOrNull { (_, teamPlayers) ->
            teamPlayers.map { it.redCards ?: 0 }.average()
        }?.key

        return teamWithMaxRedCards ?: throw IllegalStateException("Нет команд")
    }
}