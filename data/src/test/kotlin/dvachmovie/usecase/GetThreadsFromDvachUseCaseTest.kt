package dvachmovie.usecase

import dvachmovie.api.DvachMovieApi
import dvachmovie.architecture.logging.Logger
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class GetThreadsFromDvachUseCaseTest {

    @InjectMocks
    lateinit var getThreadsFromDvachUseCase: GetThreadsFromDvachUseCase

    @Mock
    lateinit var dvachApi: DvachMovieApi

    @Mock
    lateinit var logger: Logger

    private val testBoard = "test"

    private val testException = RuntimeException("test")

    private val testUseCaseModel = TestUseCaseModel()

    @Before
    fun `SetUp` () {
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
        getThreadsFromDvachUseCase.addParams(testBoard, counterWebm, executorResult)

    }

    @Test
    fun `Happy pass`(){
       // given(dvachApi.getCatalog(testBoard)).willReturn()
        getThreadsFromDvachUseCase.execute()
    }
}