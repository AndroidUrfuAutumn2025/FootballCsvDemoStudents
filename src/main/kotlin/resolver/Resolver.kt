package resolver

import model.Player
import model.Team

class Resolver(val players : List<Player>, val teams : List<Team>) : IResolver{
    override fun getCountWithoutAgency(): Int {
        var countAgency = 0
        for (player in players){
            if(player.agency.equals("")){
                countAgency++
            }
        }
        return countAgency
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        var bestDef = Pair(" ", 0)
        var goals = 0;
        for (player in players){
            if(player.position.equals("DEFENDER")){
                if(goals < player.heads){
                    bestDef = player.name to player.heads
                    goals = player.heads
                }
            }
        }
        return bestDef
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        var transfer = 0
        var nameBestPosition = ""
        for (player in players){
            if(player.nationality.equals("Germany")){
                if(transfer < player.transferValue){
                    transfer = player.transferValue
                    nameBestPosition = player.position
                }
            }
        }
        if(nameBestPosition.equals("FORWARD")){
            nameBestPosition = "Нападающий"
        }
        return nameBestPosition
    }

    override fun getTheRudestTeam(): Team {
        return players
            .groupBy { it.team }
            .maxByOrNull { (_, teamPlayers) ->
                teamPlayers.sumOf { it.redCards }.toDouble() / teamPlayers.size
            }?.key
            ?: throw IllegalArgumentException("Список игроков пуст")
    }

}