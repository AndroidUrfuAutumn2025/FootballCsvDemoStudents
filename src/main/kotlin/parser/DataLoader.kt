package parser

import model.Player
import java.io.FileNotFoundException

object DataLoader {

    private const val DEFAULT_FILE_PATH = "src/main/resources/fakePlayers.csv"

    fun loadData(filePath: String = DEFAULT_FILE_PATH): List<Player> = run {
        try {
            CsvParser.parse(filePath)
        } catch (e: FileNotFoundException) {
            println("File path not found ${e.message}")
            emptyList()
        }
    }
}