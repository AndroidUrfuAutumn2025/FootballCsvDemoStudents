package parser
import model.Person
import model.Team
import java.io.File

object CsvParser {
    var data: Pair<MutableList<Team>, MutableList<Person>> = Pair(mutableListOf(), mutableListOf())
    private var file: File = File("C:/Users/papak/AndroidStudioProjects/" +
            "FootballCsvDemoStudents/src/main/resources/fakePlayers.csv")
    fun getNotes(): MutableList<List<String>>?  {
        var players: MutableList<List<String>>? = mutableListOf()
        val filestrings: List<String> = CsvParser.file.readLines()
        val criteria = filestrings.get(0)
        println(criteria)
        if(filestrings.isNotEmpty()) {
            filestrings.forEach {
                players?.add(it.split(";"))
            }
        }
        else{
            println("Sorry, no data")
            return null
            }
        return players?.subList(1, players.size)
        }

    fun formation_of_Players_and_Teams(notes: MutableList<List<String>>?):
            Pair<MutableList<Team>, MutableList<Person>>  {
        var teams: MutableList<Team> = mutableListOf()
        var players: MutableList<Person> = mutableListOf()
        notes?.forEach {
            val player: Person = Person(
                name = it[0],
                team = it[1],
                city = it[2],
                position = it[3],
                nationality = it[4],
                agency = it[5],
                transfer_cost = it[6].toInt(),
                participations = it[7].toInt(),
                goals = it[8].toInt(),
                assists = it[9].toInt(),
                yellow_cards = it[10].toInt(),
                red_cards = it[11].toInt()
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
            val raw_data = getNotes()
            data = formation_of_Players_and_Teams(notes = raw_data)
        }
        else {
            println("Can't find the file, sorry")
        }
    }
}