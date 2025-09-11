package parser

import model.Player
import model.Team

import model.enums.*

import java.io.File

object CsvParser {

   fun parsePlayers(csvFile: File): List<Player> {
       val lines = csvFile.readLines()
       if (lines.isEmpty()) return emptyList()

       val header = lines[0].split(";")
       val dataLines = lines.drop(1)

       return dataLines.mapNotNull { line ->
           parsePlayerLine(line, header)
       }
   }

   private fun parsePlayerLine(line: String, header: List<String>): Player? {
       val values = line.split(";")


       try {
           val name = values[0].trim()
           val teamName = TeamName.fromDisplayName(values[1].trim())
           val town = TownName.fromDisplayName(values[2].trim())
           val position = PlayerPosition.fromDisplayName(values[3].trim())
           val nationality = NationalityName.fromDisplayName(values[4].trim())
           val agency = AgencyName.fromDisplayName(values[5].trim())
           val transferCost = values[6].trim().toLong()
           val participations = values[7].trim().toInt()
           val goals = values[8].trim().toInt()
           val assists = values[9].trim().toInt()
           val yellowCards = values[10].trim().toInt()
           val redCards = values[11].trim().toInt()

           val team = Team(name = teamName!!, town = town!!)

           return Player(
               name = name,
               team = team,
               position = position!!,
               nationality = nationality!!,
               agency = agency,
               transferCost = transferCost,
               participations = participations,
               goals = goals,
               assists = assists,
               yellowCards = yellowCards,
               redCards = redCards
           )
       } catch (e: Exception) {
           println("Ошибка при парсинге строки: $line")
           println("Ошибка: ${e.message}")
           return null
       }
   }
}