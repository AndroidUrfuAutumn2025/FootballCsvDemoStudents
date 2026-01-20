package resolver

import model.Player
import model.Position
import model.Team

class Resolver (val playersList: List<Player>): IResolver {

    override fun getCountWithoutAgency(): Int {
        return playersList.count { it.agency == null }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val bestScorerDefender = playersList.filter {it.position == Position.DEFENDER }.maxBy { it.goals }
        return Pair<String, Int>(bestScorerDefender.name, bestScorerDefender.goals)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val theExpensiveGermanPlayer = playersList.filter { it.nationality == "Germany" }.maxBy { it.transferCost }
        return engToRusPosition(theExpensiveGermanPlayer.position.toString())
    }

    override fun getTheRudestTeam(): Team {
        val rudestTeam = playersList
            .groupBy { it.team.name }
            .values
            .maxByOrNull {
                it.sumOf { it.redCards } / it.size
            }?.first()?.team

        if (rudestTeam == null) return Team("None", "None")


        return rudestTeam
    }

    private fun engToRusPosition(engPos: String): String{
        return when (engPos){
            "DEFENDER" -> "ЗАЩИТНИК"
            "FORWARD" -> "НАПАДАЮЩИЙ"
            "MIDFIELD" -> "ПОЛУЗАЩИТНИК"
            "GOALKEEPER" -> "ВРАТАРЬ"
            else -> engPos
        }

    }
}