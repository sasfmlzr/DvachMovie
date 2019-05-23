package dvachmovie.db.model

import androidx.room.TypeConverter
import org.joda.time.LocalDateTime

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: String?): LocalDateTime? {
        return if (value == null) null else LocalDateTime(value)
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: LocalDateTime?): String? {
        return date?.toString()
    }
}
