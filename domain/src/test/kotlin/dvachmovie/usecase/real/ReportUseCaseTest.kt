package dvachmovie.usecase.real

import dvachmovie.TestException
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
internal class ReportUseCaseTest {

    @InjectMocks
    private lateinit var useCase: ReportUseCase

    @Mock
    private lateinit var dvachRepository: DvachRepository


    private val testException = TestException()

    private val executorResult = object : ExecutorResult {
        override fun onSuccess(useCaseModel: UseCaseModel) {
            Assert.assertEquals("Test", (useCaseModel as DvachReportModel).message)
        }

        override fun onFailure(t: Throwable) {
            Assert.assertEquals(testException, t)
        }
    }

    @Test
    fun `Happy pass`() {
        runBlocking {
            useCase.addParams("test", 0, 0, executorResult)
            given(dvachRepository.reportPost("test", 0, 0, "Adult content")).willReturn("Test")
            useCase.execute()
        }
    }

    @Test
    fun `Error send to callback`() {
        runBlocking {
            useCase.addParams("test", 0, 0, executorResult)
            given(dvachRepository.reportPost("test", 0, 0, "Adult content")).willThrow(testException)
            useCase.execute()
        }
    }
}
