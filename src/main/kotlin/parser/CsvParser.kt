package parser

import model.Player
import model.Team
import java.io.File

object CsvParser {
    
    fun parsePlayers(filePath: String): List<Player> {
        val file = File(filePath)
        
        return file.readLines()
            .drop(1)
            .map { line ->
                val parts = line.split(";")
                
                Player(
                    name = parts[0].trim(),
                    team = parts[1].trim(),
                    city = parts[2].trim(),
                    position = parts[3].trim(),
                    nationality = parts[4].trim(),
                    agency = parts[5].trim().takeIf { it.isNotEmpty() },
                    transferCost = parts[6].trim().toLong(),
                    participations = parts[7].trim().toInt(),
                    goals = parts[8].trim().toInt(),
                    assists = parts[9].trim().toInt(),
                    yellowCards = parts[10].trim().toInt(),
                    redCards = parts[11].trim().toInt()
                )
            }
    }
    
    fun parseTeams(players: List<Player>): List<Team> {
        return players
            .groupBy { it.team }
            .map { (teamName, teamPlayers) ->
                Team(
                    name = teamName,
                    city = teamPlayers.first().city,
                    players = teamPlayers
                )
            }
    }
}