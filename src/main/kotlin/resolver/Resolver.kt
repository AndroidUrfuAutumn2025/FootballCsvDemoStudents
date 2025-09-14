package resolver

import model.Player
import model.Position
import model.Team

class Resolver(private val players: List<Player>) : IResolver {
    override fun getCountWithoutAgency(): Int {
        return players.count { it.agency.isNullOrBlank() }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return players
            .filter { it.position == Position.DEFENDER && it.goals > 0 }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: ("Защитники с голами не найдены" to 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter { it.nationality.equals("Germany", ignoreCase = true) }

        return germanPlayers.maxByOrNull { it.transferCost }
            ?.position
            ?.namePosition
            ?: if (germanPlayers.isEmpty()) "Немецкие игроки не найдены" else "Позиция не указана"
    }

    override fun getTheRudestTeam(): Team {
        val teamAverages = players
            .groupBy { it.team }
            .mapNotNull { (team, teamPlayers) ->
                val averageRedCards = teamPlayers.map { it.redCards }.average()
                if (averageRedCards.isNaN()) null else team to averageRedCards
            }
            .toMap()

        return teamAverages.maxByOrNull { it.value } ?.key
            ?: throw NoSuchElementException("Не найдено команд с красными карточками")
    }

    // Дополнительный метод для получения топ-10 команд по трансферной стоимости
    fun getTop10TeamsByTransferCost(): List<Pair<Team, Long>> {
        return players.groupBy { it.team }
            .mapValues { (_, teamPlayers) -> teamPlayers.sumOf { it.transferCost } }
            .toList()
            .sortedByDescending { it.second }
            .take(10)
    }
}