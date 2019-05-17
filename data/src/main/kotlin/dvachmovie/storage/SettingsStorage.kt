package dvachmovie.storage

import dvachmovie.ScopeProvider
import dvachmovie.api.model.Boards
import kotlinx.coroutines.async
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

    fun isLoadingEveryTime() =
            ScopeProvider.getIOScope().async { pref.getBoolean(LOADING_PARAM) ?: false }

    fun putLoadingEveryTime(value: Boolean) =
            ScopeProvider.getIOScope().async { pref.putBoolean(LOADING_PARAM, value) }

    fun isReportBtnVisible() =
            ScopeProvider.getIOScope().async { pref.getBoolean(REPORT_BTN_VISIBLE) ?: true }

    fun putReportBtnVisible(value: Boolean) =
            ScopeProvider.getIOScope().async { pref.putBoolean(REPORT_BTN_VISIBLE, value) }

    fun isListBtnVisible() =
            ScopeProvider.getIOScope().async { pref.getBoolean(LIST_BTN_VISIBLE) ?: true }

    fun putListBtnVisible(value: Boolean) =
            ScopeProvider.getIOScope().async { pref.putBoolean(LIST_BTN_VISIBLE, value) }

    fun getBoard() =
            ScopeProvider.getIOScope().async {
                pref.getString(BOARD) ?: Boards.defaultMap.iterator().next().key
            }

    fun putBoard(board: String) =
            ScopeProvider.getIOScope().async { pref.putString(BOARD, board) }

    fun getCookie() =
            ScopeProvider.getIOScope().async { pref.getString(COOKIE) ?: "" }

    fun putCookie(cookie: String) =
            ScopeProvider.getIOScope().async { pref.putString(COOKIE, cookie) }

    fun isAllowGesture() =
            ScopeProvider.getUiScope().async { pref.getBoolean(GESTURE) ?: true }

    fun putIsAllowGesture(value: Boolean) =
            ScopeProvider.getIOScope().async { pref.putBoolean(GESTURE, value) }
}
