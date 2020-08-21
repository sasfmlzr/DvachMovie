package dvachmovie.db.data

import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Test

class NullMovieTest {
    @Test
    fun happyPass() {
        val nullMovie = NullMovie()
        val nullMovieEquals = NullMovie()
        val nullMovieDifMovieUrl = NullMovie("ASfas")
        val nullMovieDifPreviewUrl = NullMovie("",
                "Afas")
        val nullMovieDifBoard = NullMovie("",
                "",
                "ASsad")
        val nullMovieDifIsPlayed = NullMovie("",
                "",
                "",
                true)
        val nullMovieDifThread = NullMovie("",
                "",
                "",
                false,
                LocalDateTime().minusDays(2),
                "",
                0,
                10)
        val nullMovieDifBaseUrl = NullMovie("",
                "",
                "",
                false,
                LocalDateTime().minusDays(2),
                "",
                0,
                0,
                "dsfds",
                LocalDateTime().minusDays(2))

        Assert.assertEquals(false, nullMovie == nullMovieDifMovieUrl)
        Assert.assertEquals(false, nullMovie.hashCode() == nullMovieDifMovieUrl.hashCode())

        Assert.assertEquals(false, nullMovie == nullMovieDifPreviewUrl)
        Assert.assertEquals(false, nullMovie == nullMovieDifBoard)
        Assert.assertEquals(false, nullMovie == nullMovieDifIsPlayed)
        Assert.assertEquals(false, nullMovie == nullMovieDifThread)
        Assert.assertEquals(false, nullMovie == nullMovieDifBaseUrl)
        Assert.assertEquals(false, nullMovie == null)
        Assert.assertEquals(true, nullMovie == nullMovieEquals)
        Assert.assertEquals(true, nullMovie == nullMovie)
    }
}
