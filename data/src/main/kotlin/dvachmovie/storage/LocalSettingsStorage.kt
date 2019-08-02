package dvachmovie.storage

import dvachmovie.AppConfig
import dvachmovie.api.DvachBoards
import dvachmovie.architecture.ScopeProvider
import kotlinx.coroutines.async
import javax.inject.Inject

class LocalSettingsStorage @Inject constructor(
        private val pref: KeyValueStorage,
        private val scopeProvider: ScopeProvider
) : SettingsStorage {
    companion object {
        private const val REPORT_BTN_VISIBLE = "ReportBtnVisibleOrNot"
        private const val LIST_BTN_VISIBLE = "ListBtnVisibleOrNot"
        private const val CURRENT_BASE_URL = "currentBaseUrl"
        private const val BOARD = "board"
        private const val COOKIE = "cookie"
        private const val GESTURE = "gesture"
        private const val COOKIE_DEFAULT_VALUE = "92ea293bf47456479e25b11ba67bb17a"
    }

    override fun isReportBtnVisibleAsync() =
            scopeProvider.ioScope.async { pref.getBoolean(REPORT_BTN_VISIBLE) ?: true }

    override fun isReportBtnVisible() =
            pref.getBoolean(REPORT_BTN_VISIBLE) ?: true

    override fun putReportBtnVisible(value: Boolean) =
            scopeProvider.ioScope.async { pref.putBoolean(REPORT_BTN_VISIBLE, value) }

    override fun isListBtnVisibleAsync() =
            scopeProvider.ioScope.async { pref.getBoolean(LIST_BTN_VISIBLE) ?: true }

    override fun isListBtnVisible() =
            pref.getBoolean(LIST_BTN_VISIBLE) ?: true

    override fun putListBtnVisible(value: Boolean) =
            scopeProvider.ioScope.async { pref.putBoolean(LIST_BTN_VISIBLE, value) }

    override fun getCurrentBaseUrlAsync() =
            scopeProvider.ioScope.async {
                pref.getString(CURRENT_BASE_URL) ?: AppConfig.DVACH_URL
            }

    override fun getCurrentBaseUrl() =
            pref.getString(CURRENT_BASE_URL) ?: AppConfig.DVACH_URL

    override fun putCurrentBaseUrl(baseUrl: String) =
            scopeProvider.ioScope.async { pref.putString(CURRENT_BASE_URL, baseUrl) }

    override fun getBoardAsync() =
            scopeProvider.ioScope.async {
                pref.getString(BOARD) ?: DvachBoards.defaultMap.iterator().next().key
            }

    override fun getBoard() =
            pref.getString(BOARD) ?: DvachBoards.defaultMap.iterator().next().key

    override fun putBoard(board: String) =
            scopeProvider.ioScope.async { pref.putString(BOARD, board) }

    override fun getCookieAsync() =
            scopeProvider.ioScope.async { pref.getString(COOKIE) ?: COOKIE_DEFAULT_VALUE }

    override fun getCookie() =
            pref.getString(COOKIE) ?: COOKIE_DEFAULT_VALUE

    override fun putCookie(cookie: String) =
            scopeProvider.ioScope.async { pref.putString(COOKIE, cookie) }

    override fun isAllowGestureAsync() =
            scopeProvider.ioScope.async { pref.getBoolean(GESTURE) ?: true }

    override fun isAllowGesture() =
            pref.getBoolean(GESTURE) ?: true

    override fun putIsAllowGesture(value: Boolean) =
            scopeProvider.ioScope.async { pref.putBoolean(GESTURE, value) }
}
