package model.enums

enum class TeamName(override val displayName: String) : DisplayNameEnum {
    NEVADA_WHALES("Nevada whales"),
    WISCONSIN_PROPHETS("Wisconsin prophets"),
    NORTH_CAROLINA_DOLPHINS("North Carolina dolphins"),
    MISSISSIPPI_WITCHES("Mississippi witches"),
    MASSACHUSETTS_ENCHANTERS("Massachusetts enchanters"),
    FLORIDA_TIGERS("Florida tigers"),
    UTAH_NEMESIS("Utah nemesis"),
    MINNESOTA_GIANTS("Minnesota giants"),
    NEW_MEXICO_OGRES("New Mexico ogres"),
    HAWAII_OXEN("Hawaii oxen");

    companion object {
        fun fromDisplayName(displayName: String): TeamName? {
            return DisplayNameEnum.fromDisplayName(displayName, values())
        }
    }
}









