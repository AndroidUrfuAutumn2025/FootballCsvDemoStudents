package resolver

import model.Player
import model.Position
import model.Team

/**
 * Реализация интерфейса IResolver для анализа игроков и команд
 *
 * @param players список всех игроков чемпионата
 */
class Resolver(
    private val players: List<Player>
) : IResolver {

    override fun getCountWithoutAgency(): Int {
        return players.count {
            it.agency == null
        }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val bestDefender = players.filter {
            it.position == Position.DEFENDER
        }.maxByOrNull {
            it.goals
        } ?: return "" to 0
        return bestDefender.name to bestDefender.goals
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germansPlayers = players.filter {
            it.nationality.equals("Germany", ignoreCase = true)
        }
        val theMostExpensivePlayer = germansPlayers.maxByOrNull {
            it.transferCost
        } ?: return ""
        return theMostExpensivePlayer.position.russianName
    }

    override fun getTheRudestTeam(): Team {
        return players.groupBy {
            it.team
        }.keys.maxByOrNull {
            it.averageRedCardsByOnePlayer
        } ?: throw NoSuchElementException("Нет команд для анализа")
    }
}
