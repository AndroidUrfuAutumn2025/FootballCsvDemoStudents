package parser

import model.Player
import model.Position
import model.Team
import java.io.File

object CsvParser {

    private var players: MutableList<Player> = mutableListOf()

    fun parse(path: String): List<Player> {
        players.clear()
        File(path).useLines { lines ->
            lines.drop(1)
                .forEach { parsePlayerFromString(it) }
        }
        return players
    }

    private fun parsePlayerFromString(playerString: String) {
        val playerParts = playerString.split(";")

        players.add(
            Player(
                name = playerParts[0],
                team = Team(name = playerParts[1], city = playerParts[2]),
                position = Position.getByValue(playerParts[3]),
                nationality = playerParts[4],
                agency = playerParts[5],
                transferCost = playerParts[6].toInt(),
                participationCount = playerParts[7].toInt(),
                goals = playerParts[8].toInt(),
                assists = playerParts[9].toIntOrNull().orZero(),
                yellowCards = playerParts[10].toIntOrNull().orZero(),
                redCards = playerParts[11].toIntOrNull().orZero()
            )
        )
    }

    private fun Int?.orZero() = this ?: 0
}
