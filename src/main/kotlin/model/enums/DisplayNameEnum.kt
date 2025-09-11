package model.enums

interface DisplayNameEnum {
    val displayName: String
    
    companion object {
        inline fun <reified T> fromDisplayName(
            displayName: String,
            values: Array<T>
        ): T? where T : DisplayNameEnum {
            return values.find { it.displayName == displayName }
        }
    }
}
