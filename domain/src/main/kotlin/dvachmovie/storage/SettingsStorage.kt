package dvachmovie.storage

import kotlinx.coroutines.Deferred

interface SettingsStorage {

    fun isLoadingEveryTimeAsync(): Deferred<Boolean>

    fun isLoadingEveryTime(): Boolean

    fun putLoadingEveryTime(value: Boolean): Deferred<Unit>

    fun isReportBtnVisibleAsync(): Deferred<Boolean>

    fun isReportBtnVisible(): Boolean

    fun putReportBtnVisible(value: Boolean): Deferred<Unit>

    fun isListBtnVisibleAsync(): Deferred<Boolean>

    fun isListBtnVisible(): Boolean

    fun putListBtnVisible(value: Boolean): Deferred<Unit>

    fun getBoardAsync(): Deferred<String>

    fun getBoard(): String

    fun putBoard(board: String): Deferred<Unit>

    fun getCookieAsync(): Deferred<String>

    fun getCookie(): String

    fun putCookie(cookie: String): Deferred<Unit>

    fun isAllowGestureAsync(): Deferred<Boolean>

    fun isAllowGesture(): Boolean

    fun putIsAllowGesture(value: Boolean): Deferred<Unit>
}
