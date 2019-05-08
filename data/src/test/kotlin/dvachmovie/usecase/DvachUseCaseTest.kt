package dvachmovie.usecase

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class DvachUseCaseTest {

    @InjectMocks
    lateinit var dvachUseCase: DvachUseCase

    @Mock
    lateinit var getThreadUseCase: GetThreadsFromDvachUseCase

    @Mock
    lateinit var getLinkFilesFromThreadsUseCase: GetLinkFilesFromThreadsUseCase

    val testException = RuntimeException("test")

    val testUseCaseModel = TestUseCaseModel()

    @Before
    fun `SetUp`() {
        val counterWebm = object : CounterWebm {
            override fun updateCurrentCountVideos(count: Int) {

            }

            override fun updateCountVideos(count: Int) {

            }
        }

        val executorResult = object : ExecutorResult {
            override fun onSuccess(useCaseModel: UseCaseModel) {
                Assert.assertEquals(testUseCaseModel, useCaseModel)
            }

            override fun onFailure(t: Throwable) {
                Assert.assertEquals(testException, t)
            }

        }
        dvachUseCase.addParams("test", counterWebm, executorResult)
    }

    @Test
    fun `Happy pass`() {
        dvachUseCase.execute()
    }
}