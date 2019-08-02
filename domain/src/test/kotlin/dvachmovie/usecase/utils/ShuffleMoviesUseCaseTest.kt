package dvachmovie.usecase.utils

import dvachmovie.db.data.NullMovie
import dvachmovie.utils.MovieUtils
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShuffleMoviesUseCaseTest {

    @InjectMocks
    private lateinit var useCase: ShuffleMoviesUseCase

    @Mock
    private lateinit var movieUtils: MovieUtils

    @Test
    fun `Movie list shuffled`() {
        val movieOne = NullMovie("one")
        val movieTwo = NullMovie("two")
        val testList = listOf(movieOne, movieTwo)

        val resultList = listOf(movieTwo, movieOne)
        given(movieUtils.shuffleMovies(testList)).willReturn(resultList)

        runBlocking {
            Assert.assertEquals(resultList, useCase.executeAsync(testList))
        }
    }
}
