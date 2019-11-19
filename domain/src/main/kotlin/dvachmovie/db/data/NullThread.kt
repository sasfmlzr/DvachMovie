package dvachmovie.db.data

import org.joda.time.LocalDateTime

data class NullThread(override val thread: Long = 0,
                      override val nameThread: String = "",
                      override val baseUrl: String = "",
                      override var date: LocalDateTime = LocalDateTime.parse("2000"),
                      override var isHidden: Boolean = true) : Thread {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NullThread

        if (thread != other.thread ||
                nameThread != other.nameThread ||
                baseUrl != other.baseUrl ||
                date != other.date ||
                isHidden != other.isHidden) return false

        return true
    }

    override fun hashCode(): Int {
        var result = thread.hashCode()
        result = 31 * result + nameThread.hashCode()
        result = 31 * result + baseUrl.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + isHidden.hashCode()
        return result
    }
}
