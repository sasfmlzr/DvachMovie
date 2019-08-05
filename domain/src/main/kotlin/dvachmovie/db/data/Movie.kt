package dvachmovie.db.data

import org.joda.time.LocalDateTime

interface Movie {
    val movieUrl: String
    val previewUrl: String
    val board: String
    var isPlayed: Boolean
    var date: LocalDateTime
    val md5: String
    val post: Long
    val thread: Long
    val baseUrl: String
    val isHidden: Boolean
    val nameThread: String
}
