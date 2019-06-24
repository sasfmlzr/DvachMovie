package dvachmovie.usecase.moviestorage

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.storage.MovieStorage
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMovieListUseCaseTest {

    @InjectMocks
    lateinit var useCase: GetMovieListUseCase

    @Mock
    lateinit var movieStorage: MovieStorage

    @Test
    fun `Happy pass`() {
        val testListMovies = listOf(NullMovie(), NullMovie("test"))

        given(movieStorage.movieList).willReturn(testListMovies)
        Assert.assertEquals(testListMovies, useCase.execute(Unit))
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(movieStorage.movieList).willThrow(TestException())
        useCase.execute(Unit)
    }
}
