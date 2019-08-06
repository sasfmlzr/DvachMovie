package dvachmovie.db.data

import org.joda.time.LocalDateTime

data class NullMovie(override val movieUrl: String = "",
                     override val previewUrl: String = "",
                     override val board: String = "",
                     override var isPlayed: Boolean = false,
                     override var date: LocalDateTime = LocalDateTime.parse("2000"),
                     override val md5: String = "",
                     override val post: Long = 0,
                     override val thread: Long = 0,
                     override val baseUrl: String = "") : Movie
