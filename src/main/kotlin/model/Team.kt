package model

class Team(
    var name: String = "",
    var city: String = ""
) {
    override fun toString(): String {
        return "Team(name='$name', city='$city')"
    }
}