package dvachmovie.usecase.real

import dvachmovie.TestException
import dvachmovie.api.FileItem
import dvachmovie.architecture.logging.Logger
import dvachmovie.repository.DvachRepository
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

    @Test
    fun `Happy pass`() {
        runBlocking {
            useCase.addParams("test", "test")
            given(dvachRepository.getConcreteThreadByNum("test", "test")).willReturn(listFiles)
            Assert.assertEquals(GetLinkFilesFromThreadsModel(listFiles), useCase.execute())

        }
    }

    @Test(expected = TestException::class)
    fun `Error send to callback`() {
        runBlocking {
            useCase.addParams("test", "test")
            given(dvachRepository.getConcreteThreadByNum("test", "test")).willThrow(testException)
            useCase.execute()
        }
    }
}
