package dvachmovie.storage

import kotlinx.coroutines.Deferred

interface SettingsStorage {

    fun isLoadingEveryTime(): Deferred<Boolean>

    fun putLoadingEveryTime(value: Boolean): Deferred<Unit>

    fun isReportBtnVisible(): Deferred<Boolean>

    fun putReportBtnVisible(value: Boolean): Deferred<Unit>

    fun isListBtnVisible(): Deferred<Boolean>

    fun putListBtnVisible(value: Boolean): Deferred<Unit>

    fun getBoard(): Deferred<String>

    fun putBoard(board: String): Deferred<Unit>

    fun getCookie(): Deferred<String>

    fun putCookie(cookie: String): Deferred<Unit>

    fun isAllowGesture(): Deferred<Boolean>

    fun putIsAllowGesture(value: Boolean): Deferred<Unit>
}
