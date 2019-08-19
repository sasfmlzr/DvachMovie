package dvachmovie.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dvachmovie.db.data.Movie
import org.joda.time.LocalDateTime

@Entity(tableName = "movieData")
@TypeConverters(Converters::class)
data class MovieEntity(@PrimaryKey @ColumnInfo(name = "movieUrl") override val movieUrl: String,
                       @ColumnInfo(name = "previewUrl") override val previewUrl: String = "",
                       @ColumnInfo(name = "board") override val board: String = "",
                       @ColumnInfo(name = "isPlayed") override var isPlayed: Boolean = false,
                       @ColumnInfo(name = "date") override var date: LocalDateTime = LocalDateTime(),
                       @ColumnInfo(name = "md5") override val md5: String = "",
                       @ColumnInfo(name = "thread") override val thread: Long = 0,
                       @ColumnInfo(name = "post") override val post: Long = 0,
                       @ColumnInfo(name = "baseUrl") override val baseUrl: String,
                       @ColumnInfo(name = "dateAddedToDB") override var dateAddedToDB: LocalDateTime = LocalDateTime()
) : Movie {

    override fun equals(other: Any?) =
            other is Movie
                    && movieUrl == other.movieUrl
                    && previewUrl == other.previewUrl
                    && board == other.board

    override fun hashCode(): Int {
        var result = movieUrl.hashCode()
        result = 31 * result + previewUrl.hashCode()
        result = 31 * result + board.hashCode()
        result = 31 * result + isPlayed.hashCode()
        return result
    }
}
