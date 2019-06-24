package dvachmovie.usecase.moviestorage

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.storage.MovieStorage
import dvachmovie.utils.MovieUtils
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetIndexPosByMovieUseCaseTest {

    @InjectMocks
    lateinit var useCase: GetIndexPosByMovieUseCase

    @Mock
    lateinit var movieStorage: MovieStorage

    @Mock
    lateinit var movieUtils: MovieUtils

    private val testMovie = NullMovie()
    private val testListMovies = listOf(NullMovie(), NullMovie("test"))

    @Test
    fun `Happy pass`() {
        given(movieStorage.movieList).willReturn(testListMovies)
        given(movieUtils.getIndexPosition(testMovie, testListMovies)).willReturn(10)
        Assert.assertEquals(10, useCase.execute(testMovie))
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(movieStorage.movieList).willReturn(testListMovies)
        given(movieUtils.getIndexPosition(testMovie, testListMovies)).willThrow(TestException())
        useCase.execute(testMovie)
    }
}
