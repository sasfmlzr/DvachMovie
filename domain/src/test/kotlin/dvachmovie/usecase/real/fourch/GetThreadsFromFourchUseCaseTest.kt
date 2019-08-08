package dvachmovie.usecase.real.fourch

import dvachmovie.TestException
import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.FourChanRepository
import dvachmovie.usecase.real.GetThreadsFromFourchUseCaseModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class GetThreadsFromFourchUseCaseTest {

    @InjectMocks
    private lateinit var useCase: GetThreadsFromFourchUseCase

    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var fourChanRepository: FourChanRepository

    private val testValue = "test"

    private val testException = TestException()

    private val model = GetThreadsFromFourchUseCase.Params("test")

    @Test
    fun `Happy pass`() {
        val expectedValue = listOf(Pair(1, testValue))
        runBlocking {
            given(fourChanRepository.getNumThreadsFromCatalog("test")).willReturn(expectedValue)
            Assert.assertEquals(GetThreadsFromFourchUseCaseModel(expectedValue),
                    useCase.executeAsync(model))
        }
    }

    @Test(expected = TestException::class)
    fun `Error send to callback`() {
        runBlocking {
            given(fourChanRepository.getNumThreadsFromCatalog("test")).willThrow(testException)
            useCase.executeAsync(model)
        }
    }
}
