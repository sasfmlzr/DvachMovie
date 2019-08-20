package dvachmovie.pipe.db

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.usecase.db.DeleteMoviesUseCase
import dvachmovie.usecase.db.GetMoviesFromDBUseCase
import kotlinx.coroutines.runBlocking
import org.joda.time.LocalDateTime
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DeleteOldMoviesPipeTest {

    @InjectMocks
    lateinit var pipe: DeleteOldMoviesPipe

    @Mock
    lateinit var useCase: DeleteMoviesUseCase

    @Mock
    lateinit var getMoviesFromDBUseCase: GetMoviesFromDBUseCase

    private val onlyNotPlayedMovies = listOf(NullMovie("0", isPlayed = false,
            dateAddedToDB = LocalDateTime("2002")),
            NullMovie("1", isPlayed = false,
                    dateAddedToDB = LocalDateTime("2002")),
            NullMovie("2", isPlayed = false,
                    dateAddedToDB = LocalDateTime("2002")))

    private val onlyNewMovies = listOf(NullMovie("0", isPlayed = false),
            NullMovie("1", isPlayed = false),
            NullMovie("2", isPlayed = false))

    private val onlyOldPlayedMovies = listOf(NullMovie("0", isPlayed = true,
            dateAddedToDB = LocalDateTime ("2002")),
            NullMovie("1", isPlayed = true,
                    dateAddedToDB = LocalDateTime("2002")),
            NullMovie("2", isPlayed = true,
                    dateAddedToDB = LocalDateTime("2002")))

    @Test
    fun `Happy pass`() {
        runBlocking {
            given(getMoviesFromDBUseCase.executeAsync(Unit)).willReturn(onlyNotPlayedMovies)
            pipe.execute(Unit)

            given(getMoviesFromDBUseCase.executeAsync(Unit)).willReturn(onlyNewMovies)
            pipe.execute(Unit)

            given(getMoviesFromDBUseCase.executeAsync(Unit)).willReturn(onlyOldPlayedMovies)
            pipe.execute(Unit)
        }
    }

    @Test(expected = TestException::class)
    fun `Getting movies throw error`() {
        runBlocking {
            given(getMoviesFromDBUseCase.executeAsync(Unit)).willThrow(TestException())
            pipe.execute(Unit)
        }
    }

    @Test(expected = TestException::class)
    fun `Deleting movies throw error with not played list`() {
        runBlocking {
            given(getMoviesFromDBUseCase.executeAsync(Unit)).willReturn(onlyNotPlayedMovies)
            given(useCase.executeAsync(onlyNotPlayedMovies)).willThrow(TestException())
            pipe.execute(Unit)
        }
    }

    @Test(expected = TestException::class)
    fun `Deleting movies throw error with empty list`() {
        runBlocking {
            given(getMoviesFromDBUseCase.executeAsync(Unit)).willReturn(onlyNewMovies)
            given(useCase.executeAsync(listOf())).willThrow(TestException())
            pipe.execute(Unit)
        }
    }

    @Test(expected = TestException::class)
    fun `Deleting movies throw error with empty list(old played movies)`() {
        runBlocking {
            given(getMoviesFromDBUseCase.executeAsync(Unit)).willReturn(onlyOldPlayedMovies)
            given(useCase.executeAsync(listOf())).willThrow(TestException())
            pipe.execute(Unit)
        }
    }
}
