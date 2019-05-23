package dvachmovie.storage

import dvachmovie.api.Boards
import dvachmovie.architecture.ScopeProvider
import kotlinx.coroutines.async
import javax.inject.Inject

class LocalSettingsStorage @Inject constructor(
        private val pref: KeyValueStorage,
        private val scopeProvider: ScopeProvider
) : SettingsStorage {
    companion object {
        private const val LOADING_PARAM = "LoadingMoviesOrNot"
        private const val REPORT_BTN_VISIBLE = "ReportBtnVisibleOrNot"
        private const val LIST_BTN_VISIBLE = "ListBtnVisibleOrNot"
        private const val BOARD = "board"
        private const val COOKIE = "cookie"
        private const val GESTURE = "gesture"
    }

    override fun isLoadingEveryTime() =
            scopeProvider.ioScope.async { pref.getBoolean(LOADING_PARAM) ?: false }

    override fun putLoadingEveryTime(value: Boolean) =
            scopeProvider.ioScope.async { pref.putBoolean(LOADING_PARAM, value) }

    override fun isReportBtnVisible() =
            scopeProvider.ioScope.async { pref.getBoolean(REPORT_BTN_VISIBLE) ?: true }

    override fun putReportBtnVisible(value: Boolean) =
            scopeProvider.ioScope.async { pref.putBoolean(REPORT_BTN_VISIBLE, value) }

    override fun isListBtnVisible() =
            scopeProvider.ioScope.async { pref.getBoolean(LIST_BTN_VISIBLE) ?: true }

    override fun putListBtnVisible(value: Boolean) =
            scopeProvider.ioScope.async { pref.putBoolean(LIST_BTN_VISIBLE, value) }

    override fun getBoard() =
            scopeProvider.ioScope.async {
                pref.getString(BOARD) ?: Boards.defaultMap.iterator().next().key
            }

    override fun putBoard(board: String) =
            scopeProvider.ioScope.async { pref.putString(BOARD, board) }

    override fun getCookie() =
            scopeProvider.ioScope.async { pref.getString(COOKIE) ?: "" }

    override fun putCookie(cookie: String) =
            scopeProvider.ioScope.async { pref.putString(COOKIE, cookie) }

    override fun isAllowGesture() =
            scopeProvider.ioScope.async { pref.getBoolean(GESTURE) ?: true }

    override fun putIsAllowGesture(value: Boolean) =
            scopeProvider.ioScope.async { pref.putBoolean(GESTURE, value) }
}
