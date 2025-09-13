package resolver

import model.Player
import model.Team

class Resolver(val players: List<Player>): IResolver{
    // Выведите количество игроков, интересы которых не представляет агенство.
    override fun getCountWithoutAgency(): Int{
        return players.count { it.agency.isEmpty() }
    }

    // Выведите автора наибольшего числа голов из числа защитников и их количество.
    override fun getBestScorerDefender(): Pair<String, Int> {
        val defender = players.filter { it.position == "DEFENDER" }.maxByOrNull { it.goals }

        return if (defender != null) {
            defender.name to defender.goals
        } else {
            "Нет защитников" to 0
        }
    }

    // Выведите русское название позиции самого дорогого немецкого игрока.
    override fun getTheExpensiveGermanPlayerPosition(): String{
        val player = players.filter { it.nationality == "Germany" }.maxByOrNull { it.transferCost }

        val positionsToRus: Map<String, String> = mapOf(
            "DEFENDER" to "Защитник",
            "FORWARD" to "Нападающий",
            "GOALKEEPER" to "Вратарь",
            "MIDFIELD" to "Полузащитник"
        )

        return if (player != null) {
            positionsToRus.getOrDefault(player.position, "Такой позиции не существует")
        } else {
            "Немецкие игроки не найдены"
        }
    }

    // Выберите команду с наибольшим числом удалений на одного игрока.
    override fun getTheRudestTeam(): Team {
        val teams = players.groupBy { it.team }.map { (teamName, players) -> Team(teamName, players) }
        return teams.maxBy { team -> team.players.map {it.redCards}.average() }
    }

    fun getTop10TeamsByTransferCost(): List<Pair<String, Int>>{
        val topTeams = players.groupBy { it.team }
            .mapValues { (_, players) -> players.sumOf { it.transferCost } }
            .toList().sortedByDescending { it.second }.take(10)

        return topTeams
    }

}