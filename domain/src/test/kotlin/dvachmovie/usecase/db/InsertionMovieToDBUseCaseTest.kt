package dvachmovie.usecase.db

import dvachmovie.TestException
import dvachmovie.db.data.Movie
import dvachmovie.db.data.NullMovie
import dvachmovie.repository.MovieDBRepository
import kotlinx.coroutines.runBlocking
import org.joda.time.LocalDateTime
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InsertionMovieToDBUseCaseTest {

    @InjectMocks
    lateinit var useCase: InsertionMovieToDBUseCase

    @Mock
    lateinit var movieDBRepository: MovieDBRepository

    private val movie = NullMovie("test")

    private val movieTwo = object : Movie {
        override val movieUrl: String= ""
        override val previewUrl: String= ""
        override val board: String= ""
        override var isPlayed: Boolean = false
        override var date: LocalDateTime = LocalDateTime()
        override val md5: String= ""
        override val post: Long = 0
        override val thread: Long = 0
        override val baseUrl: String= ""
        override var dateAddedToDB: LocalDateTime = LocalDateTime()
    }

    @Test
    fun `Happy pass`() {
        runBlocking {
            useCase.executeAsync(movie)
            useCase.executeAsync(movieTwo)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(movieDBRepository.insert(movieTwo)).willThrow(TestException())

            useCase.executeAsync(movieTwo)
        }
    }
}
