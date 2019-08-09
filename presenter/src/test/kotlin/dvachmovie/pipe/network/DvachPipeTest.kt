package dvachmovie.pipe.network

import dvachmovie.AppConfig
import dvachmovie.PresenterModel
import dvachmovie.TestException
import dvachmovie.architecture.ScopeProvider
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.dvach.DvachUseCase
import dvachmovie.usecase.real.fourch.FourChanUseCase
import dvachmovie.usecase.settingsstorage.GetBoardUseCase
import dvachmovie.usecase.settingsstorage.GetCurrentBaseUrlUseCase
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DvachPipeTest {

    @InjectMocks
    lateinit var pipe: DvachPipe

    @Mock
    lateinit var dvachUseCase: DvachUseCase

    @Mock
    lateinit var broadcastChannel: BroadcastChannel<PresenterModel>

    @Mock
    lateinit var scopeProvider: ScopeProvider

    @Mock
    lateinit var getBoardUseCase: GetBoardUseCase

    @Mock
    lateinit var currentBaseUrlUseCase: GetCurrentBaseUrlUseCase

    @Mock
    lateinit var fourChanUseCase: FourChanUseCase

    private val executorResult = object : ExecutorResult {
        override suspend fun onSuccess(useCaseModel: UseCaseModel) {
        }

        override suspend fun onFailure(t: Throwable) {
        }
    }

    private val params = DvachUseCase.Params("test", executorResult)

    @Test
    fun `Happy pass for dvach`() {
        runBlocking {
            given(currentBaseUrlUseCase.execute(Unit)).willReturn(AppConfig.DVACH_URL)
            given(getBoardUseCase.execute(Unit)).willReturn("test")
            given(dvachUseCase.executeAsync(params)).willReturn(Unit)
            pipe.execute(executorResult)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong but pipe emitted error for dvach`() {
        runBlocking {
            given(currentBaseUrlUseCase.execute(Unit)).willReturn(AppConfig.DVACH_URL)
            given(getBoardUseCase.execute(Unit)).willReturn("test")
            given(dvachUseCase.executeAsync(params)).willThrow(TestException())
            pipe.execute(executorResult)
        }
    }

    @Test
    fun `Happy pass for four chan`() {
        runBlocking {
            given(currentBaseUrlUseCase.execute(Unit)).willReturn(AppConfig.FOURCHAN_URL)
            given(getBoardUseCase.execute(Unit)).willReturn("test")
            given(fourChanUseCase.executeAsync(params)).willReturn(Unit)
            pipe.execute(executorResult)
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong but pipe emitted error for four chan`() {
        runBlocking {
            given(currentBaseUrlUseCase.execute(Unit)).willReturn(AppConfig.FOURCHAN_URL)
            given(getBoardUseCase.execute(Unit)).willReturn("test")
            given(fourChanUseCase.executeAsync(params)).willThrow(TestException())
            pipe.execute(executorResult)
        }
    }
}
