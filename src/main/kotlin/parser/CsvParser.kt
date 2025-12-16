package parser

import model.Player
import java.io.File

object CsvParser {

    fun readCsv(fileName: String): List<Player> {
        val url = javaClass.classLoader.getResource(fileName)
            ?: throw IllegalArgumentException("Файл $fileName не найден в resources")

        val file = File(url.toURI())
        val players = mutableListOf<Player>()

        file.readLines().drop(1).forEach { line ->
            if (line.isNotBlank()) {
                val p = parseLine(line)
                players.add(p)
            }
        }
        return players
    }

    private fun parseLine(line: String): Player {
        val tokens = line.split(";")

        return Player(
            name = tokens[0],
            teamName = tokens[1],
            city = tokens[2],
            position = tokens[3],
            nationality = tokens[4],
            agency = tokens[5],
            transferCost = tokens[6].toLongOrNull() ?: 0,
            participations = tokens[7].toIntOrNull() ?: 0,
            goals = tokens[8].toIntOrNull() ?: 0,
            assists = tokens[9].toIntOrNull() ?: 0,
            yellowCards = tokens[10].toIntOrNull() ?: 0,
            redCards = tokens[11].toIntOrNull() ?: 0
        )
    }
}
