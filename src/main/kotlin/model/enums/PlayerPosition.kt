package model.enums

enum class PlayerPosition(override val displayName: String): DisplayNameEnum {
    GOALKEEPER("GOALKEEPER"),
    DEFENDER("DEFENDER"),
    MIDFIELD("MIDFIELD"),
    FORWARD("FORWARD");

    companion object {
        fun fromDisplayName(displayName: String): PlayerPosition? {
            return DisplayNameEnum.fromDisplayName(displayName, values())
        }
    }
}









