package dvachmovie.usecase.real.fourch

import dvachmovie.TestException
import dvachmovie.api.FileItem
import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.FourChanRepository
import dvachmovie.usecase.real.GetLinkFilesFromThreadsUseCaseModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class GetLinkFilesFromThreadsFourchUseCaseTest {

    @InjectMocks
    private lateinit var useCase: GetLinkFilesFromThreadsFourchUseCase

    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var fourChanRepository: FourChanRepository

    private val testValue = FileItem("test")

    private val listNumThreads = listOf(testValue)

    private val testException = TestException()

    private val testBoard = "testBoard"

    private val model = GetLinkFilesFromThreadsFourchUseCase.Params(
            testBoard, "numThread", "nameThread")

    @Test
    fun `Happy pass`() {
        runBlocking {
            given(fourChanRepository.getConcreteThreadByNum(
                    testBoard, "numThread", "nameThread")).willReturn(listNumThreads)
            Assert.assertEquals(GetLinkFilesFromThreadsUseCaseModel(listNumThreads),
                    useCase.executeAsync(model))
        }
    }

    @Test(expected = TestException::class)
    fun `Get concrete thread by num throw exception`() {
        runBlocking {
            given(fourChanRepository.getConcreteThreadByNum(
                    testBoard, "numThread", "nameThread")).willThrow(testException)
            useCase.executeAsync(model)
        }
    }
}
