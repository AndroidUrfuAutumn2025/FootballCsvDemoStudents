package model
import model.Person
import parser.CsvParser
class Team(var players: MutableList<Person>, val name: String)
