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
                newTeam.members.add(player);
                teams[teamName] = newTeam
            } else {
                teams[teamName]?.members?.add(player)
            }
        }

        return teams.values.toList() as ArrayList<Team>;
    }

    private fun parsePlayer(splitLine: List<String>): Player {
        return Player(splitLine[0], splitLine[1], splitLine[2],
            splitLine[3], splitLine[4], splitLine[5],
            splitLine[6].toInt(), splitLine[7].toInt(), splitLine[8].toInt(),
            splitLine[9].toInt(), splitLine[10].toInt(), splitLine[11].toInt()
        )
    }
}
