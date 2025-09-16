package resolver

import model.Player
import model.Position
import model.Team
import parser.CsvParser

class Resolver(private val filePath: String): IResolver {

     val players: List<Player> by lazy {
        CsvParser.parsePlayers(filePath)
    }

    private val teams: List<Team> by lazy {
        CsvParser.parseTeams(players)
    }

    // Выведите количество игроков, интересы которых не представляет агенство.
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency === null }
    }

    // Выведите автора наибольшего числа голов из числа защитников и их количество.
    override fun getBestScorerDefender(): Pair<String, Int> {
        val defenders = players.filter{ it.position == Position.DEFENDER }
        val result = defenders.maxByOrNull { it.goals }

        return if (result !== null) {
            Pair(result.name, result.goals)
        } else {
            Pair("Не найден", 0)
        }
    }

    // Выведите русское название позиции самого дорогого немецкого игрока.
    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter { it.nationality == "Germany" }
        val expensivePlayer = germanPlayers.maxByOrNull { it.transferCost }

        return Position.getRussianPositionName(expensivePlayer?.position ?: Position.UNKNOWN)
    }

    // Выберите команду с наибольшим числом удалений на одного игрока.
    override fun getTheRudestTeam(): Team {
        val worstTeam = players
            .groupBy { it.team }
            .mapValues { (_, tPlayers) ->
                val countRedCards = tPlayers.sumOf { it.redCards }
                val countPlayers = tPlayers.size
                if (tPlayers.isNotEmpty()) {
                    countRedCards.toDouble() / countPlayers
                } else {
                    0.0
                }
            }
        val rudestTeam = worstTeam.maxByOrNull { it.value }?.key ?: ""
        return teams.find {it.name == rudestTeam } ?: teams.first()
    }
}

