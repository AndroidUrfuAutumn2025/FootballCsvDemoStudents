package parser

import model.Player
import model.Team
import java.io.File

object CsvParser{
    fun playersParsing(src: String): MutableList<Player> {
        val players = mutableListOf<Player>()

        File(src)
            .readLines()
            .drop(1)
            .forEachIndexed { index, line ->
                val row = line.split(";")
                if (row.size == 12) {
                    players.add(Player(row))
                } else {
                    println("Строка ${index + 1} пропущена: $row")
                }
            }

        return players
    }

}