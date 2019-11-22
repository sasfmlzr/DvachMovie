package dvachmovie.utils

import dvachmovie.AppConfig
import dvachmovie.api.FileItem
import dvachmovie.db.model.MovieEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert
import org.junit.Test
import java.lang.RuntimeException

class LocalMovieConverterTest {

    private val movieConverter = LocalMovieConverter()

    private val board = "testBoard"
    private val dvachBaseUrl = AppConfig.DVACH_URL
    private val fourChanBaseUrl = AppConfig.FOURCHAN_URL
    private val neoChanBaseUrl = AppConfig.NEOCHAN_URL
    private val dvachFileOne = FileItem(path = "one.webm", date = "14/05/19 Втр 21:20:37")
    private val dvachFileTwo = FileItem(path = "two.webm", date = "14/05/19 Втр 21:20:37")
    private val dvachFileThree = FileItem(path = "three", date = "14/05/19 Втр 21:20:37")
    private val dvachFileItems = listOf(dvachFileOne, dvachFileTwo, dvachFileThree)
    private val fourChanFileOne = FileItem(path = "one.webm", date = "1344402680740")
    private val fourChanFileTwo = FileItem(path = "two.webm", date = "1344402680740")
    private val fourChanFileThree = FileItem(path = "three", date = "1344402680740")
    private val fourChanFileItems = listOf(fourChanFileOne, fourChanFileTwo, fourChanFileThree)

    private val movieEntityOne = MovieEntity(board = board,
            movieUrl = dvachBaseUrl + dvachFileOne.path,
            previewUrl = dvachBaseUrl,
            date = parseDateFromFileItem(dvachFileOne),
            baseUrl = dvachBaseUrl)
    private val movieEntityTwo = MovieEntity(board = board,
            movieUrl = dvachBaseUrl + dvachFileTwo.path,
            previewUrl = dvachBaseUrl,
            date = parseDateFromFileItem(dvachFileTwo),
            baseUrl = dvachBaseUrl)
    private val movieEntityThree = MovieEntity(board = board,
            movieUrl = dvachBaseUrl + dvachFileThree.path,
            previewUrl = dvachBaseUrl,
            date = parseDateFromFileItem(dvachFileThree),
            baseUrl = dvachBaseUrl)
    private val movieEntities = listOf(movieEntityOne,
            movieEntityTwo,
            movieEntityThree)


    private val fourChanMovieEntityOne = MovieEntity(board = board,
            movieUrl = fourChanFileOne.path,
            date = parseDateFromFileItem(dvachFileOne),
            baseUrl = fourChanBaseUrl)
    private val fourChanMovieEntityTwo = MovieEntity(board = board,
            movieUrl = fourChanFileTwo.path,
            date = parseDateFromFileItem(dvachFileTwo),
            baseUrl = fourChanBaseUrl)
    private val fourChanMovieEntityThree = MovieEntity(board = board,
            movieUrl = fourChanFileThree.path,
            date = parseDateFromFileItem(dvachFileThree),
            baseUrl = fourChanBaseUrl)
    private val fourChanMovieEntities = listOf(fourChanMovieEntityOne,
            fourChanMovieEntityTwo,
            fourChanMovieEntityThree)
    private val neoChanMovieEntityOne = MovieEntity(board = board,
            movieUrl = fourChanFileOne.path,
            date = parseDateFromFileItem(dvachFileOne),
            baseUrl = neoChanBaseUrl)
    private val neoChanMovieEntityTwo = MovieEntity(board = board,
            movieUrl = fourChanFileTwo.path,
            date = parseDateFromFileItem(dvachFileTwo),
            baseUrl = neoChanBaseUrl)
    private val neoChanMovieEntityThree = MovieEntity(board = board,
            movieUrl = fourChanFileThree.path,
            date = parseDateFromFileItem(dvachFileThree),
            baseUrl = neoChanBaseUrl)
    private val neoChanMovieEntities = listOf(neoChanMovieEntityOne,
            neoChanMovieEntityTwo,
            neoChanMovieEntityThree)

    @Test
    fun `Convert FileItem to MovieItem was successful for dvach`() {
        val resultList = movieConverter.convertFileItemToMovie(dvachFileItems, board, dvachBaseUrl)

        Assert.assertEquals(movieEntities, resultList)
    }


    @Test
    fun `Convert FileItem to MovieItem was successful for fourch`() {
        val resultList = movieConverter.convertFileItemToMovie(fourChanFileItems, board, AppConfig.FOURCHAN_URL)

        Assert.assertEquals(fourChanMovieEntities, resultList)
    }

    @Test
    fun `Convert FileItem to MovieItem was successful for neochan`() {
        val resultList = movieConverter.convertFileItemToMovie(fourChanFileItems, board, AppConfig.NEOCHAN_URL)

        Assert.assertEquals(neoChanMovieEntities, resultList)
    }

    @Test (expected = RuntimeException::class)
    fun `Convert FileItem to MovieItem threw error for unknown error`() {
        movieConverter.convertFileItemToMovie(fourChanFileItems, board, "Unknown board")
    }

    @Test
    fun `Convert string date to LocalDateTime was successful`() {
        val resultList = parseDateFromFileItem(dvachFileOne)

        Assert.assertEquals(LocalDateTime.parse(dvachFileOne.date,
                DateTimeFormat.forPattern
                ("dd/MM/YYYY '${dvachFileOne.date.substring(9, 12)}' HH:mm:ss"))
                .plusYears(2000), resultList)
    }

    private fun parseDateFromFileItem(fileItem: FileItem): LocalDateTime =
            LocalDateTime.parse(fileItem.date,
                    DateTimeFormat.forPattern
                    ("dd/MM/YYYY '${fileItem.date.substring(9, 12)}' HH:mm:ss"))
                    .plusYears(2000)
}
