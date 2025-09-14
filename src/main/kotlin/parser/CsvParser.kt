package parser

import model.Player
import model.Team
import java.io.File

object CsvParser {
    fun parsePlayers(filePath: String): List<Player> {
        val file = File(filePath)
        if (!file.exists()) {
            throw IllegalArgumentException("File not found: $filePath")
        }

        return file.readLines().drop(1)
            .mapNotNull { line ->
                try {
                    val values = line.split(";").map { it.trim() }
                    if (values.size >= 12) {
                        Player(
                            name = values[0],
                            team = Team(values[1], values[2]),
                            position = values[3],
                            nationality = values[4],
                            agency = values[5],
                            transferCost = values[6].toIntOrNull() ?: 0,
                            participations = values[7].toIntOrNull() ?: 0,
                            goals = values[8].toIntOrNull() ?: 0,
                            assists = values[9].toIntOrNull() ?: 0,
                            yellowCards = values[10].toIntOrNull() ?: 0,
                            redCards = values[11].toIntOrNull() ?: 0
                        )
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    println("Error parsing line: $line - ${e.message}")
                    null
                }
            }
    }
}