package parser

import model.Player
import model.Position
import model.Team

object CsvParser {
    private const val DELIM = ";"

    fun parsePlayers(csv: List<String>): List<Player> {
        if (csv.isEmpty()) return emptyList()
        return csv
            .drop(1) // skip header
            .filter { it.isNotBlank() }
            .map { parseLine(it) }
    }

    private fun parseLine(line: String): Player {
        val parts = line.split(DELIM)
        require(parts.size >= 12) { "Некорректная строка CSV: $line" }

        val name = parts[0].trim()
        val teamName = parts[1].trim()
        val city = parts[2].trim()
        val position = Position.valueOf(parts[3].trim())
        val nationality = parts[4].trim()
        val agency = parts[5].trim().ifEmpty { null }
        val transferCost = parts[6].trim().toLong()
        val participations = parts[7].trim().toInt()
        val goals = parts[8].trim().toInt()
        val assists = parts[9].trim().toInt()
        val yellow = parts[10].trim().toInt()
        val red = parts[11].trim().toInt()

        val team = Team(teamName, city)
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
            yellowCards = yellow,
            redCards = red
        )
    }
}