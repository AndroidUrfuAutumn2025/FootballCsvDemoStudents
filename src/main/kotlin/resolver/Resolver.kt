package resolver
import model.Team
import model.Player

class Resolver(private val teams: List<Team>) : IResolver {

    // Карта для перевода позиций с английского на русский
    private val positionTranslations = mapOf(
        "GOALKEEPER" to "Вратарь",
        "DEFENDER" to "Защитник",
        "MIDFIELD" to "Полузащитник",
        "FORWARD" to "Нападающий",
        "GOALKEEPER" to "Вратарь",
        "DEFENDER" to "Защитник",
        "MIDFIELD" to "Полузащитник",
        "FORWARD" to "Нападающий"
    )

    // Функция для перевода позиции
    private fun translatePosition(englishPosition: String): String {
        return positionTranslations[englishPosition.toUpperCase()] ?: englishPosition
    }

    override fun getCountWithoutAgency(): Int {
        return teams.flatMap { it.player }
            .count { player ->
                player.agency.isBlank()
            }
    }

    override fun getBestScorerDefender(): Pair<String, Int> {
        return teams.flatMap { it.player }
            .filter { player ->
                player.position.contains("defender", ignoreCase = true)
            }
            .maxByOrNull { it.goals }
            ?.let { Pair(it.name, it.goals) }
            ?: Pair("Защитники не найдены", 0)
    }

    override fun getTheExpensiveGermanPlayerPosition(): String {
        val germanPlayer = teams.flatMap { it.player }
            .filter { player ->
                player.nationality.contains("germany", ignoreCase = true)
            }
            .maxByOrNull { it.transfer_cost }

        return germanPlayer?.let { translatePosition(it.position) } ?: "Немецкие игроки не найдены"
    }

    override fun getTheRudestTeam(): Team {
        return teams.maxByOrNull { team ->
            if (team.player.isEmpty()) 0.0
            else team.player.sumOf { it.red_cards }.toDouble() / team.player.size
        } ?: throw IllegalStateException("Команды не найдены")
    }

    // Вариант 1: Доля игроков по позициям (с переводом)
    fun getPositionDistribution(): Map<String, Double> {
        val allPlayers = teams.flatMap { it.player }
        val totalPlayers = allPlayers.size.toDouble()

        if (totalPlayers == 0.0) return emptyMap()

        return allPlayers.groupBy { translatePosition(it.position.trim()) }
            .mapValues { (_, players) ->
                (players.size / totalPlayers) * 100.0
            }
            .toList()
            .sortedByDescending { (_, percentage) -> percentage }
            .toMap()
    }

    fun getPositionDistributionFormatted(): String {
        val distribution = getPositionDistribution()
        if (distribution.isEmpty()) return "Нет данных об игроках"

        val result = StringBuilder("РАСПРЕДЕЛЕНИЕ ИГРОКОВ ПО ПОЗИЦИЯМ:\n")
        result.append("=".repeat(50)).append("\n")

        distribution.forEach { (position, percentage) ->
            result.append("${position.padEnd(25)}: ${"%.2f".format(percentage)}%\n")
        }

        result.append("=".repeat(50)).append("\n")
        result.append("Всего игроков: ${teams.sumOf { it.player.size }}\n")
        result.append("Уникальных позиций: ${distribution.size}")

        return result.toString()
    }

    // Вариант 2: Топ-10 команд по трансферной стоимости
    fun getTopTeamsByTransferValue(limit: Int = 10): List<Pair<String, Long>> {
        return teams.map { team ->
            val totalValue = team.player.sumOf { it.transfer_cost.toLong() }
            Pair(team.name, totalValue)
        }
            .sortedByDescending { it.second }
            .take(limit)
    }

    fun getTopTeamsByTransferValueFormatted(): String {
        val topTeams = getTopTeamsByTransferValue()
        if (topTeams.isEmpty()) return "Нет данных о командах"

        val result = StringBuilder("ТОП-10 КОМАНД ПО ТРАНСФЕРНОЙ СТОИМОСТИ:\n")
        result.append("=".repeat(60)).append("\n")

        topTeams.forEachIndexed { index, (team, value) ->
            val formattedValue = "%,d".format(value)
            result.append("${(index + 1).toString().padEnd(3)}. ${team.padEnd(25)}: $formattedValue €\n")
        }

        result.append("=".repeat(60))
        return result.toString()
    }

    // Вариант 3: Зависимость голов от стоимости для нападающих (с переводом)
    fun getGoalsVsCostForForwards(): List<Triple<String, Int, Int>> {
        return teams.flatMap { it.player }
            .filter { player ->
                player.position.contains("forward", ignoreCase = true)
            }
            .map { player ->
                Triple(player.name, player.transfer_cost, player.goals)
            }
            .sortedByDescending { it.second }
    }

    fun getGoalsVsCostForForwardsFormatted(): String {
        val data = getGoalsVsCostForForwards()
        if (data.isEmpty()) return "Нападающие не найдены"

        val result = StringBuilder("ЗАВИСИМОСТЬ ГОЛОВ ОТ ТРАНСФЕРНОЙ СТОИМОСТИ (НАПАДАЮЩИЕ):\n")
        result.append("=".repeat(80)).append("\n")
        result.append("Игрок".padEnd(25) + "Стоимость".padEnd(15) + "Голы".padEnd(10) + "Голы/Млн€\n")
        result.append("-".repeat(80)).append("\n")

        data.forEach { (name, cost, goals) ->
            val costInMillions = cost / 1_000_000.0
            val goalsPerMillion = if (costInMillions > 0) goals / costInMillions else 0.0
            val formattedCost = "%,d".format(cost)

            result.append("${name.padEnd(25)}${formattedCost.padEnd(15)}${goals.toString().padEnd(10)}")
            result.append("${"%.2f".format(goalsPerMillion)}\n")
        }

        result.append("=".repeat(80))
        return result.toString()
    }

    // Вариант 4: Доля игроков по странам
    fun getNationalityDistribution(): Map<String, Double> {
        val allPlayers = teams.flatMap { it.player }
        val totalPlayers = allPlayers.size.toDouble()

        if (totalPlayers == 0.0) return emptyMap()

        return allPlayers.groupBy { it.nationality.trim() }
            .mapValues { (_, players) ->
                (players.size / totalPlayers) * 100.0
            }
            .toList()
            .sortedByDescending { (_, percentage) -> percentage }
            .toMap()
    }

    fun getNationalityDistributionFormatted(): String {
        val distribution = getNationalityDistribution()
        if (distribution.isEmpty()) return "Нет данных об игроках"

        val result = StringBuilder("РАСПРЕДЕЛЕНИЕ ИГРОКОВ ПО СТРАНАМ:\n")
        result.append("=".repeat(50)).append("\n")

        distribution.forEach { (country, percentage) ->
            result.append("${country.padEnd(35)}: ${"%.2f".format(percentage)}%\n")
        }

        result.append("=".repeat(50)).append("\n")
        result.append("Всего игроков: ${teams.sumOf { it.player.size }}\n")
        result.append("Уuniqueльных стран: ${distribution.size}")

        return result.toString()
    }

    // Дополнительный метод: получить перевод позиции для любого игрока
    fun getTranslatedPosition(playerName: String): String {
        val player = teams.flatMap { it.player }
            .find { it.name.equals(playerName, ignoreCase = true) }

        return player?.let { translatePosition(it.position) } ?: "Игрок не найден"
    }
}