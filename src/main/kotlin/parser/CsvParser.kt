package parser

import model.Player
import model.Position
import model.Team
import java.io.File

object CsvParser {
    fun parse(): List<Player> {
        val file = File("src/main/resources/fakePlayers.csv")
        val lines: List<String> = file.readLines()

        val players: MutableList<Player> = mutableListOf()

        for (i in 1 until lines.size) {
            val line = lines[i]
            val fields = line.split(";")

            try {
                val team = Team(name = fields[1], city = fields[2])
                val position: Position = when (fields[3]) {
                    Position.FORWARD.name -> Position.FORWARD
                    Position.DEFENDER.name -> Position.DEFENDER
                    Position.MIDFIELD.name -> Position.MIDFIELD
                    Position.GOALKEEPER.name -> Position.GOALKEEPER
                    else -> Position.UNKNOWN
                }

                val player = Player(
                    name = fields[0],
                    team = team,
                    position = position,
                    nationality = fields[4],
                    agency = fields[5].ifBlank { null },
                    transferCost = fields[6].toInt(),
                    participations = fields[7].toInt(),
                    goals = fields[8].toInt(),
                    assists = fields[9].toInt(),
                    yellowCards = fields[10].toInt(),
                    redCards = fields[11].toInt(),
                )

                players.add(player)
            } catch (e: Exception) {
                println("Не смогли распарсить строку: $line")
                println(e)
            }
        }

        return players
    }
}