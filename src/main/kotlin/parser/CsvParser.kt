package parser
import model.Player
import model.Team
import java.io.File

object CsvParser{
    fun parsePlayersCsv (filePath : String): List<Player>{
        val file = File(filePath)

        if (!file.exists())
            {throw IllegalArgumentException("Файл не существует.")}

        val lines = file.readLines()
        val playerList = mutableListOf<Player>()
        for (i in 1 until lines.size){
            val line = lines[i]
            val values = line.split(";")
            try {
                val player = Player(
                    name = values[0],
                    team = values[1],
                    city = values[2],
                    position = values[3],
                    nationality = values[4],
                    agency = values[5],
                    cost = values[6].toInt(),
                    participation = values[7].toInt(),
                    goalCount = values[8].toInt(),
                    assistCount = values[9].toInt(),
                    yellowCardCount = values[10].toInt(),
                    redCardCount = values[11].toInt()
                )
                playerList.add(player)
            }
            catch(e: Exception)
            { throw IllegalArgumentException("Данные из строки $i не были добавлены.") }
        }
        return playerList
    }

    fun groupPlayersByTeam (players: List<Player>): List<Team>{
        val teamsMap = mutableMapOf<String, MutableList<Player>>()

        for (player in players) {
            val teamName = player.team
            if (!teamsMap.containsKey(teamName)) {
                teamsMap[teamName] = mutableListOf()
            }
            teamsMap[teamName]?.add(player)
        }
        val teams = mutableListOf<Team>()
        for ((teamName, teamPlayers) in teamsMap) {
            val teamCity = teamPlayers.first().city
            teams.add(Team(teamName, teamCity, teamPlayers))
        }
        return teams
    }
}