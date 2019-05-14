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
internal class GetLinkFilesFromThreadsUseCaseTest {

    @InjectMocks
    private lateinit var useCase: GetLinkFilesFromThreadsUseCase

    @Mock
    private lateinit var logger: Logger

    @Mock
    private lateinit var dvachRepository: DvachRepository

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
            useCase.addParams("test", "test", executorResult)
            given(dvachRepository.getConcreteThreadByNum("test", "test")).willReturn(listFiles)
            useCase.execute()
        }
    }

    @Test
    fun `Error send to callback`() {
        runBlocking {
            useCase.addParams("test", "test", executorResult)
            given(dvachRepository.getConcreteThreadByNum("test", "test")).willThrow(testException)
            useCase.execute()
        }
    }
}
