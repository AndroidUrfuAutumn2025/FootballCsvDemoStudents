package parser

import model.Player
import model.Team
import java.io.File

object CsvParser {
    fun parsePlayers(filename: String): List<Player> {
        val players = mutableListOf<Player>()
        val inputStream = javaClass.classLoader.getResourceAsStream(filename)
        val lines = inputStream.bufferedReader().readLines()

        for (i in 1 until lines.size) {
            val line = lines[i]
            val parts = line.split(";")

            val team = Team(
                name = parts[1],
                city = parts[2]
            )

            val player = Player(
                name = parts[0],
                team = team,
                position = parts[3],
                nationality = parts[4],
                agency = parts[5].ifEmpty { null },
                transferCost = parts[6].toDouble(),
                participations = parts[7].toInt(),
                goals = parts[8].toInt(),
                assists = parts[9].toInt(),
                yellowCards = parts[10].toInt(),
                redCards = parts[11].toInt()
            )

            players.add(player)
        }

        return players
    }
}