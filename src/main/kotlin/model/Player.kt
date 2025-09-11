package model
import java.io.File

import java.io.InputStream

class Player(val name: String)

//InputStream - предоставляет стандартный байтовый способ чтения данных из разных источников.
fun readCSV(inputStream: InputStream): List<Player>{
    //.use {reader -> - функция use автоматически закрывает ресурс после использования
    //bufferedReader- переводим байты в символы
    return inputStream.bufferedReader().use {reader ->
        //reader.lineSequence() - создает последовательность (Sequence) строк из reader
        reader.lineSequence()
            //.filter {it.isNotBlank()} - фильтрует строки, оставляя только непустые
            .filter {it.isNotBlank()}
            //преобразует каждую строку в объект Player
            .map { line ->
                val (name) = line.split(";")
                Player(name)
            }
            .toList()
    }
}
