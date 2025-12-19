package parser

import model.Player
import model.Position
import model.Team

object CsvParser {

    private const val DELIM = ";"

    fun parsePlayers(csv: List<String>): List<Player> {
        // Если файл пустой, возвращаем пустой список
        if (csv.isEmpty()) return emptyList()

        // Пропускаем заголовок (первую строку) и обрабатываем остальные
        return csv
            .drop(1) // пропускаем заголовок
            .filter { it.isNotBlank() } // игнорируем пустые строки
            .map { parseLine(it) } // преобразуем каждую строку в Player
    }

    private fun parseLine(line: String): Player {
        // Разбиваем строку по разделителю ;
        val parts = line.split(DELIM)


        if (parts.size < 12) {
            throw IllegalArgumentException("Некорректная строка CSV: $line")
        }

        // Извлекаем данные из строки
        val name = parts[0].trim()
        val teamName = parts[1].trim()
        val city = parts[2].trim()

        // Преобразуем строку в значение enum Position
        val position = Position.valueOf(parts[3].trim().uppercase())

        val nationality = parts[4].trim()

        // Если агентство пустое, сохраняем null
        val agency = parts[5].trim().ifEmpty { null }

        val transferCost = parts[6].trim().toLong()
        val participations = parts[7].trim().toInt()
        val goals = parts[8].trim().toInt()
        val assists = parts[9].trim().toInt()
        val yellowCards = parts[10].trim().toInt()
        val redCards = parts[11].trim().toInt()

        // Создаём объект Team
        val team = Team(teamName, city)

        // Создаём и возвращаем объект Player
        return Player(
            name = name,
            team = team,
            position = position,
            nationality = nationality,
            agency = agency,
            transferCost = transferCost,
            participations = participations,
            goals = goals,
            assists = assists,
            yellowCards = yellowCards,
            redCards = redCards
        )
    }
}