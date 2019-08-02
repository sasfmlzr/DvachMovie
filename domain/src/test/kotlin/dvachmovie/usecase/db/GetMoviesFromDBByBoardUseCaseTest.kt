package dvachmovie.usecase.db

import dvachmovie.TestException
import dvachmovie.db.data.NullMovie
import dvachmovie.repository.MovieDBRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetMoviesFromDBByBoardUseCaseTest {

    @InjectMocks
    lateinit var useCase: GetMoviesFromDBByBoardUseCase

    @Mock
    lateinit var movieDBRepository: MovieDBRepository

    private val testBaseUrl = "testBaseUrl"
    private val testBoard = "test"

    @Test
    fun `Happy pass`() {
        runBlocking {
            val testList = listOf(NullMovie(), NullMovie("test"))
            given(movieDBRepository.getMoviesFromBoard(testBaseUrl, testBoard)).willReturn(testList)
            Assert.assertEquals(testList, useCase.executeAsync(Pair(testBaseUrl, testBoard)))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong`() {
        runBlocking {
            given(movieDBRepository.getMoviesFromBoard(testBaseUrl, testBoard)).willThrow(TestException())

            useCase.executeAsync(Pair(testBaseUrl, testBoard))
        }
    }
}
