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
    override fun equals(other: Any?) =
            other is Movie
                    && movieUrl == other.movieUrl
                    && previewUrl == other.previewUrl
                    && board == other.board
                    && isPlayed == other.isPlayed

    override fun hashCode(): Int {
        var result = movieUrl.hashCode()
        result = 31 * result + previewUrl.hashCode()
        result = 31 * result + board.hashCode()
        result = 31 * result + isPlayed.hashCode()
        return result
    }
}

interface Movie {
     val movieUrl: String
     val previewUrl: String
     val board: String
     var isPlayed: Boolean
}
