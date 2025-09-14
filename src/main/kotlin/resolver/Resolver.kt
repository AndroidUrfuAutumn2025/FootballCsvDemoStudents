package resolver

import model.Player
import model.Position
import model.Team

class Resolver(private val players: List<Player>) : IResolver {

    override fun countWithoutAgency(): Int =
        players.count { it.agency.isNullOrBlank() }

    override fun bestDefenderByGoals(): Pair<String, Int> =
        players.filter { it.position == Position.DEFENDER }
            .maxByOrNull { it.goals }
            ?.let { it.name to it.goals }
            ?: ("Защитник не найден" to 0)

    override fun mostExpensiveGermanPosition(): String =
        players.filter { it.nationality.equals("Germany", ignoreCase = true) }
            .maxByOrNull { it.transferCost }
            ?.position?.namePosition
            ?: "Нет данных о немецких игроках"

    override fun teamWithMostRedCards(): Team =
        players.groupBy { it.team }
            .maxByOrNull { (_, members) -> members.map { it.redCards }.average() }
            ?.key ?: Team("Неизвестная команда", "N/A")

    override fun nationalityDistribution(): Map<String, Int> =
        players.groupingBy { it.nationality }.eachCount()
}
