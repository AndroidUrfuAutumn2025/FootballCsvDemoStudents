package parser

import model.Player
import java.io.File

object CsvParser {
    fun parseCsv(filePath: String): List<Player> {
        val lines = File(filePath).readLines()
        return lines.drop(1).map { line ->
            val cols = line.split(";")
            Player(
                name = cols[0],
                team = cols[1],
                city = cols[2],
                position = cols[3],
                nationality = cols[4],
                agency = cols[5],
                transferCost = cols[6].toLong(),
                participations = cols[7].toInt(),
                goals = cols[8].toInt(),
                assists = cols[9].toInt(),
                yellowCards = cols[10].toInt(),
                redCards = cols[11].toInt()
            )
        }
    }
}
