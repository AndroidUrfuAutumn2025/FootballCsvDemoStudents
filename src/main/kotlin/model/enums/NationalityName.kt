package model.enums

enum class NationalityName(override val displayName: String) : DisplayNameEnum {
    COLOMBIA("Colombia"),
    BRAZIL("Brazil"),
    PALAU("Palau"),
    CROATIA("Croatia"),
    ECUADOR("Ecuador"),
    QATAR("Qatar"),
    GHANA("Ghana"),
    GERMANY("Germany"),
    KOREA("Korea (Democratic People's Republic of)"),
    MOZAMBIQUE("Mozambique");

    companion object{
        fun fromDisplayName(displayName: String): NationalityName? {
            return DisplayNameEnum.fromDisplayName(displayName, values())
        }
    }
}