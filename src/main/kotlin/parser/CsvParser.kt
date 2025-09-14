package parser

import model.Player
import model.Position
import model.Team
import java.io.File

object CsvParser {
    fun parse(file: File): List<Player> {
        return file.readLines()
            .drop(1) // пропускаем заголовок
            .mapNotNull { line ->
                val parts = line.split(";")

                if (parts.size < 11) return@mapNotNull null

                try {
                    Player(
                        name = parts[0].trim(),
                        team = Team(parts[1].trim(), parts[2].trim()),
                        position = Position.getString(parts[3].trim()),
                        nationality = parts[4].trim(),
                        agency = parts[5].takeIf { it.isNotBlank() },
                        transferCost = parts[6].trim().toLong(),
                        participations = parts[7].trim().toInt(),
                        goals = parts[8].trim().toInt(),
                        assists = parts[9].trim().toInt(),
                        yellowCards = parts[10].trim().toInt(),
                        redCards = parts[11].trim().toInt()
                    )
                } catch (e: Exception) {
                    println("Ошибка парсинга строки: $line → ${e.message}")
                    null
                }
            }
    }
}
