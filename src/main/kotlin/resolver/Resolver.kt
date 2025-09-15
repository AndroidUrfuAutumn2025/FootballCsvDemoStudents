package resolver

import model.Player
import model.Roles
import model.Team

class Resolver(val Players: List<Player>) : IResolver{

    override fun getCountWithoutAgency(): Int {
        return Players.count {it.Agency.isEmpty() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return Players.filter{ it.Role == Roles.DEFENDER }.maxBy{ it.Goals }.let { Pair(it.Name, it.Goals) }
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        return Players.filter { it.Nationality == "Germany" }.maxBy { it.Cost }.Role.trans
    }

    override fun getTheRudestTeam(): Team {

        return Players.groupBy { it.team }.map { Pair(it.key, it.value.map { it.RedCards }.average())}.maxBy { it.second }.first
    }

}