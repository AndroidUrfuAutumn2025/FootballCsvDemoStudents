package csv.parser

interface ICsvParser<T> {
    fun parse(line: String): T
}