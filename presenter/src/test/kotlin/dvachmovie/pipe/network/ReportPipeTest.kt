package dvachmovie.pipe.network

import dvachmovie.PresenterModel
import dvachmovie.TestException
import dvachmovie.architecture.ScopeProvider
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.ReportUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ReportPipeTest {

    @InjectMocks
    lateinit var pipe: ReportPipe

    @Mock
    lateinit var useCase: ReportUseCase

    @Mock
    lateinit var broadcastChannel: BroadcastChannel<PresenterModel>

    @Mock
    lateinit var scopeProvider: ScopeProvider

    private val executorResult = object : ExecutorResult {
        override suspend fun onSuccess(useCaseModel: UseCaseModel) {
        }

        override suspend fun onFailure(t: Throwable) {
        }
    }

    @Test
    fun `Happy pass`() {
        runBlocking {
            val params = ReportUseCase.Params("test", 0, 0, executorResult)
            pipe.execute(params)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong but pipe emitted error`() {
        runBlocking {
            val params = ReportUseCase.Params("test", 0, 0, executorResult)
            given(useCase.executeAsync(params)).willThrow(TestException())
            pipe.execute(params)
        }
    }
}
