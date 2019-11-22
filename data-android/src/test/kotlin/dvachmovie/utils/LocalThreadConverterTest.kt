package dvachmovie.utils

import dvachmovie.AppConfig
import dvachmovie.api.FileItem
import dvachmovie.db.model.ThreadEntity
import org.joda.time.Instant
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat
import org.junit.Assert
import org.junit.Test

class LocalThreadConverterTest {

    private val movieConverter = LocalThreadConverter()

    private val board = "testBoard"
    private val dvachBaseUrl = AppConfig.DVACH_URL
    private val fourChanBaseUrl = AppConfig.FOURCHAN_URL
    private val neoChanBaseUrl = AppConfig.NEOCHAN_URL
    private val dvachFileOne = FileItem(path = "one.webm",
            date = "14/05/19 Втр 21:20:37",
            numThread = 1L,
            threadName = dvachBaseUrl)
    private val dvachFileTwo = FileItem(path = "two.webm",
            date = "14/05/19 Втр 21:20:37",
            numThread = 2L,
            threadName = dvachBaseUrl)
    private val dvachFileThree = FileItem(path = "three",
            date = "14/05/19 Втр 21:20:37",
            numThread = 3L,
            threadName = dvachBaseUrl)
    private val dvachFileItems = listOf(dvachFileOne, dvachFileTwo, dvachFileThree)
    private val fourChanFileOne = FileItem(path = "one.webm",
            date = "1536684856",
            numThread = 1L,
            threadName = fourChanBaseUrl)
    private val fourChanFileTwo = FileItem(path = "two.webm",
            date = "1536684856",
            numThread = 2L,
            threadName = fourChanBaseUrl)
    private val fourChanFileThree = FileItem(path = "three",
            date = "1536684856",
            numThread = 3L,
            threadName = fourChanBaseUrl)
    private val fourChanFileItems = listOf(fourChanFileOne, fourChanFileTwo, fourChanFileThree)
    private val neoChanFileOne = FileItem(path = "one.webm",
            date = "1536684856",
            numThread = 1L,
            threadName = neoChanBaseUrl)
    private val neoChanFileTwo = FileItem(path = "two.webm",
            date = "1536684856",
            numThread = 2L,
            threadName = neoChanBaseUrl)
    private val neoChanFileThree = FileItem(path = "three",
            date = "1536684856",
            numThread = 3L,
            threadName = neoChanBaseUrl)
    private val neoChanFileItems = listOf(neoChanFileOne, neoChanFileTwo, neoChanFileThree)

    private val threadEntityOne = ThreadEntity(
            thread = 1L,
            nameThread = dvachBaseUrl,
            date = parseDateFromFileItem(dvachFileOne),
            baseUrl = dvachBaseUrl)
    private val threadEntityTwo = ThreadEntity(
            thread = 2L,
            nameThread = dvachBaseUrl,
            date = parseDateFromFileItem(dvachFileTwo),
            baseUrl = dvachBaseUrl)
    private val threadEntityThree = ThreadEntity(
            thread = 3L,
            nameThread = dvachBaseUrl,
            date = parseDateFromFileItem(dvachFileThree),
            baseUrl = dvachBaseUrl)
    private val threadEntities = listOf(threadEntityOne,
            threadEntityTwo,
            threadEntityThree)


    private val fourChanThreadEntityOne = ThreadEntity(
            thread = 1L,
            nameThread = fourChanBaseUrl,
            date = Instant.ofEpochSecond(fourChanFileOne.date.toLong())
                    .toDateTime()
                    .toLocalDateTime(),
            baseUrl = fourChanBaseUrl)
    private val fourChanThreadEntityTwo = ThreadEntity(
            thread = 2L,
            nameThread = fourChanBaseUrl,
            date = Instant.ofEpochSecond(fourChanFileTwo.date.toLong())
                    .toDateTime()
                    .toLocalDateTime(),
            baseUrl = fourChanBaseUrl)
    private val fourChanThreadEntityThree = ThreadEntity(
            thread = 3L,
            nameThread = fourChanBaseUrl,
            date = Instant.ofEpochSecond(fourChanFileThree.date.toLong())
                    .toDateTime()
                    .toLocalDateTime(),
            baseUrl = fourChanBaseUrl)
    private val fourChanThreadEntities = listOf(fourChanThreadEntityOne,
            fourChanThreadEntityTwo,
            fourChanThreadEntityThree)
    private val neoChanThreadEntityOne = ThreadEntity(
            thread = 1L,
            nameThread = neoChanBaseUrl,
            date = Instant.ofEpochSecond(neoChanFileOne.date.toLong())
                    .toDateTime()
                    .toLocalDateTime(),
            baseUrl = neoChanBaseUrl)
    private val neoChanThreadEntityTwo = ThreadEntity(
            thread = 2L,
            nameThread = neoChanBaseUrl,
            date = Instant.ofEpochSecond(neoChanFileTwo.date.toLong())
                    .toDateTime()
                    .toLocalDateTime(),
            baseUrl = neoChanBaseUrl)
    private val neoChanThreadEntityThree = ThreadEntity(
            thread = 3L,
            nameThread = neoChanBaseUrl,
            date = Instant.ofEpochSecond(neoChanFileThree.date.toLong())
                    .toDateTime()
                    .toLocalDateTime(),
            baseUrl = neoChanBaseUrl)
    private val neoChanThreadEntities = listOf(neoChanThreadEntityOne,
            neoChanThreadEntityTwo,
            neoChanThreadEntityThree)

    @Test
    fun `Convert FileItem to MovieItem was successful for dvach`() {
        val resultList = movieConverter.convertFileItemToThread(dvachFileItems, dvachBaseUrl)

        Assert.assertEquals(threadEntities, resultList)
    }


    @Test
    fun `Convert FileItem to MovieItem was successful for fourch`() {
        val resultList = movieConverter.convertFileItemToThread(fourChanFileItems, AppConfig.FOURCHAN_URL)

        Assert.assertEquals(fourChanThreadEntities, resultList)
    }

    @Test
    fun `Convert FileItem to MovieItem was successful for neochan`() {
        val resultList = movieConverter.convertFileItemToThread(neoChanFileItems, AppConfig.NEOCHAN_URL)

        Assert.assertEquals(neoChanThreadEntities, resultList)
    }

    @Test(expected = RuntimeException::class)
    fun `Convert FileItem to MovieItem threw error for unknown error`() {
        movieConverter.convertFileItemToThread(fourChanFileItems, "Unknown board")
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
