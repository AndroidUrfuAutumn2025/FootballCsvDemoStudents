package resolver

import model.Player
import model.Team
import model.enums.PlayerPosition
import model.enums.NationalityName
import model.enums.RussianPosition
import model.enums.TeamName

class Resolver (private val players: List<Player>): IResolver{
    
    override fun getCountWithoutAgency(): Int{
        return players.count { it.agency == null }
    }

    override fun getBestScorerDefender(): Pair<String, Int>{
        val bestDefender = players
            .filter { it.position == PlayerPosition.DEFENDER }
            .maxBy { it.goals }

        return Pair(bestDefender.name, bestDefender.goals)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String{
        val expensiveGerman = players
            .filter { it.nationality == NationalityName.GERMANY }
            .maxBy { it.transferCost }
        
        return RussianPosition.fromRussianPosition(expensiveGerman.position).displayName
    }

    override fun getTheRudestTeam(): Team{
        val teamRedCards = players
            .groupBy { it.team }
            .mapValues { (_, teamPlayers) ->
                teamPlayers.sumOf { it.redCards} / teamPlayers.size
            }
        
        return teamRedCards.maxBy { it.value }.key
    }
}