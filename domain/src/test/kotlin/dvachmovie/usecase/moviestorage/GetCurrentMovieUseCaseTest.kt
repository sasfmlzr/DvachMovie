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
class GetCurrentMovieUseCaseTest {

    @InjectMocks
    lateinit var useCase: GetCurrentMovieUseCase

    @Mock
    lateinit var movieStorage: MovieStorage

    @Test
    fun `Happy pass`() {
        val testMovie = NullMovie()
        given(movieStorage.currentMovie).willReturn(testMovie)
        Assert.assertEquals(testMovie, useCase.execute(Unit))
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        given(movieStorage.currentMovie).willThrow(TestException())
        useCase.execute(Unit)
    }
}
