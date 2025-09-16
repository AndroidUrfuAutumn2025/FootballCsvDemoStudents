package parser

import model.Player
import model.Team
import java.io.InputStream

class CsvParser(private val resourceName: String) {
    fun parseCSV(): List<Player> {
        val inputStream = this::class.java.getResourceAsStream(resourceName)
            ?: throw IllegalArgumentException("File ${this::class.java.getResource(resourceName)} not found")

        val lines = inputStream.bufferedReader().readLines()
        return lines.drop(1).map { parseLine(it) }
    }

    private fun parseLine(line: String): Player {
        val parts = line.split(";")

        val team = Team(parts[1], parts[2])

        return Player(
            name = parts[0],
            team = team,
            position = parts[3],
            nationality = parts[4],
            agency = parts[5],
            transferCost = parts[6].toInt(),
            participations = parts[7].toInt(),
            goals = parts[8].toInt(),
            assists = parts[9].toInt(),
            yellowCards = parts[10].toInt(),
            redCards = parts[11].toInt()
        )
    }
}

