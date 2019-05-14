package dvachmovie.usecase

import dvachmovie.TestException
import dvachmovie.api.FileItem
import dvachmovie.data.BuildConfig
import dvachmovie.db.data.MovieEntity
import dvachmovie.repository.local.MovieUtils
import dvachmovie.usecase.base.CounterWebm
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.GetLinkFilesFromThreadsModel
import dvachmovie.usecase.real.GetLinkFilesFromThreadsUseCase
import dvachmovie.usecase.real.GetThreadsFromDvachModel
import dvachmovie.usecase.real.GetThreadsFromDvachUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DvachUseCaseTest {
    @InjectMocks
    lateinit var dvachUseCase: DvachUseCase

    @Mock
    lateinit var getThreadUseCase: GetThreadsFromDvachUseCase
    @Mock
    lateinit var getLinkFilesFromThreadsUseCase:
            GetLinkFilesFromThreadsUseCase

    private val board = "testBoard"
    private val fileOne = FileItem(path = "one.webm", date = "14/05/19 Втр 21:20:37")
    private val fileTwo = FileItem(path = "two.webm", date = "14/05/19 Втр 21:20:37")

    private val numOne = "One"
    private val numTwo = "Two"
    private val model = GetThreadsFromDvachModel(listOf(numOne, numTwo))

    private val linkOneModel = GetLinkFilesFromThreadsModel(listOf(fileOne))
    private val linkTwoModel = GetLinkFilesFromThreadsModel(listOf(fileTwo))
    private val linkNullModel = GetLinkFilesFromThreadsModel(listOf())

    private val testException = TestException()

    private val movieEntityOne = MovieEntity("${BuildConfig.DVACH_URL}one.webm",
            board = board,
            previewUrl = BuildConfig.DVACH_URL,
            date = MovieUtils.parseDateFromFileItem(fileOne))
    private val movieEntityTwo = MovieEntity("${BuildConfig.DVACH_URL}two.webm",
            board = board,
            previewUrl = BuildConfig.DVACH_URL,
            date = MovieUtils.parseDateFromFileItem(fileTwo))

    private val resultHappyModel = DvachModel(listOf(movieEntityOne, movieEntityTwo))
    private val resultPartOfModel = DvachModel(listOf(movieEntityOne))

    private var happyExecutorResult = object : ExecutorResult {
        override fun onSuccess(useCaseModel: UseCaseModel) {
            Assert.assertEquals(resultHappyModel, useCaseModel)
        }

        override fun onFailure(t: Throwable) {
            Assert.assertEquals(testException, t)
        }
    }

    private var partOfSuccessfulyExecutorResult = object : ExecutorResult {
        override fun onSuccess(useCaseModel: UseCaseModel) {
            Assert.assertEquals(resultPartOfModel, useCaseModel)
        }

        override fun onFailure(t: Throwable) {
            Assert.assertEquals(testException, t)
        }
    }

    private var zeroExecutorResult = object : ExecutorResult {
        override fun onSuccess(useCaseModel: UseCaseModel) {
            Assert.assertEquals(null, useCaseModel)
        }

        override fun onFailure(t: Throwable) {
            Assert.assertEquals("This is a private board", t.message)
        }
    }

    private var counterWebm = object : CounterWebm {
        override fun updateCurrentCountVideos(count: Int) {}

        override fun updateCountVideos(count: Int) {}
    }

    @Test
    fun happypass() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.execute(threadModel)).willReturn(model)

            val linkFilesModelOne = GetLinkFilesFromThreadsUseCase.Params(board, numOne)
            val linkFilesModelTwo = GetLinkFilesFromThreadsUseCase.Params(board, numTwo)

            given(getLinkFilesFromThreadsUseCase
                    .execute(linkFilesModelOne)).willReturn(linkOneModel)
            given(getLinkFilesFromThreadsUseCase
                    .execute(linkFilesModelTwo)).willReturn(linkTwoModel)

            val dvachInputModel = DvachUseCase.Params(board, counterWebm, happyExecutorResult)
            dvachUseCase.execute(dvachInputModel)
        }
    }

    @Test
    fun getThreadUseCaseEmitsError() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.execute(threadModel)).willThrow(testException)

            val dvachInputModel = DvachUseCase.Params(board, counterWebm, happyExecutorResult)
            dvachUseCase.execute(dvachInputModel)
        }
    }

    @Test
    fun getLinkFilesFromThreadsUseCaseEmitsError() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.execute(threadModel)).willReturn(model)

            val linkFilesModelOne = GetLinkFilesFromThreadsUseCase.Params(board, numOne)
            val linkFilesModelTwo = GetLinkFilesFromThreadsUseCase.Params(board, numTwo)

            given(getLinkFilesFromThreadsUseCase
                    .execute(linkFilesModelOne)).willReturn(linkOneModel)
            given(getLinkFilesFromThreadsUseCase
                    .execute(linkFilesModelTwo)).willThrow(testException)

            val dvachInputModel = DvachUseCase.Params(board, counterWebm,
                    partOfSuccessfulyExecutorResult)
            dvachUseCase.execute(dvachInputModel)
        }
    }

    @Test
    fun dvachUseCaseReturnZeroMovies() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.execute(threadModel)).willReturn(model)

            val linkFilesModelOne = GetLinkFilesFromThreadsUseCase.Params(board, numOne)
            val linkFilesModelTwo = GetLinkFilesFromThreadsUseCase.Params(board, numTwo)

            given(getLinkFilesFromThreadsUseCase
                    .execute(linkFilesModelOne)).willReturn(linkNullModel)
            given(getLinkFilesFromThreadsUseCase
                    .execute(linkFilesModelTwo)).willReturn(linkNullModel)

            val dvachInputModel = DvachUseCase.Params(board, counterWebm,
                    zeroExecutorResult)
            dvachUseCase.execute(dvachInputModel)
        }
    }
}
