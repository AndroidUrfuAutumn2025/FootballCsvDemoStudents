package parser

import model.Player
import model.Team
import java.io.File

object CsvParser
{
    fun readPlayersFromCsv(filePath: String): List<Player> {
        val file = File(filePath)
        if (!file.exists()) {
            throw IllegalArgumentException("Файл не найден: $filePath")
        }

        return file.readLines().drop(1)
            .map { line ->
                val values = line.split(";")
                if (values.size < 12) {
                    throw IllegalArgumentException("Неверный формат строки: $line")
                }

                Player(
                    name = values[0].trim(),
                    team = Team(
                        name = values[1].trim(),
                        city = values[2].trim()
                    ),
                    position = values[3].trim(),
                    nationality = values[4].trim(),
                    agency = values[5].trim(),
                    transferCost = values[6].trim().toLongOrNull() ?: 0L,
                    participations = values[7].trim().toIntOrNull() ?: 0,
                    goals = values[8].trim().toIntOrNull() ?: 0,
                    assists = values[9].trim().toIntOrNull() ?: 0,
                    yellowCards = values[10].trim().toIntOrNull() ?: 0,
                    redCards = values[11].trim().toIntOrNull() ?: 0
                )
            }
    }
}
