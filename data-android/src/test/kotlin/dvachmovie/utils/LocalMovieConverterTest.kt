package dvachmovie.utils

import dvachmovie.AppConfig
import dvachmovie.api.FileItem
import dvachmovie.db.model.MovieEntity
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert
import org.junit.Test

class LocalMovieConverterTest {

    private val appConfig = AppConfig("whew")
    private val movieConverter = LocalMovieConverter(appConfig)

    private val board = "testBoard"
    private val fileOne = FileItem(path = "one.webm", date = "14/05/19 Втр 21:20:37")
    private val fileTwo = FileItem(path = "two.webm", date = "14/05/19 Втр 21:20:37")
    private val fileThree = FileItem(path = "three", date = "14/05/19 Втр 21:20:37")
    private val fileItems = listOf(fileOne, fileTwo, fileThree)

    private val movieEntityOne = MovieEntity(board = board,
            movieUrl = appConfig.DVACH_URL + fileOne.path,
            previewUrl = appConfig.DVACH_URL,
            date = parseDateFromFileItem(fileOne))
    private val movieEntityTwo = MovieEntity(board = board,
            movieUrl = appConfig.DVACH_URL + fileTwo.path,
            previewUrl = appConfig.DVACH_URL,
            date = parseDateFromFileItem(fileTwo))
    private val movieEntityThree = MovieEntity(board = board,
            movieUrl = appConfig.DVACH_URL + fileThree.path,
            previewUrl = appConfig.DVACH_URL,
            date = parseDateFromFileItem(fileThree))
    private val movieEntities = listOf(movieEntityOne,
            movieEntityTwo,
            movieEntityThree)

    @Test
    fun `Convert FileItem to MovieItem was successful`() {
        val resultList = movieConverter.convertFileItemToMovie(fileItems, board)

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
