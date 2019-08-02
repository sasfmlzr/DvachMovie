package dvachmovie.utils

import dvachmovie.api.FileItem
import dvachmovie.db.model.MovieEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert
import org.junit.Test

class LocalMovieConverterTest {

    private val movieConverter = LocalMovieConverter()

    private val board = "testBoard"
    private val baseUrl = "testBaseUrl"
    private val fileOne = FileItem(path = "one.webm", date = "14/05/19 Втр 21:20:37")
    private val fileTwo = FileItem(path = "two.webm", date = "14/05/19 Втр 21:20:37")
    private val fileThree = FileItem(path = "three", date = "14/05/19 Втр 21:20:37")
    private val fileItems = listOf(fileOne, fileTwo, fileThree)

    private val movieEntityOne = MovieEntity(board = board,
            movieUrl = baseUrl + fileOne.path,
            previewUrl = baseUrl,
            date = parseDateFromFileItem(fileOne),
            baseUrl = baseUrl)
    private val movieEntityTwo = MovieEntity(board = board,
            movieUrl = baseUrl + fileTwo.path,
            previewUrl = baseUrl,
            date = parseDateFromFileItem(fileTwo),
            baseUrl = baseUrl)
    private val movieEntityThree = MovieEntity(board = board,
            movieUrl = baseUrl + fileThree.path,
            previewUrl = baseUrl,
            date = parseDateFromFileItem(fileThree),
            baseUrl = baseUrl)
    private val movieEntities = listOf(movieEntityOne,
            movieEntityTwo,
            movieEntityThree)

    @Test
    fun `Convert FileItem to MovieItem was successful`() {
        val resultList = movieConverter.convertFileItemToMovie(fileItems, board, baseUrl)

        Assert.assertEquals(movieEntities, resultList)
    }

    @Test
    fun `Convert string date to LocalDateTime was successful`() {
        val resultList = parseDateFromFileItem(fileOne)

        Assert.assertEquals(LocalDateTime.parse(fileOne.date,
                DateTimeFormat.forPattern
                ("dd/MM/YYYY '${fileOne.date.substring(9, 12)}' HH:mm:ss"))
                .plusYears(2000), resultList)
    }

    private fun parseDateFromFileItem(fileItem: FileItem): LocalDateTime =
            LocalDateTime.parse(fileItem.date,
                    DateTimeFormat.forPattern
                    ("dd/MM/YYYY '${fileItem.date.substring(9, 12)}' HH:mm:ss"))
                    .plusYears(2000)
}
