package dvachmovie.usecase.moviestorage

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.storage.MovieStorage
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SetMovieListUseCaseTest {

    @InjectMocks
    lateinit var useCase: SetMovieListUseCase

    @Mock
    lateinit var movieStorage: MovieStorage

    private val testListMovies = listOf(NullMovie(), NullMovie("test"))
    @Test
    fun `Happy pass`() {
        useCase.execute(testListMovies)
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(movieStorage.setMovieListAndUpdate(testListMovies)).willThrow(TestException())
        useCase.execute(testListMovies)
    }
}
