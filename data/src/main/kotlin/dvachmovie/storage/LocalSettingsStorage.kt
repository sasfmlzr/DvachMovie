package dvachmovie.storage

import dvachmovie.ScopeProvider
import dvachmovie.api.model.Boards
import kotlinx.coroutines.async
import javax.inject.Inject

class LocalSettingsStorage @Inject constructor(
        private val pref: KeyValueStorage
) : SettingsStorage{
    companion object {
        private const val LOADING_PARAM = "LoadingMoviesOrNot"
        private const val REPORT_BTN_VISIBLE = "ReportBtnVisibleOrNot"
        private const val LIST_BTN_VISIBLE = "ListBtnVisibleOrNot"
        private const val BOARD = "board"
        private const val COOKIE = "cookie"
        private const val GESTURE = "gesture"
    }

    override fun isLoadingEveryTime() =
            ScopeProvider.getIOScope().async { pref.getBoolean(LOADING_PARAM) ?: false }

    override fun putLoadingEveryTime(value: Boolean) =
            ScopeProvider.getIOScope().async { pref.putBoolean(LOADING_PARAM, value) }

    override fun isReportBtnVisible() =
            ScopeProvider.getIOScope().async { pref.getBoolean(REPORT_BTN_VISIBLE) ?: true }

    override fun putReportBtnVisible(value: Boolean) =
            ScopeProvider.getIOScope().async { pref.putBoolean(REPORT_BTN_VISIBLE, value) }

    override fun isListBtnVisible() =
            ScopeProvider.getIOScope().async { pref.getBoolean(LIST_BTN_VISIBLE) ?: true }

    override fun putListBtnVisible(value: Boolean) =
            ScopeProvider.getIOScope().async { pref.putBoolean(LIST_BTN_VISIBLE, value) }

    override fun getBoard() =
            ScopeProvider.getIOScope().async {
                pref.getString(BOARD) ?: Boards.defaultMap.iterator().next().key
            }

    override fun putBoard(board: String) =
            ScopeProvider.getIOScope().async { pref.putString(BOARD, board) }

    override fun getCookie() =
            ScopeProvider.getIOScope().async { pref.getString(COOKIE) ?: "" }

    override fun putCookie(cookie: String) =
            ScopeProvider.getIOScope().async { pref.putString(COOKIE, cookie) }

    override fun isAllowGesture() =
            ScopeProvider.getIOScope().async { pref.getBoolean(GESTURE) ?: true }

    override fun putIsAllowGesture(value: Boolean) =
            ScopeProvider.getIOScope().async { pref.putBoolean(GESTURE, value) }
}
