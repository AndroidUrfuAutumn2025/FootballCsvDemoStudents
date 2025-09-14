package parser

import model.Player
import model.Team
import java.io.File

object CsvParser {
    fun parsePlayersAndTeams(csvFile: File): Pair<List<Player>, List<Team>> {
        val lines = csvFile.readLines().drop(1) // Пропускаем заголовок
        val rawData = mutableListOf<Map<String, String>>()
        val teamNamesToCities = mutableMapOf<String, String>()

        // Парсим строки в мапу ключ-значение
        lines.forEach { line ->
            val values = line.split(";")
            if (values.size >= 11) {
                val map = mapOf(
                    "Name" to values[0],
                    "Team" to values[1],
                    "City" to values[2],
                    "Position" to values[3],
                    "Nationality" to values[4],
                    "Agency" to values[5],
                    "Transfer cost" to values[6],
                    "Participations" to values[7],
                    "Goals" to values[8],
                    "Assists" to values[9],
                    "Yellow cards" to values[10],
                    "Red cards" to values[11]
                )
                rawData.add(map)
                // Сохраняем соответствие названия команды и города
                teamNamesToCities[values[1]] = values[2]
            }
        }

        // Сначала создаем все команды без игроков
        val teams = teamNamesToCities.map { (name, city) ->
            Team(name, city, mutableListOf())
        }.associateBy { it.name }

        // Затем создаем игроков, подвязывая команды
        val players = rawData.map { data ->
            Player(
                name = data["Name"] ?: "",
                team = teams[data["Team"]]!!,
                position = data["Position"] ?: "",
                nationality = data["Nationality"] ?: "",
                agency = data["Agency"]?.takeIf { it.isNotBlank() },
                transferCost = data["Transfer cost"]?.toLongOrNull() ?: 0L,
                participations = data["Participations"]?.toIntOrNull() ?: 0,
                goals = data["Goals"]?.toIntOrNull() ?: 0,
                assists = data["Assists"]?.toIntOrNull() ?: 0,
                yellowCards = data["Yellow cards"]?.toIntOrNull() ?: 0,
                redCards = data["Red cards"]?.toIntOrNull() ?: 0
            )
        }

        // Добавляем игроков в соответствующие команды
        players.forEach { player ->
            teams[player.team.name]?.players?.add(player)
        }

        return Pair(players, teams.values.toList())
    }

    fun parsePlayers(csvFile: File): List<Player> {
        return parsePlayersAndTeams(csvFile).first
    }

    fun parseTeams(csvFile: File): List<Team> {
        return parsePlayersAndTeams(csvFile).second
    }
}