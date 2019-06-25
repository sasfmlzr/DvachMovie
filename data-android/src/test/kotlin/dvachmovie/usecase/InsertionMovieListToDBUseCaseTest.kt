package dvachmovie.usecase

import dvachmovie.TestException
import dvachmovie.db.data.Movie
import dvachmovie.repository.MovieDBRepository
import dvachmovie.usecase.db.InsertionMovieListToDBUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.doNothing
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InsertionMovieListToDBUseCaseTest {

    @InjectMocks
    lateinit var insertionMovieListToDBUseCase: InsertionMovieListToDBUseCase

    @Mock
    lateinit var movieDBRepository: MovieDBRepository

    private val movieList = listOf<Movie>()

    @Test
    fun `Happy pass`() {
        runBlocking {
            doNothing().`when`(movieDBRepository).insertAll(movieList)

            insertionMovieListToDBUseCase.execute(movieList)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(movieDBRepository.insertAll(movieList)).willThrow(TestException())

            insertionMovieListToDBUseCase.execute(movieList)
        }
    }
}
