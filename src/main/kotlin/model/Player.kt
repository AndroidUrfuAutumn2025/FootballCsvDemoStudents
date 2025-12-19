package model


enum class Position {
    GOALKEEPER,    // Вратарь
    DEFENDER,      // Защитник
    MIDFIELD,      // Полузащитник
    FORWARD        // Нападающий
}

// Класс данных для игрока
data class Player(
    val name: String,           // Имя игрока
    val team: Team,             // Команда (объект Team)
    val position: Position,     // Позиция (enum)
    val nationality: String,    // Национальность (важно для задачи про немцев)
    val agency: String?,        // Агентство (может быть null)
    val transferCost: Long,     // Трансферная стоимость
    val participations: Int,    // Участия в матчах
    val goals: Int,             // Голы
    val assists: Int,           // Голевые передачи
    val yellowCards: Int,       // Жёлтые карточки
    val redCards: Int           // Красные карточки
)