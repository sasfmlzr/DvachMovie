package dvachmovie.db.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

@Entity(tableName = "movieData")
data class MovieEntity(@PrimaryKey(autoGenerate = true) val id: Long?,
                       @NonNull @ColumnInfo(name = "movieLink") val movieLink: String) {

}