package parser

import model.Player
import model.Team
import model.enums.Position
import java.io.File

object CsvParser {
    private const val CSV_DELIMITER = ';'

    fun parsePlayers(csvFilePath: String): List<Player> {
        return File(csvFilePath)
            .readLines()
            .drop(1)
            .filter { it.isNotBlank() }
            .map { mapFileLineToPlayer(it) }
    }

    private fun mapFileLineToPlayer(fileLine: String): Player {
        val values = fileLine.split(CSV_DELIMITER)

        return Player(
            name = values[0],
            team = Team(
                name = values[1],
                city = values[2]
            ),
            position = enumValueOf<Position>(values[3]),
            nationality = values[4],
            agencyName = values[5],
            transferCost = values[6].toLong(),
            participations = values[7].toInt(),
            goals = values[8].toInt(),
            assists = values[9].toInt(),
            yellowCards = values[10].toInt(),
            redCards = values[11].toInt()
        )
    }
}