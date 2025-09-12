import parser.CsvParser

const val PLAYERS_FILE_PATH = "src/main/resources/fakePlayers.csv"

fun main() {
    print("Yeah rock!")

    val players = CsvParser.parsePlayers(PLAYERS_FILE_PATH)

    players.forEach {
        println(it)
    }
}