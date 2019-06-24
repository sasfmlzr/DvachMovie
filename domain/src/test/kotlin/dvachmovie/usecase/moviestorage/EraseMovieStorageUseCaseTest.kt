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
class EraseMovieStorageUseCaseTest {

    @InjectMocks
    lateinit var useCase: EraseMovieStorageUseCase

    @Mock
    lateinit var movieStorage: MovieStorage

    @Test
    fun `Happy pass`() {
        useCase.execute(Unit)
    }

    @Test(expected = TestException::class)
    fun `Something was wrong - movie list throw exception`() {
        given(movieStorage.setMovieListAndUpdate(listOf())).willThrow(TestException())
        useCase.execute(Unit)
    }

    @Test(expected = TestException::class)
    fun `Something was wrong - current movie throw exception`() {
        given(movieStorage.setCurrentMovieAndUpdate(NullMovie())).willThrow(TestException())
        useCase.execute(Unit)
    }
}
