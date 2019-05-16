package dvachmovie.storage

import dvachmovie.api.model.Boards
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SettingsStorage @Inject constructor(
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

    suspend fun putLoadingEveryTime(value: Boolean) =
            withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                pref.putBoolean(LOADING_PARAM, value)
            }

    fun isReportBtnVisible() = pref.getBoolean(REPORT_BTN_VISIBLE) ?: true

    suspend fun putReportBtnVisible(value: Boolean) =
            withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                pref.putBoolean(REPORT_BTN_VISIBLE, value)
            }


    fun isListBtnVisible() = pref.getBoolean(LIST_BTN_VISIBLE) ?: true

    suspend fun putListBtnVisible(value: Boolean) {
        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            pref.putBoolean(LIST_BTN_VISIBLE, value)
        }
    }

    fun getBoard() = pref.getString(BOARD) ?: Boards.defaultMap.iterator().next().key

    suspend fun putBoard(board: String) {
        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            pref.putString(BOARD, board)
        }
    }

    fun getCookie() = pref.getString(COOKIE) ?: ""

    suspend fun putCookie(cookie: String) =
            withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
                pref.putString(COOKIE, cookie)
            }

    fun isAllowGesture() = pref.getBoolean(GESTURE) ?: true

    suspend fun putIsAllowGesture(value: Boolean) {
        withContext(CoroutineScope(Dispatchers.Default).coroutineContext) {
            pref.putBoolean(GESTURE, value)
        }
    }
}
