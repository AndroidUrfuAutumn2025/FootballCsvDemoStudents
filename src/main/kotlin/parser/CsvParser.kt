package parser

import model.Player
import model.Team
import java.io.BufferedReader
import java.io.InputStreamReader

object CsvParser{
    fun loadPlayers(): List<Player> {
        val inputStream = this::class.java.getResourceAsStream("/fakePlayers.csv")
            ?: throw IllegalArgumentException("Файл не найден")

        val reader = BufferedReader(InputStreamReader(inputStream))

        return reader.useLines { lines ->
            lines.drop(1)
                .map { line ->
                    val tokens = line.split(";")

                    Player(
                        name = tokens[0],
                        team = Team(
                            name = tokens[1],
                            city = tokens[2]
                        ),
                        position = tokens[3],
                        nationality = tokens[4],
                        agency = tokens[5],
                        transferValue = tokens[6].toInt(),
                        matches = tokens[7].toInt(),
                        heads = tokens[8].toInt(),
                        assists = tokens[9].toInt(),
                        yellowCards = tokens[10].toInt(),
                        redCards = tokens[11].toInt()
                    )
                }.toList()
        }
    }

    fun loadTeams(players: List<Player>): List<Team> {
        return players.map { it.team }.distinctBy { it.name to it.city }
    }
}