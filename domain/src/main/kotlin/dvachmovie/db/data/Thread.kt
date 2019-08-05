package dvachmovie.db.data

import org.joda.time.LocalDateTime

interface Thread {
    var date: LocalDateTime
    val thread: Long
    val isHidden: Boolean
    val nameThread: String
}
