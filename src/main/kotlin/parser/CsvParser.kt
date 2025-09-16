package parser

import model.Player
import model.Team
import java.io.File

class CsvParser {
    fun parseCsv(filePath: String): List<Player> {
        val file = File(filePath)
        if (!file.exists()) {
            throw IllegalArgumentException("File not found: $filePath")
        }

        return file.readLines().drop(1)
            .mapNotNull { line ->
                val values = line.split(";").map { it.trim() }
                if (values.size >= 12) {
                    try {
                        Player(
                            name = values[0],
                            team = Team(values[1], values[2]),
                            position = values[3],
                            nationality = values[4],
                            agency = if (values[5].isBlank()) null else values[5],
                            transferValue = values[6].toDouble(),
                            matchesPlayed = values[7].toInt(),
                            goals = values[8].toInt(),
                            assists = values[9].toInt(),
                            yellowCards = values[10].toInt(),
                            redCards = values[11].toInt()
                        )
                    } catch (e: Exception) {
                        println("Error parsing line: $line - ${e.message}")
                        null
                    }
                } else {
                    println("Skipping invalid line: $line")
                    null
                }
            }
    }
}