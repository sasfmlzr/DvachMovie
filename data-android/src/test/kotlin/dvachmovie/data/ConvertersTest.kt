package dvachmovie.data

import dvachmovie.db.model.Converters
import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Test

internal class ConvertersTest {

    @Test
    fun `Converting String to LocalDateTime was successful`() {
        val date = "2017-10-10"
        Assert.assertEquals(LocalDateTime(date), Converters.fromTimestamp(date))
    }

    @Test
    fun `Converting String(null) to LocalDateTime return null`() {
        val date = null
        Assert.assertEquals(null, Converters.fromTimestamp(date))
    }

    @Test
    fun `Converting LocalDateTime to String was successful`() {
        val date = LocalDateTime.now()
        Assert.assertEquals(LocalDateTime(date).toString(), Converters.dateToTimestamp(date))
    }

    @Test
    fun `Converting LocalDateTime(null) to String return null`() {
        val date = null
        Assert.assertEquals(null, Converters.dateToTimestamp(date))
    }
}
