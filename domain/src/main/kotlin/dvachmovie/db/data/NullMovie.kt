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
                     override val baseUrl: String = "",
                     override var dateAddedToDB: LocalDateTime = LocalDateTime()) : Movie {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NullMovie

        if (movieUrl != other.movieUrl ||
                previewUrl != other.previewUrl ||
                board != other.board ||
                isPlayed != other.isPlayed ||
                thread != other.thread ||
                baseUrl != other.baseUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = movieUrl.hashCode()
        result = 31 * result + previewUrl.hashCode()
        result = 31 * result + board.hashCode()
        result = 31 * result + isPlayed.hashCode()
        result = 31 * result + thread.hashCode()
        result = 31 * result + baseUrl.hashCode()
        return result
    }
}
