package model.enums

enum class AgencyName(override val displayName: String): DisplayNameEnum {
    D_AMORE_LLC("D'Amore LLC"),
    WALKER_AND_SONS("Walker and Sons"),
    NAN("nan"),
    JAKUBOWSKI_AND_SONS("Jakubowski and Sons"),
    PRICE_HIRTHE("Price-Hirthe"),
    FADEL_LLC("Fadel LLC"),
    GRAHAM_POWLOWSKI("Graham-Powlowski"),
    BOGISICH_REMPEL("Bogisich-Rempel"),
    BAHRINGER_LARSON("Bahringer-Larson"),
    HAUCK_INC("Hauck Inc"),
    SCHADEN_ROBEL_AND_RAYNOR("Schaden, Robel and Raynor");

    companion object {
        fun fromDisplayName(displayName: String): AgencyName? {
            return DisplayNameEnum.fromDisplayName(displayName, values())
        }
    }
}