package parser

import model.Player
import model.Position
import model.Team
import java.io.File

fun String?.toIntOrZero(): Int = this?.toIntOrNull() ?: 0

object CsvParser {
    fun parsePlayers(filePath: String): List<Player> {
        val file = File(filePath)

        return file.readLines()
            .drop(1)
            .map { line -> parsePlayerFromLine(line) }
    }

    private fun parsePlayerFromLine(line: String): Player {
        val fields = line.split(';')

        return Player(
            name = fields[0],
            teamName = fields[1],
            city = fields[2],
            position = Position.getByName(fields[3]),
            nationality = fields[4],
            agency = fields[5],
            transferCost = fields[6].toIntOrZero(),
            participations = fields[7].toIntOrZero(),
            goals = fields[8].toIntOrZero(),
            assists = fields[9].toIntOrZero(),
            yellowCards = fields[10].toIntOrZero(),
            redCards = fields[11].toIntOrZero(),
        )
    }
}