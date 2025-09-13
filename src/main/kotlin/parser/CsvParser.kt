package parser

import model.Player
import java.io.File

/**
 * Парсер CSV файлов для преобразования данных в объекты Player
 */
object CsvParser {
    /**
     * Парсит CSV файл и возвращает список игроков
     */
    fun parsePlayers(filePath: String): List<Player> {
        val file = File(filePath)
        if (!file.exists()) {
            throw IllegalArgumentException("Файл не найден: $filePath")
        }

        return file.readLines().drop(1)
            .map { line ->
                val parts = line.split(";")
                if (parts.size < 12) return@map null

                Player(
                    name = parts[0].trim(),
                    team = parts[1].trim(),
                    city = parts[2].trim(),
                    position = parts[3].trim(),
                    nationality = parts[4].trim(),
                    agency = if (parts[5].isBlank()) null else parts[5].trim(),
                    transferCost = parts[6].trim().toLongOrNull() ?: 0L,
                    participations = parts[7].trim().toIntOrNull() ?: 0,
                    goals = parts[8].trim().toIntOrNull() ?: 0,
                    assists = parts[9].trim().toIntOrNull() ?: 0,
                    yellowCards = parts[10].trim().toIntOrNull() ?: 0,
                    redCards = parts[11].trim().toIntOrNull() ?: 0
                )
            }
            .filterNotNull()
    }
}