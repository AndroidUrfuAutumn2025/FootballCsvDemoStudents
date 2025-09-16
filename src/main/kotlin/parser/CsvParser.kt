package parser

import model.Player
import java.io.File

object CsvParser {
    fun parsePlayers(filePath: String): List<Player> {
        return File(filePath).readLines()
            .drop(1) // skip header
            .mapNotNull { line ->
                val parts = line.split(";")
                if (parts.size < 12) return@mapNotNull null
                Player(
                    name = parts[0],
                    team = parts[1],
                    city = parts[2],
                    position = parts[3],
                    nationality = parts[4],
                    agency = if (parts[5].isBlank()) null else parts[5],
                    transferCost = parts[6].toIntOrNull() ?: 0,
                    participations = parts[7].toIntOrNull() ?: 0,
                    goals = parts[8].toIntOrNull() ?: 0,
                    assists = parts[9].toIntOrNull() ?: 0,
                    yellowCards = parts[10].toIntOrNull() ?: 0,
                    redCards = parts[11].toIntOrNull() ?: 0
                )
            }
    }
}