package parser
import model.Team
import model.Player
import java.io.File

object CsvParser {

    fun parseCsv(filePath: String): List<Team> {
        val file = File(filePath)
        if (!file.exists()) {
            throw IllegalArgumentException("Файл не найден: $filePath")
        }

        val lines = file.readLines()
        if (lines.size <= 1) {
            return emptyList()
        }

        val players = lines.drop(1).mapNotNull { line ->
            parsePlayer(line)
        }

        return players.groupBy { it.team }
            .map { (teamName, teamPlayers) ->
                val firstPlayer = teamPlayers.first()
                Team(
                    player = teamPlayers.toMutableList(),
                    name = teamName,
                    city = firstPlayer.city
                )
            }
    }

    private fun parsePlayer(line: String): Player? {
        try {
            val values = parseCsvLine(line, ';')

            if (values.size < 12) {
                println("Пропускаем строку (недостаточно данных): $line")
                return null
            }

            if (values[0].trim().isEmpty() || values[1].trim().isEmpty() ||
                values[2].trim().isEmpty() || values[3].trim().isEmpty()) {
                println("Пропускаем строку (отсутствуют обязательные поля): $line")
                return null
            }

            return Player(
                name = values[0].trim(),
                team = values[1].trim(),
                city = values[2].trim(),
                position = values[3].trim(),
                nationality = values[4].trim(),
                agency = values[5].trim(),
                transfer_cost = safeParseInt(values[6].trim(), "transfer_cost"),
                participations = safeParseInt(values[7].trim(), "participations"),
                goals = safeParseInt(values[8].trim(), "goals"),
                assists = safeParseInt(values[9].trim(), "assists"),
                yellow_cards = safeParseInt(values[10].trim(), "yellow_cards"),
                red_cards = safeParseInt(values[11].trim(), "red_cards")
            )
        } catch (e: Exception) {
            println("Ошибка при парсинге строки: $line. Ошибка: ${e.message}")
            return null
        }
    }

    private fun safeParseInt(value: String, fieldName: String = ""): Int {
        return try {
            value.trim().takeIf { it.isNotBlank() }?.toIntOrNull() ?: 0
        } catch (e: Exception) {
            println("Ошибка парсинга $fieldName: '$value'")
            0
        }
    }

    private fun parseCsvLine(line: String, delimiter: Char): List<String> {
        val result = mutableListOf<String>()
        var current = StringBuilder()
        var inQuotes = false

        for (char in line) {
            when {
                char == '"' -> inQuotes = !inQuotes
                char == delimiter && !inQuotes -> {
                    result.add(current.toString())
                    current = StringBuilder()
                }
                else -> current.append(char)
            }
        }
        result.add(current.toString())

        return result
    }
}