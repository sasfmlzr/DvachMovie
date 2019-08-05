package dvachmovie.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dvachmovie.db.data.Thread
import org.joda.time.LocalDateTime

@Entity(tableName = "threadData"//,
        //foreignKeys = [ForeignKey(entity = MovieEntity::class, parentColumns = ["thread"], childColumns = ["thread"], onDelete = CASCADE)]
         )
@TypeConverters(Converters::class)
data class ThreadEntity(@PrimaryKey @ColumnInfo(name = "thread") override val thread: Long,
                        @ColumnInfo(name = "date") override var date: LocalDateTime = LocalDateTime(),
                        @ColumnInfo(name = "isHidden") override val isHidden: Boolean = false,
                        @ColumnInfo(name = "nameThread") override val nameThread: String
) : Thread {

    override fun equals(other: Any?) =
            other is Thread
                    && thread == other.thread
                    && nameThread == other.nameThread

    override fun hashCode(): Int {
        var result = thread.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + isHidden.hashCode()
        result = 31 * result + nameThread.hashCode()
        return result
    }
}
