package model.enums

enum class TownName(override val displayName: String): DisplayNameEnum {
    SOUTH_CAROLINE("South Carolina"),
    VERMONT("Vermont"),
    TENNESSEE("Tennessee"),
    VIRGINIA("Virginia"),
    CONNECTICUT("Connecticut"),
    WASHINGTON("Washington"),
    NEW_YORK("New York"),
    GEORGIA("Georgia"),
    OKLAHOMA("Oklahoma");

    companion object {
        fun fromDisplayName(displayName: String): TownName? {
            return DisplayNameEnum.fromDisplayName(displayName, values())
        }
    }
}