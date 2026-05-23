package analyzer

import model.Player
import model.Team

class StatisticsCalculator(
    private val allPlayers: List<Player>, 
    private val allTeams: List<Team>
) : FootballStatistics {
    
    private val positionTranslations = mapOf(
        "DEFENDER" to "Защитник",
        "FORWARD" to "Нападающий",
        "MIDFIELD" to "Полузащитник",
        "GOALKEEPER" to "Вратарь"
    )
    
    override fun calculatePlayersWithoutAgency(): Int {
        var counter = 0
        for (player in allPlayers) {
            val agencyMissing = player.agency == null || player.agency.trim().isEmpty()
            if (agencyMissing) {
                counter++
            }
        }
        return counter
    }
    
    override fun findTopScoringDefender(): Pair<String, Int> {
        val defenders = allPlayers.filter { it.position == "DEFENDER" }
        
        if (defenders.isEmpty()) {
            return Pair("Нет защитников", 0)
        }
        
        var bestDefender: Player? = null
        var maxGoals = -1
        
        for (defender in defenders) {
            if (defender.goals > maxGoals) {
                maxGoals = defender.goals
                bestDefender = defender
            }
        }
        
        return if (bestDefender != null) {
            Pair(bestDefender.name, bestDefender.goals)
        } else {
            Pair("Нет защитников", 0)
        }
    }
    
    override fun getMostExpensiveGermanPlayerPosition(): String {
        val germanPlayers = allPlayers.filter { 
            it.nationality.lowercase().trim() == "germany".lowercase() 
        }
        
        if (germanPlayers.isEmpty()) {
            return "Нет немецких игроков"
        }
        
        var mostExpensivePlayer: Player? = null
        var highestCost = -1L
        
        for (player in germanPlayers) {
            if (player.transferCost > highestCost) {
                highestCost = player.transferCost
                mostExpensivePlayer = player
            }
        }
        
        return if (mostExpensivePlayer != null) {
            val positionKey = mostExpensivePlayer.position
            positionTranslations[positionKey] ?: positionKey
        } else {
            "Не найдено"
        }
    }
    
    override fun findTeamWithHighestRedCardAverage(): Team {
        require(allTeams.isNotEmpty()) { "Список команд пуст" }
        
        var teamWithHighestAverage: Team? = null
        var maxAverage = -1.0
        
        for (team in allTeams) {
            val currentAverage = team.averageRedCardsPerPlayer
            if (currentAverage > maxAverage) {
                maxAverage = currentAverage
                teamWithHighestAverage = team
            }
        }
        
        return teamWithHighestAverage ?: throw IllegalStateException("Не удалось найти команду")
    }
}
