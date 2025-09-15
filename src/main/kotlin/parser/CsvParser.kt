package parser
import java.io.InputStream
import model.*
import java.io.File


object CsvParser{
    const val PATH = "/home/user/study/Kotlin/KotlinHomework1/src/main/resources/fakePlayers.csv"

    fun readCsv(): List<Player> {
        val reader = File(PATH).inputStream().bufferedReader()
        val header = reader.readLine()
        return reader.lineSequence()
            .map{
                val data = it.split(";")
                Player(data[0],
                    Team(data[1],
                        data[2]),
                    enumValueOf<Position>(data[3]) ,
                    data[4],
                    data[5].ifBlank { null },
                    data[6].toFloat(),
                    data[7].toInt(),
                    data[8].toInt(),
                    data[9].toInt(),
                    data[10].toInt(),
                    data[11].toInt())
            }.toList()

    }
    val myList = readCsv()
}