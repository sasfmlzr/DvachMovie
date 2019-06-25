package dvachmovie.pipe.utils

import dvachmovie.db.data.Movie
import dvachmovie.usecase.utils.SortMoviesByDateUseCase
import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SortMoviesByDatePipeTest {

    @InjectMocks
    lateinit var pipe: SortMoviesByDatePipe

    @Mock
    lateinit var useCase: SortMoviesByDateUseCase

    private val movieOne = object : Movie {
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

    private val movieTwo = object : Movie {
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

    private val testList = listOf(movieOne, movieTwo)
    private val resultList = listOf(movieTwo, movieOne)

    @Test
    fun `Happy pass`() {
        given(useCase.execute(testList)).willReturn(resultList)
        Assert.assertEquals(resultList, pipe.execute(testList))
    }
}