package parser
import java.io.File
import model.Player

object CsvParser {
    fun readPlayersFromCsv(fileName: String): List<Player> {
        val file = File(fileName)
        val lines = file.readLines()
        val players = mutableListOf<Player>()
        for (i in 1 until lines.size) {
            val currLine = lines[i]
            val values = currLine.split(";")

            if (values.size == 12) {
                try {
                    val player = Player(
                        name = values[0],
                        team = values[1],
                        city = values[2],
                        position = values[3],
                        nationality = values[4],
                        agency = values[5],
                        transferCost = values[6].toLong(),
                        participations = values[7].toInt(),
                        goals = values[8].toInt(),
                        assists = values[9].toInt(),
                        yellowCards = values[10].toInt(),
                        redCards = values[11].toInt()
                    )
                    players.add(player)
                } catch (e: Exception) {
                    println("Ошибка в строке ${i+1}")
                }
            }
        }
        return players
    }
}