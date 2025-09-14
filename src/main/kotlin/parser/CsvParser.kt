package parser

import model.Player
import model.Position
import model.Team
import java.io.File

/**
 * Парсер CSV файлов для преобразования данных в объекты Player
 */
object CsvParser {

    /**
     * Парсит CSV файл и возвращает список игроков.
     *
     * @param filePath путь к CSV файлу
     * @return список игроков
     * @throws IllegalArgumentException если файл не найден
     */
    fun parsePlayers(filePath: String): List<Player> {
        val file = File(filePath)
        if (!file.exists()) {
            throw IllegalArgumentException("Файл не найден: $filePath")
        }

        val teamsMap = mutableMapOf<String, Team>()
        val playersList = mutableListOf<Player>()

        file.readLines()
            .drop(1)
            .forEach { line ->
                val lineParts = line.split(";")
                if (lineParts.size < 12) {
                    return@forEach
                }

                val teamName = lineParts[1].trim()
                val teamCity = lineParts[2].trim()

                val team = teamsMap.getOrPut("$teamName|$teamCity") {
                    Team(name = teamName, city = teamCity, players = mutableListOf())
                }

                val player = Player(
                    name = lineParts[0].trim(),
                    team = team,
                    position = Position.valueOf(lineParts[3].trim().uppercase()),
                    nationality = lineParts[4].trim(),
                    agency = if (lineParts[5].isBlank()) null else lineParts[5].trim(),
                    transferCost = lineParts[6].toLongOrNull() ?: 0L,
                    participations = lineParts[7].toIntOrNull() ?: 0,
                    goals = lineParts[8].toIntOrNull() ?: 0,
                    assists = lineParts[9].toIntOrNull() ?: 0,
                    yellowCards = lineParts[10].toIntOrNull() ?: 0,
                    redCards = lineParts[11].toIntOrNull() ?: 0
                )

                team.players.add(player)
                playersList.add(player)
            }

        return playersList
    }
}
