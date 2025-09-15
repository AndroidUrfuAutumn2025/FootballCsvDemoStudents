package parser

import model.Player
import model.Team
import java.io.File
import java.io.IOException

class CsvParser {

    fun parsePlayers(filePath: String): List<Player> {
        val players = mutableListOf<Player>()
        val file = File(filePath)

        if (!file.exists()) {
            throw IOException("Файл не найден: $filePath")
        }

        file.bufferedReader().useLines { lines ->
            lines.drop(1)
                .forEach { line ->
                    val tokens = line.split(';')
                    if (tokens.size == 12) {
                        try {
                            val team = Team(name = tokens[1], city = tokens[2])
                            val player = Player(
                                name = tokens[0],
                                team = team,
                                position = tokens[3],
                                nationality = tokens[4],
                                agency = tokens[5].ifEmpty { null },
                                transferValue = tokens[6].toDouble(),
                                matchesPlayed = tokens[7].toInt(),
                                goals = tokens[8].toInt(),
                                assists = tokens[9].toInt(),
                                yellowCards = tokens[10].toInt(),
                                redCards = tokens[11].toInt()
                            )
                            players.add(player)
                        } catch (e: NumberFormatException) {
                            println("Предупреждение: пропуск строки $line - ${e.message}")
                        }
                    }
                }
        }
        return players
    }
}
