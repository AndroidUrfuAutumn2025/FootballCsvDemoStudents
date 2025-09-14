package parser

import java.io.File
import model.Player
import model.Team

object CsvParser{
    fun csvParser(filename: String): List<Player>{
        val lines = File(filename).readLines()
        if (lines.size < 2) return emptyList()

        val result = mutableListOf<Player>()

        for (i in 1 until lines.size){
            val line = lines[i]
            val columns = line.split(";").map { it.trim() }

            if (columns.size >= 11){
                try {
                    val team = Team(columns[1], columns[2])
                    val player = Player(
                        namePlayer = columns[0],
                        team = team,
                        position = columns[3],
                        nationality = columns[4],
                        agency = columns[5],
                        transferCost = columns[6].toInt(),
                        countMatches = columns[7].toInt(),
                        countGoals = columns[8].toInt(),
                        countAssists = columns[9].toInt(),
                        countYellowCards = columns[10].toInt(),
                        countRedCards = columns[10].toInt()
                    )
                    result.add(player)
                } catch (e : NumberFormatException) {
                    println("Ошибка преобразования числа в строке ${i + 1}: $line")
                }
            }
        }
        return result
    }
}