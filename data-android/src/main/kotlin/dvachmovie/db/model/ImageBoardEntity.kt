package dvachmovie.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import dvachmovie.db.data.ImageBoard

@Entity(tableName = "imageBoardData")
data class ImageBoardEntity(@PrimaryKey @ColumnInfo(name = "baseUrl") override val baseUrl: String,
                            @ColumnInfo(name = "cookie") override val cookie: String,
                            @ColumnInfo(name = "currentBoard") override val currentBoard: String
) : ImageBoard {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ImageBoardEntity

        if (baseUrl != other.baseUrl) return false
        if (cookie != other.cookie) return false
        if (currentBoard != other.currentBoard) return false

        return true
    }

    override fun hashCode(): Int {
        var result = baseUrl.hashCode()
        result = 31 * result + cookie.hashCode()
        result = 31 * result + currentBoard.hashCode()
        return result
    }
}
