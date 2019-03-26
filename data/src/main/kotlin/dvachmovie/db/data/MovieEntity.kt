package dvachmovie.db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieData")
data class MovieEntity(@PrimaryKey @ColumnInfo(name = "movieUrl") override val movieUrl: String,
                       @ColumnInfo(name = "previewUrl") override val previewUrl: String = "",
                       @ColumnInfo(name = "board") override val board: String = "",
                       @ColumnInfo(name = "isPlayed") override var isPlayed: Boolean = false
) : Movie {

    companion object {
        private const val uniqueNumber = 31
    }

    override fun equals(other: Any?) =
            other is Movie
                    && movieUrl == other.movieUrl
                    && previewUrl == other.previewUrl
                    && board == other.board

    override fun hashCode(): Int {
        var result = movieUrl.hashCode()
        result = uniqueNumber * result + previewUrl.hashCode()
        result = uniqueNumber * result + board.hashCode()
        result = uniqueNumber * result + isPlayed.hashCode()
        return result
    }
}

interface Movie {
    val movieUrl: String
    val previewUrl: String
    val board: String
    var isPlayed: Boolean
}
