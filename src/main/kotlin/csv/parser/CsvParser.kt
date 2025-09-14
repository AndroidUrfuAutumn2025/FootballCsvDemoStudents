package csv.parser

abstract class CsvParser<T>(protected val headers: Map<String, Int>) : ICsvParser<T> {
    protected fun getValue(column: String, values: List<String>): String {
        val index = headers[column] ?: throw IllegalArgumentException("Column '$column' not found")
        return values.getOrElse(index) { "" }.trim()
    }

    protected fun getIntValue(column: String, values: List<String>): Int {
        return getValue(column, values).toIntOrNull() ?: 0
    }

    protected fun getLongValue(column: String, values: List<String>): Long {
        return getValue(column, values).toLongOrNull() ?: 0
    }

    protected fun getNullableString(column: String, values: List<String>): String? {
        val value = getValue(column, values)
        return value.takeIf { it.isNotEmpty() }
    }
}