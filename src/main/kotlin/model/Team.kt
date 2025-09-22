package model


import java.io.InputStream

class Team(val city: String, val teamName: String)

fun readCsv(inputStream: InputStream): List<Team>{
    return inputStream.bufferedReader().use {reader ->
        reader.lineSequence()
            .filter{it.isNotBlank()}
            .map {line ->
                val parts = line.split(";")
                if (parts.size >=2){
                    val city = parts[2]
                    val teamName = parts[1]
                    Team(city, teamName)
                } else{
                    throw IllegalArgumentException("Неверный формат строки: $line")
                }
            }
            .toList()
    }
}