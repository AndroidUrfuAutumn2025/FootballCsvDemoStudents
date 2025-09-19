package parser

import model.Player
import model.Team
import java.io.File

object CsvParser {

    fun parsePlayers(filePath: String): List<Player> {
        val players = mutableListOf<Player>()

        File(filePath).readLines().drop(1).forEach { line ->
            val parts = line.split(";")
            if (parts.size >= 12) {
                val team = Team(parts[1].trim(), parts[2].trim())
                players.add(
                    Player(
                        name = parts[0].trim(),
                        team = team,
                        position = parts[3].trim(),
                        nationality = parts[4].trim(),
                        agency = parts[5].trim(),
                        transferCost = parts[6].trim().toLongOrNull() ?: 0,
                        participations = parts[7].trim().toIntOrNull() ?: 0,
                        goals = parts[8].trim().toIntOrNull() ?: 0,
                        assists = parts[9].trim().toIntOrNull() ?: 0,
                        yellowCards = parts[10].trim().toIntOrNull() ?: 0,
                        redCards = parts[11].trim().toIntOrNull() ?: 0
                    )
                )
            }
        }

        return players
    }
}