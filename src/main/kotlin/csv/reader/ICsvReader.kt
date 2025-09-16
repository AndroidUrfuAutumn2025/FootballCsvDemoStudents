package csv.reader

import csv.parser.ICsvParser

interface ICsvReader {
    fun <T> readLines(filePath: String, parser: ICsvParser<T>): List<T>
}