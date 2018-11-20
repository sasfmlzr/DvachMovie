package dvachmovie.db.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movieData")
data class MovieEntity(@PrimaryKey(autoGenerate = true) val id: Long?,
                       @NonNull @ColumnInfo(name = "movieLink") val movieLink: String) {

}