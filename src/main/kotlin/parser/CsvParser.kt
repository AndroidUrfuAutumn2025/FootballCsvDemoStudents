package parser

import model.Player
import model.Team
import java.io.InputStream

object CsvParser {
    
    fun parsePlayers(inputStream: InputStream): List<Player> {
        val players = mutableListOf<Player>()
        val lines = inputStream.bufferedReader().readLines()
        
        // Skip header line
        for (i in 1 until lines.size) {
            val line = lines[i].trim()
            if (line.isEmpty()) continue
            
            val parts = line.split(";")
            if (parts.size < 12) continue
            
            try {
                val player = Player(
                    name = parts[0].trim(),
                    team = parts[1].trim(),
                    city = parts[2].trim(),
                    position = parts[3].trim(),
                    nationality = parts[4].trim(),
                    agency = parts[5].trim().takeIf { it.isNotEmpty() },
                    transferCost = parts[6].trim().toLongOrNull() ?: 0L,
                    participations = parts[7].trim().toIntOrNull() ?: 0,
                    goals = parts[8].trim().toIntOrNull() ?: 0,
                    assists = parts[9].trim().toIntOrNull() ?: 0,
                    yellowCards = parts[10].trim().toIntOrNull() ?: 0,
                    redCards = parts[11].trim().toIntOrNull() ?: 0
                )
                players.add(player)
            } catch (e: Exception) {
                // Skip invalid lines
                continue
            }
        }
        
        return players
    }
    
    fun parseTeams(players: List<Player>): List<Team> {
        val teamsMap = mutableMapOf<String, MutableList<Player>>()
        
        for (player in players) {
            teamsMap.getOrPut(player.team) { mutableListOf() }.add(player)
        }
        
        return teamsMap.map { (teamName, teamPlayers) ->
            Team(
                name = teamName,
                city = teamPlayers.first().city,
                players = teamPlayers
            )
        }
    }
}