package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>, private val teams: List<Team>) : IResolver {
    
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency == null }
    }
    
    override fun getBestScorerDefender(): Pair<String, Int> {
        val bestDefender = players
            .filter { it.position == "DEFENDER" }
            .maxByOrNull { it.goals }
        
        return if (bestDefender != null) {
            Pair(bestDefender.name, bestDefender.goals)
        } else {
            Pair("Не найдено", 0)
        }
    }
    
    override fun getTheExpensiveGermanPlayerPosition(): String {
        val expensiveGermanPlayer = players
            .filter { it.nationality == "Germany" }
            .maxByOrNull { it.transferCost }
        
        return when (expensiveGermanPlayer?.position) {
            "FORWARD" -> "Нападающий"
            "DEFENDER" -> "Защитник"
            "MIDFIELD" -> "Полузащитник"
            "GOALKEEPER" -> "Вратарь"
            else -> "Неизвестно"
        }
    }
    
    override fun getTheRudestTeam(): Team {
        return teams.maxByOrNull { it.averageRedCards } ?: teams.first()
    }

    fun getForwards(): List<Player> {
        return players.filter { it.position == "FORWARD" }
    }
    
    fun getForwardsGoalsVsTransferCost(): List<Pair<Long, Int>> {
        return getForwards().map { Pair(it.transferCost, it.goals) }
    }
}