package dvachmovie.usecase.real

import dvachmovie.TestException
import dvachmovie.api.FileItem
import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetLinkFilesFromThreadsUseCaseTest {

    @InjectMocks
    lateinit var getLinkFilesFromThreadsUseCase: GetLinkFilesFromThreadsUseCase

    @Mock
    lateinit var logger: Logger

    @Mock
    lateinit var dvachRepository: DvachRepository

    private val listFiles = listOf(FileItem())

    private val testException = TestException()

    private val executorResult = object : ExecutorResult {
        override fun onSuccess(useCaseModel: UseCaseModel) {
            Assert.assertEquals(listFiles, (useCaseModel as GetLinkFilesFromThreadsModel).fileItems)
        }

        override fun onFailure(t: Throwable) {
            Assert.assertEquals(testException, t)
        }
    }

    @Test
    fun `Happy pass`() {
        runBlocking {
            getLinkFilesFromThreadsUseCase.addParams("test", "test", executorResult)
            given(dvachRepository.getConcreteThreadByNum("test", "test")).willReturn(listFiles)
            getLinkFilesFromThreadsUseCase.execute()
        }
    }

    @Test
    fun `Error send to callback`() {
        runBlocking {
            getLinkFilesFromThreadsUseCase.addParams("test", "test", executorResult)
            given(dvachRepository.getConcreteThreadByNum("test", "test")).willThrow(testException)
            getLinkFilesFromThreadsUseCase.execute()
        }
    }
}
