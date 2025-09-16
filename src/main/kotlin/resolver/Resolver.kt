package resolver

import model.Player
import model.Position
import model.Team

class Resolver(private val players: List<Player>, private val teams: List<Team>) : IResolver {
    
    // Выведите количество игроков, интересы которых не представляет агенство.
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency == null }
    }
    
    // Выведите автора наибольшего числа голов из числа защитников и их количество.
    override fun getBestScorerDefender(): Pair<String, Int> {
        val defenders = players.filter { it.position == Position.DEFENDER }
        val bestDefender = defenders.maxByOrNull { it.goals }

        return if (bestDefender != null) {
            Pair(bestDefender.name, bestDefender.goals)
        } else {
            Pair("Нет защитников", 0)
        }
    }
    
    // Выведите русское название позиции самого дорогого немецкого игрока.
    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter { it.nationality == "Germany" }
        val mostExpensiveGerman = germanPlayers.maxByOrNull { it.transferCost }

        return mostExpensiveGerman?.position?.russianName ?: "Нет немецких игроков"
    }
    
    // Выберите команду с наибольшим числом удалений на одного игрока.
    override fun getTheRudestTeam(): Team {
        val teamRedCards = players.groupBy { it.team }
            .mapValues { (_, teamPlayers) ->
                val totalRedCards = teamPlayers.sumOf { it.redCards }
                val playerCount = teamPlayers.size
                if (playerCount > 0) totalRedCards.toDouble() / playerCount else 0.0
            }
        
        val rudestTeamName = teamRedCards.maxByOrNull { it.value }?.key ?: ""
        return teams.find { it.name == rudestTeamName } ?: teams.first()
    }
}