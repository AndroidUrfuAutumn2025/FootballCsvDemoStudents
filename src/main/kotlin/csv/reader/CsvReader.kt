package csv.reader

import csv.parser.ICsvParser
import java.io.File

class CsvReader : ICsvReader {
    override fun <T> readLines(
        filePath: String,
        parser: ICsvParser<T>
    ): List<T> = File(filePath).readLines().drop(1).map(parser::parse)
}