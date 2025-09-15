package parser

import model.Player
import java.io.File

object CsvParser {
    private const val DELIMITER = ";"
    private const val HEADER_LINES = 1

    fun parseFile(pathName: String): List<Player> {
        return File(pathName).useLines { lines ->
            lines.drop(HEADER_LINES)
                .mapNotNull { parseLine(it) }
                .toList()
        }
    }

    private fun parseLine(line: String): Player? {
        return try {
            val row = line.split(DELIMITER)
            Player(
                name = row[0],
                team = row[1],
                city = row[2],
                position = row[3],
                nationality = row[4],
                agency = row[5],
                transferCost = row[6].toInt(),
                participations = row[7].toInt(),
                goals = row[8].toInt(),
                assists = row[9].toInt(),
                yellowCards = row[10].toInt(),
                redCards = row[11].toInt()
            )
        } catch (e: Exception) {
            println("Ошибка при обработке строки: $line. Причина: ${e.message}")
            null
        }
    }
}