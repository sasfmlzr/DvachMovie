package dvachmovie.utils

import dvachmovie.api.FileItem
import dvachmovie.data.BuildConfig
import dvachmovie.db.data.MovieEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert
import org.junit.Test

class MovieUtilsTest {

    private val movieOne = MovieEntity("Whew", isPlayed = true)
    private val movieTwo = MovieEntity("Test", isPlayed = false)
    private val movieThree = MovieEntity("TestWhew", isPlayed = false)
    private val movieList = listOf(movieOne, movieTwo)
    private val movieDiffList = listOf(movieOne, movieThree)

    private val board = "testBoard"
    private val fileOne = FileItem(path = "one.webm", date = "14/05/19 Втр 21:20:37")
    private val fileTwo = FileItem(path = "two.webm", date = "14/05/19 Втр 21:20:37")
    private val fileThree = FileItem(path = "three", date = "14/05/19 Втр 21:20:37")
    private val fileItems = listOf(fileOne, fileTwo, fileThree)

    private val movieEntityOne = MovieEntity(board = board,
            movieUrl = BuildConfig.DVACH_URL + fileOne.path,
            previewUrl = BuildConfig.DVACH_URL,
            date = MovieUtils.parseDateFromFileItem(fileOne))
    private val movieEntityTwo = MovieEntity(board = board,
            movieUrl = BuildConfig.DVACH_URL + fileTwo.path,
            previewUrl = BuildConfig.DVACH_URL,
            date = MovieUtils.parseDateFromFileItem(fileTwo))
    private val movieEntityThree = MovieEntity(board = board,
            movieUrl = BuildConfig.DVACH_URL + fileThree.path,
            previewUrl = BuildConfig.DVACH_URL,
            date = MovieUtils.parseDateFromFileItem(fileThree))
    private val movieEntities = listOf(movieEntityOne,
            movieEntityTwo,
            movieEntityThree)

    @Test
    fun `Shuffle movies and delete played movies`() {
        val movieShuffleList = MovieUtils.shuffleMovies(movieList)

        Assert.assertNotEquals(movieList, movieShuffleList)
        Assert.assertEquals(movieList.last(), movieShuffleList.first())
        Assert.assertNotEquals(movieList.size, movieShuffleList.size)
        Assert.assertEquals(movieTwo, movieShuffleList.first())
    }

    @Test
    fun `Find diff movies and delete played movies`() {
        val movieResultList = MovieUtils.calculateDiff(movieList, movieDiffList)

        Assert.assertNotEquals(movieList, movieResultList)
        Assert.assertEquals(1, movieResultList.size)
        Assert.assertEquals(movieThree, movieResultList.first())
    }

    @Test
    fun `Getting index movie of movie list`() {
        val pos = MovieUtils.getIndexPosition(movieThree, movieDiffList)
        val posNothing = MovieUtils.getIndexPosition(movieThree, movieList)
        Assert.assertEquals(1, pos)
        Assert.assertEquals(0, posNothing)
    }

    @Test
    fun `Filter file as only webm file`() {
        val resultList = MovieUtils.filterFileItemOnlyAsWebm(fileItems)

        Assert.assertEquals(2, resultList.size)
        Assert.assertEquals(false, resultList.contains(fileThree))
        Assert.assertEquals(true, resultList.contains(fileOne))
        Assert.assertEquals(true, resultList.contains(fileTwo))
    }

    @Test
    fun `Convert FileItem to MovieItem`() {
        val resultList = MovieUtils.convertFileItemToMovieEntity(fileItems, board)

        Assert.assertEquals(movieEntities, resultList)
    }

    @Test
    fun `Convert string date to LocalDateTime`() {
        val resultList = MovieUtils.parseDateFromFileItem(fileOne)

        Assert.assertEquals(LocalDateTime.parse(fileOne.date,
                DateTimeFormat.forPattern
                ("dd/MM/YYYY '${fileOne.date.substring(9, 12)}' HH:mm:ss"))
                .plusYears(2000), resultList)
    }
}
