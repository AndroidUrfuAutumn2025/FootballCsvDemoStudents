package parser

import java.io.File
import model.Player
import model.Position
import model.Team

object CsvParser {
    fun parse(file: File): List<Player> {
        val lines = file.readLines()

        if (lines.isEmpty()) return emptyList()

        val dataLines = lines.drop(1)

        return dataLines.map { line ->
            val data = line.split(";")

            Player(
                fullName = data[0],
                team = Team(name = data[1], city = data[2]),
                position = Position.fromEnglish(data[3])
                    ?: throw IllegalArgumentException("Unknown position: ${data[3]}"),
                nationality = data[4],
                agency = data[5],
                transferCost = data[6].toIntOrNull(),
                participations = data[7].toIntOrNull(),
                goals = data[8].toIntOrNull(),
                assists = data[9].toIntOrNull(),
                yellowCards = data[10].toIntOrNull(),
                redCards = data[11].toIntOrNull()
            )
        }
    }
}