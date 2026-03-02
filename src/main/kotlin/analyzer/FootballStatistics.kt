package analyzer

import model.Team

interface FootballStatistics {
    
    // Выведите количество игроков, интересы которых не представляет агенство.
    fun calculatePlayersWithoutAgency(): Int
    
    // Выведите автора наибольшего числа голов из числа защитников и их количество.
    fun findTopScoringDefender(): Pair<String, Int>
    
    // Выведите русское название позиции самого дорогого немецкого игрока.
    fun getMostExpensiveGermanPlayerPosition(): String
    
    // Выберите команду с наибольшим числом удалений на одного игрока.
    fun findTeamWithHighestRedCardAverage(): Team
}
