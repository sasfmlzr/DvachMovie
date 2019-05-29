package dvachmovie.usecase.real

import dvachmovie.TestException
import dvachmovie.api.FileItem
import dvachmovie.architecture.ScopeProvider
import dvachmovie.db.data.Movie
import dvachmovie.usecase.base.CounterWebm
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.utils.MovieUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Before
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

    @Mock
    lateinit var movieUtils: MovieUtils

    @Mock
    lateinit var scopeProvider: ScopeProvider

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

    private val movieEntityOne = object : Movie {
        override val movieUrl: String
            get() = "testOne.webm"
        override val previewUrl: String
            get() = ""
        override val board: String
            get() = ""
        override var isPlayed: Boolean
            get() = false
            set(value) {}
        override var date: LocalDateTime
            get() = LocalDateTime()
            set(value) {}
        override val md5: String
            get() = ""
        override val post: Long
            get() = 0
        override val thread: Long
            get() = 0
    }

    private val movieEntityTwo = object : Movie {
        override val movieUrl: String
            get() = "testTwo.webm"
        override val previewUrl: String
            get() = ""
        override val board: String
            get() = ""
        override var isPlayed: Boolean
            get() = false
            set(value) {}
        override var date: LocalDateTime
            get() = LocalDateTime()
            set(value) {}
        override val md5: String
            get() = ""
        override val post: Long
            get() = 0
        override val thread: Long
            get() = 0
    }

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
            Assert.assertEquals("This is a private board or internet problem", t.message)
        }
    }

    private var counterWebm = object : CounterWebm {
        override fun updateCurrentCountVideos(count: Int) {}

        override fun updateCountVideos(count: Int) {}
    }

    @Before
    fun `Set up`() {
        given(scopeProvider.ioScope).willReturn(CoroutineScope(Dispatchers.IO))
    }

    @Test
    fun `Happy pass`() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.execute(threadModel)).willReturn(model)

            val linkFilesModelOne = GetLinkFilesFromThreadsUseCase.Params(board, numOne)
            val linkFilesModelTwo = GetLinkFilesFromThreadsUseCase.Params(board, numTwo)

            given(getLinkFilesFromThreadsUseCase
                    .execute(linkFilesModelOne)).willReturn(linkOneModel)
            given(getLinkFilesFromThreadsUseCase
                    .execute(linkFilesModelTwo)).willReturn(linkTwoModel)

            given(movieUtils.filterFileItemOnlyAsWebm(
                    listOf(fileOne)))
                    .willReturn(listOf(fileOne))
            given(movieUtils.filterFileItemOnlyAsWebm(
                    listOf(fileTwo)))
                    .willReturn(listOf(fileTwo))
            given(movieUtils.convertFileItemToMovie(listOf(fileOne), board))
                    .willReturn(listOf(movieEntityOne))
            given(movieUtils.convertFileItemToMovie(listOf(fileTwo), board))
                    .willReturn(listOf(movieEntityTwo))

            val dvachInputModel = DvachUseCase.Params(board, counterWebm, happyExecutorResult)
            dvachUseCase.execute(dvachInputModel)
        }
    }

    @Test
    fun `Error emits error in the GetThreadUseCase`() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.execute(threadModel)).willThrow(testException)

            val dvachInputModel = DvachUseCase.Params(board, counterWebm, happyExecutorResult)
            dvachUseCase.execute(dvachInputModel)
        }
    }

    @Test
    fun `Error emits error but return a part of successful result`() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.execute(threadModel)).willReturn(model)

            val linkFilesModelOne = GetLinkFilesFromThreadsUseCase.Params(board, numOne)
            val linkFilesModelTwo = GetLinkFilesFromThreadsUseCase.Params(board, numTwo)

            given(getLinkFilesFromThreadsUseCase
                    .execute(linkFilesModelOne)).willReturn(linkOneModel)
            given(movieUtils.filterFileItemOnlyAsWebm(listOf(fileOne)))
                    .willReturn(listOf(fileOne))
            given(movieUtils.convertFileItemToMovie(listOf(fileOne), board))
                    .willReturn(listOf(movieEntityOne))
            given(getLinkFilesFromThreadsUseCase
                    .execute(linkFilesModelTwo)).willThrow(testException)

            val dvachInputModel = DvachUseCase.Params(board, counterWebm,
                    partOfSuccessfulyExecutorResult)
            dvachUseCase.execute(dvachInputModel)
        }
    }

    @Test
    fun `Return zero movies`() {
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
