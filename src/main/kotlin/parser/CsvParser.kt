package parser

import model.Player
import model.Team
import java.io.BufferedReader
import java.io.InputStreamReader

object CsvParser {
    fun parsePlayer(filename: String): List<Player> {
        val players = mutableListOf<Player>()

        val stream = this::class.java.classLoader.getResourceAsStream(filename)
            ?: throw IllegalArgumentException("Resource not found in classpath: $filename")

        BufferedReader(InputStreamReader(stream)).use { reader ->
            var isFirstLine = true
            reader.lineSequence().forEach { line ->
                if (isFirstLine) {
                    isFirstLine = false
                    return@forEach
                }
                if (line.isNotBlank()) {
                    val data = line.split(";").map { it.trim() }
                    if (data.size >= 12) {
                        val team = Team(data[1], data[2])
                        val player = Player(
                            name = data[0],
                            team = team,
                            position = data[3],
                            nationality = data[4],
                            agency = if (data[5].isBlank()) null else data[5],
                            transferCost = data[6].toDoubleOrNull() ?: 0.0,
                            participations = data[7].toIntOrNull() ?: 0,
                            goals = data[8].toIntOrNull() ?: 0,
                            assists = data[9].toIntOrNull() ?: 0,
                            yellowCards = data[10].toIntOrNull() ?: 0,
                            redCards = data[11].toIntOrNull() ?: 0
                        )
                        players.add(player)
                    }
                }
            }
        }
        return players
    }
}