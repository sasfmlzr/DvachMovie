package dvachmovie.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dvachmovie.db.data.Thread
import org.joda.time.LocalDateTime

@Entity(tableName = "threadData")
@TypeConverters(Converters::class)
data class ThreadEntity(@PrimaryKey @ColumnInfo(name = "thread") override val thread: Long,
                        @ColumnInfo(name = "date") override var date: LocalDateTime = LocalDateTime(),
                        @ColumnInfo(name = "isHidden") override var isHidden: Boolean = false,
                        @ColumnInfo(name = "nameThread") override val nameThread: String,
                        @ColumnInfo(name = "baseUrl") override val baseUrl: String
) : Thread {

    override fun equals(other: Any?) =
            other is Thread
                    && thread == other.thread
                    && nameThread == other.nameThread
                    && baseUrl == other.baseUrl

    override fun hashCode(): Int {
        var result = thread.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + isHidden.hashCode()
        result = 31 * result + nameThread.hashCode()
        return result
    }
}
