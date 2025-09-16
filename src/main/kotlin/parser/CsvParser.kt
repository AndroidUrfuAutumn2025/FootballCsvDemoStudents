package parser

import model.Player
import model.Position
import model.Team

object CsvParser {
    fun parsePlayers(resourceFileName : String) : List<Player> {
        val players = mutableListOf<Player>()
        val input = javaClass.classLoader.getResourceAsStream(resourceFileName)
            ?: throw Exception("File not found")
        val lines = input.bufferedReader().readLines().drop(1)
        for(line in lines){
            players.add(parsePlayerFromLine(line))
        }
        return players
    }

    private fun parsePlayerFromLine(line : String) : Player {
        val parts = line.split(";")
        if (parts.size < 12) {
            throw IllegalArgumentException("Недостаточно данных в строке")
        }

        return Player(
            name = parts[0],
            team = Team(parts[1].trim()),
            city = parts[2].trim(),
            position = Position.valueOf(parts[3]),
            nationality = parts[4],
            agency = parts[5],
            transferCost = parts[6].toDouble(),
            participations = parts[7].toIntOrNull().orZero(),
            goals = parts[8].toIntOrNull().orZero(),
            assists = parts[9].toIntOrNull().orZero(),
            yellowCards = parts[10].toIntOrNull().orZero(),
            redCards = parts[11].toIntOrNull().orZero()
        )
    }

    private fun Int?.orZero() = this ?: 0
}
