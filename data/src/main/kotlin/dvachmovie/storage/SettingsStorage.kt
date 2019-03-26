package dvachmovie.storage

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

open class SettingsStorage @Inject constructor(
        private val pref: KeyValueStorage
) {

    companion object {
        private const val LOADING_PARAM = "LoadingMoviesOrNot"
        private const val BOARD = "board"
        private const val COOKIE = "cookie"
    }

    fun isLoadingEveryTime() = pref.getBoolean(LOADING_PARAM) ?: false

    fun putLoadingEveryTime(value: Boolean) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            pref.putBoolean(LOADING_PARAM, value)
        }
    }

    fun putBoard(board: String) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            pref.putString(BOARD, board)
        }
    }

    fun getBoard() = pref.getString(BOARD) ?: "b"

    fun getCookie() = pref.getString(COOKIE) ?: ""

    fun putCookie(cookie: String) = pref.putString(COOKIE, cookie)
}
