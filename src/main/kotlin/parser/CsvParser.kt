package parser
import model.Player
import model.Team
import model.Position
import java.io.File

class CsvParser(private val fileName: String) {
    fun parse(): List<Player> {
        return File(fileName).useLines { lines -> lines
            .drop(1)
            .filter { it.isNotBlank() }
            .mapNotNull { parseLine(it) }
            .toList()
        }
    }

    fun parseLine(line: String): Player? {
        val tokens = line.split(";")

        if (tokens.size < 12) return null

        return try {
            val name = tokens[0].trim()
            val teamName = tokens[1].trim()
            val city = tokens[2].trim()
            val position = Position.fromString(tokens[3].trim())
            val nationality = tokens[4].trim()
            val agency = tokens[5].trim().ifBlank { null }
            val transferValue = tokens[6].trim().toIntOrNull()
            val matches = tokens[7].trim().toIntOrNull()
            val goals = tokens[8].trim().toIntOrNull()
            val assists = tokens[9].trim().toIntOrNull()
            val yellowCards = tokens[10].trim().toIntOrNull()
            val redCards = tokens[11].trim().toIntOrNull()

            val team = Team(teamName, city)

            Player(
                name,
                team,
                position,
                nationality,
                agency,
                transferValue,
                matches,
                goals,
                assists,
                yellowCards,
                redCards
            )
        } catch (e: Exception) {
            println("Error while parsing '$line' → ${e.message}")
            null
        }
    }
}