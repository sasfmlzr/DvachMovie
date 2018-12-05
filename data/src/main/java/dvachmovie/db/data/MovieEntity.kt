package dvachmovie.db.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieData")
data class MovieEntity(@PrimaryKey(autoGenerate = true) val id: Long?,
                       @NonNull @ColumnInfo(name = "movieUrl") val movieUrl: String,
                       @NonNull @ColumnInfo(name = "previewUrl") val previewUrl: String,
                       @NonNull @ColumnInfo(name = "board") val board: String,
                       @NonNull @ColumnInfo(name = "isPlayed") val isPlayed: Int = 0
)