package dvachmovie.db.data

import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Test

class NullThreadTest {
    @Test
    fun happyPass() {
        val typicalDateTime = LocalDateTime()
        val nullThread = NullThread(date = typicalDateTime)
        val nullThreadEquals = NullThread(date = typicalDateTime)
        val nullThreadDifThread = NullThread(2,
                "Afas",
                "ASsad",
                LocalDateTime().minusDays(2),
                true)
        val nullThreadDifNameThread = NullThread(0,
                "Afas",
                "ASsad",
                LocalDateTime().minusDays(2),
                true)
        val nullThreadDifBaseUrl = NullThread(0,
                "",
                "ASsad",
                LocalDateTime().minusDays(2),
                true)
        val nullThreadDifDate = NullThread(0,
                "",
                "",
                LocalDateTime().minusDays(2),
                true)
        val nullThreadDifIsHidden = NullThread(0,
                "",
                "",
                typicalDateTime,
                false)

        Assert.assertEquals(false, nullThread == nullThreadDifThread)
        Assert.assertEquals(false, nullThread.hashCode() == nullThreadDifThread.hashCode())

        Assert.assertEquals(false, nullThread == nullThreadDifNameThread)
        Assert.assertEquals(false, nullThread == nullThreadDifBaseUrl)
        Assert.assertEquals(false, nullThread == nullThreadDifDate)
        Assert.assertEquals(false, nullThread == nullThreadDifIsHidden)
        Assert.assertEquals(true, nullThread == nullThreadEquals)
        Assert.assertEquals(false, nullThread.equals(listOf("")))
        Assert.assertEquals(false, nullThread == null)
    }
}