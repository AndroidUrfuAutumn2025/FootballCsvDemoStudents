package parser

import model.Player
import model.enums.Position
import model.Team
import java.io.File

object CsvParser {

     fun parse(filePath: String): List<Player> {
        return File(filePath).bufferedReader().use {
            it.lineSequence()
                .drop(1)
                .filter { it.isNotBlank() }
                .mapNotNull { parseToPlayer(it) }
                .toList()
        }
    }

    private fun parseToPlayer(line: String): Player? {
        val data = line.split(';')
        val team = Team(
            name = data[1],
            city = data[2]
        )

        return Player(
            name = data[0],
            team = team,
            position = Position.fromString(data[3]),
            nationality = data[4],
            agency = data[5],
            transferCost = data[6].toDoubleOrNull() ?: 0.0,
            participationCount = data[7].toIntOrNull() ?: 0,
            goalCount = data[8].toIntOrNull() ?: 0,
            assistsCount = data[9].toIntOrNull() ?: 0,
            yellowCardCount = data[10].toIntOrNull() ?: 0,
            redCardCount = data[11].toIntOrNull() ?: 0
        )
    }
}