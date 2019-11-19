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
class MarkCurrentMovieAsPlayedUseCaseTest {

    @InjectMocks
    lateinit var useCase: MarkCurrentMovieAsPlayedUseCase

    @Mock
    lateinit var movieStorage: MovieStorage

    @Test
    fun `Happy pass`() {
        useCase.execute(999)

        given(movieStorage.movieList).willReturn(listOf(NullMovie()))
        useCase.execute(0)
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(movieStorage.setCurrentMovieAndUpdate(NullMovie())).willThrow(TestException())
        useCase.execute(999)
    }
}
