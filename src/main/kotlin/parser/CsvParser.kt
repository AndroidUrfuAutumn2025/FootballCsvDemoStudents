package parser

import model.Player
import model.Roles
import model.Team
import  java.io.File

object CsvParser{
    val teams : MutableMap<String, Team> = mutableMapOf()
    fun parseString(str: String) : Player{
        val contents = str.split(';')


        val tmp: Team

        if(!teams.containsKey(contents[1])){
            tmp = Team(contents[1], contents[2])
            teams[tmp.Name] = tmp
        }
        else{
            tmp = teams[contents[1]]!!
        }




        val tmp2 =
        try{
             Roles.valueOf(contents[3])
        }
        catch (e: IllegalArgumentException){
             Roles.MisteryRole
        }
        return Player(contents[0], tmp, tmp2, contents[4], contents[5], contents[6].toInt(), contents[7].toInt(), contents[8].toInt(), contents[9].toInt(), contents[10].toInt(), contents[11].toInt())
    }

    fun parseFile(path: String) : List<Player>{
        return File(path).useLines {
            lines ->
            lines.drop(1).map {
                line -> parseString(line)
            }.toList()
        }
    }

}