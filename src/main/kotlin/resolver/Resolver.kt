package resolver

import model.Player
import model.Team

class Resolver(private val players: List<Player>) : IResolver {

    private enum class Position(val english: String, val russian: String) {
        GOALKEEPER("GOALKEEPER", "Вратарь"),
        DEFENDER("DEFENDER", "Защитник"),
        MIDFIELD("MIDFIELD", "Полузащитник"),
        FORWARD("FORWARD", "Нападающий"),
        UNKNOWN("UNKNOWN", "Неизвестно");

        companion object {
            fun fromEnglish(englishPosition: String): Position {
                return values().find { it.english.equals(englishPosition, ignoreCase = true) } ?: UNKNOWN
            }
        }
    }

    override fun getCountWithoutAgency(): Int {
        return players.count {
            it.agency.isBlank() || it.agency == "N/A" || it.agency == "-" || it.agency == "null"
        }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        val defenders = players.filter {
            it.position.equals("DEFENDER", ignoreCase = true) ||
                    it.position.contains("DEFENDER", ignoreCase = true)
        }

        if (defenders.isEmpty()) return Pair("Защитники не обнаружены", 0)

        val bestDefender = defenders.maxByOrNull { it.goals }
        return Pair(bestDefender?.name ?: "Unknown", bestDefender?.goals ?: 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayers = players.filter {
            it.nationality.equals("Germany", ignoreCase = true) ||
                    it.nationality.contains("Germany", ignoreCase = true)
        }

        if (germanPlayers.isEmpty()) return "Немецкие игроки не обнаружены"

        val mostExpensive = germanPlayers.maxByOrNull { it.transferCost }
        return translatePositionToRussian(mostExpensive?.position ?: "Unknown")
    }

    override fun getTheRudestTeam(): Team {
        val teamRedCards = players.groupBy { it.team }
            .filter { it.value.isNotEmpty() }
            .mapValues { (_, players) ->
                val totalRedCards = players.sumOf { it.redCards }
                totalRedCards.toDouble() / players.size
            }

        return teamRedCards.maxByOrNull { it.value }?.key ?: Team("Unknown", "Unknown")
    }

    private fun translatePositionToRussian(position: String): String {
        return Position.fromEnglish(position).russian
    }

    data class TeamTransferStats(
        val team: Team,
        val totalTransferCost: Long,
        val playerCount: Int,
        val averageTransferCost: Double
    )

    fun getTopTeamsByTransferCost(limit: Int = 10): List<TeamTransferStats> {
        return players.groupBy { it.team }
            .map { (team, players) ->
                val totalCost = players.sumOf { it.transferCost.toLong() }
                val count = players.size
                val averageCost = if (count > 0) totalCost.toDouble() / count else 0.0
                TeamTransferStats(team, totalCost, count, averageCost)
            }
            .sortedByDescending { it.totalTransferCost }
            .take(limit)
    }

    fun printTopTeamsByTransferCost(limit: Int = 10) {
        val topTeams = getTopTeamsByTransferCost(limit)

        println("\n=== ТОП-$limit КОМАНД ПО СУММАРНОЙ ТРАНСФЕРНОЙ СТОИМОСТИ ===")
        println("№   Команда".padEnd(30) + "Город".padEnd(20) + "Игроков".padEnd(10) +
                "Суммарная стоимость".padEnd(25) + "Средняя стоимость")
        println("-".repeat(90))

        topTeams.forEachIndexed { index, stats ->
            val number = (index + 1).toString().padEnd(3)
            val teamName = stats.team.name.padEnd(27)
            val city = stats.team.city.padEnd(18)
            val players = stats.playerCount.toString().padEnd(8)
            val totalCost = formatMoney(stats.totalTransferCost).padEnd(23)
            val avgCost = formatMoney(stats.averageTransferCost.toLong())

            println("$number $teamName $city $players $totalCost $avgCost")
        }
    }

    private fun formatMoney(amount: Long): String {
        return when {
            amount >= 1_000_000_000 -> "%.1f млрд".format(amount / 1_000_000_000.0)
            amount >= 1_000_000 -> "%.1f млн".format(amount / 1_000_000.0)
            amount >= 1_000 -> "%.1f тыс".format(amount / 1_000.0)
            else -> "$amount"
        } + " €"
    }
}