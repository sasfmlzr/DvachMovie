package dvachmovie.utils

import dvachmovie.api.FileItem
import dvachmovie.db.data.NullMovie
import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Test

class MovieUtilsTest {

    private val movieUtils = LocalMovieUtils()

    private val movieOne = NullMovie("Whew", isPlayed = true,
            date = LocalDateTime().minusYears(1))
    private val movieTwo = NullMovie("Test", isPlayed = false,
            date = LocalDateTime())
    private val movieThree = NullMovie("TestWhew", isPlayed = false)
    private val movieList = listOf(movieOne, movieTwo)
    private val movieDiffList = listOf(movieOne, movieThree)

    private val fileOne = FileItem(path = "one.webm", date = "14/05/19 Втр 21:20:37")
    private val fileTwo = FileItem(path = "two.webm", date = "14/05/19 Втр 21:20:37")
    private val fileThree = FileItem(path = "three", date = "14/05/19 Втр 21:20:37")
    private val fileItems = listOf(fileOne, fileTwo, fileThree)

    @Test
    fun `Shuffle movies and delete played movies`() {
        val movieShuffleList = movieUtils.shuffleMovies(movieList)

        Assert.assertNotEquals(movieList, movieShuffleList)
        Assert.assertEquals(movieList.last(), movieShuffleList.first())
        Assert.assertNotEquals(movieList.size, movieShuffleList.size)
        Assert.assertEquals(movieTwo, movieShuffleList.first())
    }

    @Test
    fun `Find diff movies and delete played movies`() {
        val movieResultList = movieUtils.calculateDiff(movieList, movieDiffList)

        Assert.assertNotEquals(movieList, movieResultList)
        Assert.assertEquals(1, movieResultList.size)
        Assert.assertEquals(movieThree, movieResultList.first())
    }

    @Test
    fun `Getting index movie of movie list`() {
        val pos = movieUtils.getIndexPosition(movieThree, movieDiffList)
        val posNothing = movieUtils.getIndexPosition(movieThree, movieList)
        Assert.assertEquals(1, pos)
        Assert.assertEquals(0, posNothing)
    }

    @Test
    fun `Filter file as only webm file`() {
        val resultList = movieUtils.filterFileItemOnlyAsWebm(fileItems)

        Assert.assertEquals(2, resultList.size)
        Assert.assertEquals(false, resultList.contains(fileThree))
        Assert.assertEquals(true, resultList.contains(fileOne))
        Assert.assertEquals(true, resultList.contains(fileTwo))
    }


    @Test
    fun `Sort movies by date`() {
        val resultList = movieUtils.sortByDate(movieList)

        Assert.assertEquals(movieList.first(), resultList.last())
        Assert.assertEquals(movieList.last(), resultList.first())
    }
}
