package parser

import model.Player
import model.Team
import java.io.File

object CsvParser {
    fun parsePlayers(filePath: String, splitSymbol: Char, hasTitles: Boolean): ArrayList<Player> {
        val players = arrayListOf<Player>()

        File(filePath).readLines().forEachIndexed { index, line ->
            if(hasTitles && index == 0) {
                return@forEachIndexed
            }
            val splitLine = line.split(splitSymbol)
            players.add(parsePlayer(splitLine))
        }
        return players
    }

    fun parseTeams(filePath: String, splitSymbol: Char, hasTitles: Boolean): ArrayList<Team> {
        val teams = mutableMapOf<String, Team>();

        File(filePath).readLines().forEachIndexed { index, line ->
            if(hasTitles && index == 0) {
                return@forEachIndexed
            }
            val splitLine = line.split(splitSymbol)
            val player = parsePlayer(splitLine)
            val teamName = player.teamName
            val teamCity = player.teamCity

            if(!teams.contains(teamName)) {
                val newTeam = Team(teamName, teamCity)
                newTeam.addPlayer(player);
                teams[teamName] = newTeam
            } else {
                val existingTeam = teams[teamName];
                existingTeam?.addPlayer(player)
            }
        }

        return teams.values.toList() as ArrayList<Team>;
    }

    private fun parsePlayer(splitLine: List<String>): Player {
        return Player(
            name = splitLine[0],
            teamName = splitLine[1],
            teamCity = splitLine[2],
            position = splitLine[3],
            nationality = splitLine[4],
            agency = splitLine[5],
            transferCost = splitLine[6].toInt(),
            participations = splitLine[7].toInt(),
            goals = splitLine[8].toInt(),
            assists = splitLine[9].toInt(),
            yellowCards = splitLine[10].toInt(),
            redCards = splitLine[11].toInt()
        )
    }
}
