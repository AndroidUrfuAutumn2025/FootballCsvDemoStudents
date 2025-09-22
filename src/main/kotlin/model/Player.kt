package model
import java.io.File

import java.io.InputStream

class Player(val name: String, val team: String, val city: String, val position: String,
             val agency: String, val transferCost: String, val participations: String,
             val goals: String, val assists: String, val yellowCards: String, val redCards: String)


class CSVParser {

    companion object {
        fun parsePlayersFromCSV(inputStream: InputStream): List<Player> {
            return inputStream.bufferedReader().use { reader ->
                reader.lineSequence()
                    .filter { it.isNotBlank() }
                    .map { line -> parsePlayerFromLine(line) }
                    .toList()
            }
        }


        private fun parsePlayerFromLine(line: String): Player {
            val parts = line.split(";")

            if (parts.size < 11) {
                throw IllegalArgumentException("Недостаточно данных в строке: $line")
            }

            return Player(
                name = parts[0].trim(),
                team = parts[1].trim(),
                city = parts[2].trim(),
                position = parts[3].trim(),
                agency = parts[4].trim(),
                transferCost = parts[5].trim(),
                participations = parts[6].trim(),
                goals = parts[7].trim(),
                assists = parts[8].trim(),
                yellowCards = parts[9].trim(),
                redCards = parts[10].trim()
            )
        }
    }
}
