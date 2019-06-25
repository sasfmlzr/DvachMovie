package dvachmovie.usecase.utils

import dvachmovie.db.data.Movie
import dvachmovie.utils.MovieUtils
import kotlinx.coroutines.runBlocking
import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SortMovieByDateUseCaseTest {

    @InjectMocks
    private lateinit var useCase: SortMovieByDateUseCase

    @Mock
    private lateinit var movieUtils: MovieUtils

    @Test
    fun `Movie list sorted by date`() {
        val movieOne = object : Movie {
            override val movieUrl: String
                get() = "testOne"
            override val previewUrl: String
                get() = "testOne"
            override val board: String
                get() = "testOne"
            override var isPlayed: Boolean
                get() = false
                set(value) {}
            override var date: LocalDateTime
                get() = LocalDateTime()
                set(value) {}
            override val md5: String
                get() = "testOne"
            override val post: Long
                get() = 0
            override val thread: Long
                get() = 0
        }
        val movieTwo = object : Movie {
            override val movieUrl: String
                get() = "testTwo"
            override val previewUrl: String
                get() = "testTwo"
            override val board: String
                get() = "testTwo"
            override var isPlayed: Boolean
                get() = false
                set(value) {}
            override var date: LocalDateTime
                get() = LocalDateTime()
                set(value) {}
            override val md5: String
                get() = "testTwo"
            override val post: Long
                get() = 0
            override val thread: Long
                get() = 0
        }
        val testList = listOf(movieOne, movieTwo)

        val resultList = listOf(movieTwo, movieOne)
        given(movieUtils.sortByDate(testList)).willReturn(resultList)

        runBlocking {
            Assert.assertEquals(resultList, useCase.execute(testList))
        }
    }
}