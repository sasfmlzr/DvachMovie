package dvachmovie.api

object NeoChanBoards {

    fun isBoardExists(board: String): Boolean =
            defaultMap.containsKey(board) ||
                    popularMap.containsKey(board)

    val defaultMap = linkedMapOf<String, String>()

    val popularMap = hashMapOf<String, String>()

    init {
        defaultMap["kpop"] = "/kpop/ - KPOP"

        popularMap["kpop"] = "/kpop/ - KPOP"
        popularMap["b"] = "/b/ - Разное"
        popularMap["mu"] = "/mu/ - Музыка"
        popularMap["test"] = "/test/ - TEST"
        popularMap["jp"] = "/jp/ - Япония"
    }
}
