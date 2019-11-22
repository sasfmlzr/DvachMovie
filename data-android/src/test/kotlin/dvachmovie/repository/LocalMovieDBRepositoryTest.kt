package dvachmovie.repository

import dvachmovie.TestException
import dvachmovie.db.model.MovieDao
import dvachmovie.db.model.MovieEntity
import kotlinx.coroutines.runBlocking
import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocalMovieDBRepositoryTest {

    @InjectMocks
    lateinit var repository: LocalMovieDBRepository

    @Mock
    lateinit var dao: MovieDao

    private val testBaseUrl = "testBaseUrl"
    private val testBoard = "testNameThread"

    private val testMovieEntity = MovieEntity("movieUrl", "previewUrl", testBoard,
            false, LocalDateTime(), "md5", 1, 5, testBaseUrl)

    @Test
    fun `Happy pass get movies`() {
        runBlocking {
            given(dao.getMovies()).willReturn(listOf(testMovieEntity))
            Assert.assertEquals(listOf(testMovieEntity), repository.getMovies())
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong - get movies`() {
        runBlocking {
            given(dao.getMovies()).willThrow(TestException())
            repository.getMovies()
        }
    }

    @Test
    fun `Happy pass get movies from board`() {
        runBlocking {
            given(dao.getMoviesFromBoard(testBaseUrl, testBoard)).willReturn(listOf(testMovieEntity))
            Assert.assertEquals(listOf(testMovieEntity), repository.getMoviesFromBoard(testBoard, testBaseUrl))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong - get movies from board`() {
        runBlocking {
            given(dao.getMoviesFromBoard(testBaseUrl, testBoard)).willThrow(TestException())
            repository.getMoviesFromBoard(testBoard, testBaseUrl)
        }
    }

    @Test
    fun `Happy pass insert`() {
        runBlocking {
            repository.insert(testMovieEntity)
        }
    }

    @Test
    fun `Happy pass insert all`() {
        runBlocking {
            repository.insertAll(listOf(testMovieEntity))
        }
    }

    @Test
    fun `Happy pass delete all`() {
        runBlocking {
            repository.deleteAll()
        }
    }

    @Test
    fun `Happy pass delete movies`() {
        runBlocking {
            repository.deleteMovies(listOf(testMovieEntity))
        }
    }
}
