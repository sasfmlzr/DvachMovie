package dvachmovie.storage

import dvachmovie.api.model.Boards
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
        private const val REPORT_BTN_VISIBLE = "ReportBtnVisibleOrNot"
        private const val LIST_BTN_VISIBLE = "ListBtnVisibleOrNot"
        private const val BOARD = "board"
        private const val COOKIE = "cookie"
        private const val GESTURE = "gesture"
    }

    fun isLoadingEveryTime() = pref.getBoolean(LOADING_PARAM) ?: false

    fun putLoadingEveryTime(value: Boolean) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            pref.putBoolean(LOADING_PARAM, value)
        }
    }

    fun isReportBtnVisible() = pref.getBoolean(LOADING_PARAM) ?: true

    fun putReportBtnVisible(value: Boolean) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            pref.putBoolean(LOADING_PARAM, value)
        }
    }

    fun isListBtnVisible() = pref.getBoolean(LOADING_PARAM) ?: true

    fun putListBtnVisible(value: Boolean) {
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

    fun getBoard() = pref.getString(BOARD) ?: Boards.defaultMap.iterator().next().key

    fun getCookie() = pref.getString(COOKIE) ?: ""

    fun putCookie(cookie: String) = pref.putString(COOKIE, cookie)

    fun isAllowGesture() = pref.getBoolean(GESTURE) ?: true

    fun putIsAllowGesture(value: Boolean) {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            pref.putBoolean(GESTURE, value)
        }
    }
}
