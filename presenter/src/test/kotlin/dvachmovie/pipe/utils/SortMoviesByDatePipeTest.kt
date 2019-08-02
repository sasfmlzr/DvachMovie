package dvachmovie.pipe.utils

import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.utils.SortMoviesByDateUseCase
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

    private val movieOne = NullMovie("testOne")

    private val movieTwo = NullMovie("testTwo")

    private val testList = listOf(movieOne, movieTwo)
    private val resultList = listOf(movieTwo, movieOne)

    @Test
    fun `Happy pass`() {
        given(useCase.execute(testList)).willReturn(resultList)
        Assert.assertEquals(resultList, pipe.execute(testList))
    }
}
