package parser

import model.Player
import model.Team
import java.io.File

/**
 * Парсер CSV файлов для преобразования данных в объекты Player и Team
 */
object CsvParser {

    /**
     * Парсит CSV файл и возвращает список игроков
     */
    fun parse(file: File): List<Player> {
        val lines = file.readLines()
        if (lines.size <= 1) return emptyList()

        return lines.drop(1)
            .mapNotNull { line -> parseLine(line.trim()) }
    }

    /**
     * Парсит строку CSV и возвращает игрока с командой
     */
    private fun parseLine(line: String): Player? {
        if (line.isBlank()) return null

        return try {
            val data = line.split(";")
            if (data.size != 12) {
                println("Пропущена строка: неверное количество данных - '$line'")
                return null
            }

            Player(
                name = data[0].trim(),
                team = Team(
                    name = data[1].trim(),
                    city = data[2].trim()
                ),
                position = data[3].trim(),
                nationality = data[4].trim(),
                agency = if (data[5].isBlank()) null else data[5].trim(),
                transferCost = data[6].trim().toLong(),
                participations = data[7].trim().toInt(),
                goals = data[8].trim().toInt(),
                assists = data[9].trim().toInt(),
                yellowCards = data[10].trim().toInt(),
                redCards = data[11].trim().toInt()
            )
        } catch (e: Exception) {
            println("Ошибка парсинга строки: '$line' - ${e.message}")
            null
        }
    }
}