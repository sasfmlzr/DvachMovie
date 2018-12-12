package dvachmovie.db.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieData")
data class MovieEntity(@PrimaryKey @ColumnInfo(name = "movieUrl") val movieUrl: String,
                       @ColumnInfo(name = "previewUrl") val previewUrl: String = "",
                       @ColumnInfo(name = "board") val board: String = "",
                       @ColumnInfo(name = "isPlayed") val isPlayed: Int = 0
)