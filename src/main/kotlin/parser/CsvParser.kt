package parser


import model.Player
import model.Team
import java.io.File

object DataLoader {
    fun loadPlayers(): List<Player> {
        val inputStream = this::class.java.getResourceAsStream("/fakePlayers.csv")
            ?: throw IllegalArgumentException("File not found!")

        val lines = inputStream.bufferedReader().readLines()

        return lines
            .drop(1) // убираем заголовок
            .filter { it.isNotBlank() }
            .map { line ->
                val parts = line.split(";")

                if (parts.size < 12) {
                    throw IllegalArgumentException("Bad CSV line: $line")
                }

                val team = Team(parts[1], parts[2])

                Player(
                    name = parts[0],
                    team = team,
                    position = parts[3],
                    nationality = parts[4],   // <- используем из CSV
                    agency = parts[5].ifBlank { null },
                    transferCost = parts[6].toDouble(),
                    matches = parts[7].toInt(),
                    goals = parts[8].toInt(),
                    assists = parts[9].toInt(),
                    yellowCards = parts[10].toInt(),
                    redCards = parts[11].toInt()
                )
        }
    }
}