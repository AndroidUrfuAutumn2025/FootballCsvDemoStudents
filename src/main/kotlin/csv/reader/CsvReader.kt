package csv.reader

import csv.parser.ICsvParser
import java.io.File

class CsvReader : ICsvReader {
    override fun <T> readLines(
        filePath: String,
        parser: ICsvParser<T>
    ): List<T> {
        val file = File(filePath)
        val lines = file.readLines()

        if (lines.isEmpty()) return emptyList()

        return lines.drop(1).map{ parser.parse(it)}
    }
}