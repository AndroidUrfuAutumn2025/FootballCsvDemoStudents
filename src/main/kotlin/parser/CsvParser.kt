package parser
import model.Person
import model.Position
import model.Team
import java.io.File

object CsvParser {
    var data: Pair<MutableList<Team>, MutableList<Person>> = Pair(mutableListOf(), mutableListOf())
    private val file: File = File("C:/Users/papak/AndroidStudioProjects/" +
            "FootballCsvDemoStudents/src/main/resources/fakePlayers.csv")
    fun getNotes(): MutableList<List<String>>?  {
        val players: MutableList<List<String>>? = mutableListOf()
        val fileStrings: List<String> = CsvParser.file.readLines()
        val criteria = fileStrings.get(0)
        if(fileStrings.isNotEmpty()) {
            fileStrings.forEach {
                players?.add(it.split(";"))
            }
        }
        else{
            println("Sorry, no data")
            return null
            }
        return players?.subList(1, players.size)
        }

    fun formationOfPlayersAndTeams(notes: MutableList<List<String>>?):
            Pair<MutableList<Team>, MutableList<Person>>  {
        val teams: MutableList<Team> = mutableListOf()
        val players: MutableList<Person> = mutableListOf()
        notes?.forEach {
            val player: Person = Person(
                name = it[0],
                team = it[1],
                city = it[2],
                position = Position.valueOf(it[3]),
                nationality = it[4],
                agency = it[5],
                transferCost = it[6].toIntOrNull(),
                participations = it[7].toIntOrNull(),
                goals = it[8].toIntOrNull(),
                assists = it[9].toIntOrNull(),
                yellowCards = it[10].toIntOrNull(),
                redCards = it[11].toIntOrNull()
            )
            players.add(player)
            val team = teams.find {it.name == player.team}
            if (team != null) {
                team.players.add(player)
            }
            else{
                val newTeam = Team(players = mutableListOf(player), name = player.team)
                teams.add(newTeam)
            }
        }
        return Pair(teams, players)
    }

    init {
        if(file.exists()) {
            println("File Exist, Check data")
            val rawData = getNotes()
            data = formationOfPlayersAndTeams(notes = rawData)
        }
        else {
            println("Can't find the file, sorry")
        }
        //Не стал добавлять ссылку на файл в аргументы функции, не разобрался с init
    }
}