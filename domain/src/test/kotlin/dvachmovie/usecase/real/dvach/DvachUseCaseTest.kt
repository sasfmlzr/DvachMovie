package dvachmovie.usecase.real.dvach

import dvachmovie.AppConfig
import dvachmovie.TestException
import dvachmovie.api.FileItem
import dvachmovie.architecture.ScopeProvider
import dvachmovie.db.data.NullMovie
import dvachmovie.db.data.NullThread
import dvachmovie.usecase.base.ExecutorResult
import dvachmovie.usecase.base.UseCaseModel
import dvachmovie.usecase.real.DvachAmountRequestsUseCaseModel
import dvachmovie.usecase.real.DvachUseCaseModel
import dvachmovie.usecase.real.GetLinkFilesFromThreadsUseCaseModel
import dvachmovie.usecase.real.GetThreadsFromDvachUseCaseModel
import dvachmovie.utils.MovieConverter
import dvachmovie.utils.MovieUtils
import dvachmovie.utils.ThreadConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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
    lateinit var movieConverter: MovieConverter

    @Mock
    lateinit var threadConverter: ThreadConverter

    @Mock
    lateinit var scopeProvider: ScopeProvider

    private val board = "testBoard"
    private val baseUrl = "testBaseUrl"
    private val fileOne = FileItem(path = "one.webm", date = "14/05/19 Втр 21:20:37")
    private val fileTwo = FileItem(path = "two.webm", date = "14/05/19 Втр 21:20:37")

    private val numOne = "One"
    private val numTwo = "Two"
    private val model = GetThreadsFromDvachUseCaseModel(listOf(numOne, numTwo))

    private val linkOneModel = GetLinkFilesFromThreadsUseCaseModel(listOf(fileOne))
    private val linkTwoModel = GetLinkFilesFromThreadsUseCaseModel(listOf(fileTwo))
    private val linkNullModel = GetLinkFilesFromThreadsUseCaseModel(listOf())

    private val testException = TestException()

    private val movieEntityOne = NullMovie("one")
    private val movieEntityTwo = NullMovie("two")

    private val resultHappyModel = DvachUseCaseModel(listOf(movieEntityOne, movieEntityTwo), listOf(NullThread()))
    private val resultPartOfModel = DvachUseCaseModel(listOf(movieEntityOne), listOf())

    private var happyExecutorResult = object : ExecutorResult {
        override suspend fun onSuccess(useCaseModel: UseCaseModel) {
            when (useCaseModel) {
                is DvachAmountRequestsUseCaseModel ->
                    Assert.assertEquals(2, useCaseModel.max)
                is DvachUseCaseModel -> {
                    Assert.assertTrue(resultHappyModel.movies.containsAll(useCaseModel.movies))
                    Assert.assertTrue(resultHappyModel.threads.containsAll(useCaseModel.threads))
                    Assert.assertEquals(resultHappyModel.movies.size, useCaseModel.movies.size)
                    Assert.assertEquals(resultHappyModel.threads.size, useCaseModel.threads.size)
                }
            }
        }

        override suspend fun onFailure(t: Throwable) {
            Assert.assertEquals(testException, t)
        }
    }

    private var partOfSuccessfulyExecutorResult = object : ExecutorResult {
        override suspend fun onSuccess(useCaseModel: UseCaseModel) {
            when (useCaseModel) {
                is DvachAmountRequestsUseCaseModel ->
                    Assert.assertEquals(2, useCaseModel.max)
                is DvachUseCaseModel -> Assert.assertEquals(resultPartOfModel, useCaseModel)
            }
        }

        override suspend fun onFailure(t: Throwable) {
            Assert.assertEquals(testException, t)
        }
    }

    private var zeroExecutorResult = object : ExecutorResult {
        override suspend fun onSuccess(useCaseModel: UseCaseModel) {
            when (useCaseModel) {
                is DvachAmountRequestsUseCaseModel ->
                    Assert.assertEquals(2, useCaseModel.max)
                is DvachUseCaseModel -> Assert.assertEquals(null, useCaseModel)
            }
        }

        override suspend fun onFailure(t: Throwable) {
            Assert.assertEquals("This is a private board or internet problem", t.message)
        }
    }

    @Before
    fun `Set up`() {
        given(scopeProvider.ioScope).willReturn(CoroutineScope(Dispatchers.Unconfined))
    }

    @Test
    fun `Happy pass`() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.executeAsync(threadModel)).willReturn(model)

            val linkFilesModelOne = GetLinkFilesFromThreadsUseCase.Params(board, numOne)
            val linkFilesModelTwo = GetLinkFilesFromThreadsUseCase.Params(board, numTwo)

            given(getLinkFilesFromThreadsUseCase
                    .executeAsync(linkFilesModelOne)).willReturn(linkOneModel)
            given(getLinkFilesFromThreadsUseCase
                    .executeAsync(linkFilesModelTwo)).willReturn(linkTwoModel)

            given(movieUtils.filterFileItemOnlyAsMovie(
                    listOf(fileOne)))
                    .willReturn(listOf(fileOne))
            given(movieUtils.filterFileItemOnlyAsMovie(
                    listOf(fileTwo)))
                    .willReturn(listOf(fileTwo))

            given(movieConverter.convertFileItemToMovie(listOf(fileOne),
                    board,
                    AppConfig.DVACH_URL)).willReturn(listOf(movieEntityOne))

            given(movieConverter.convertFileItemToMovie(listOf(fileTwo),
                    board,
                    AppConfig.DVACH_URL)).willReturn(listOf(movieEntityTwo))

            given(threadConverter.convertFileItemToThread(listOf(fileOne), AppConfig.DVACH_URL))
                    .willReturn(listOf(NullThread()))

            val dvachInputModel = DvachUseCase.Params(board, happyExecutorResult)
            dvachUseCase.executeAsync(dvachInputModel)
        }
    }

    @Test
    fun `Deep happy pass`() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.executeAsync(threadModel)).willReturn(model)

            val modelOne = GetLinkFilesFromThreadsUseCase.Params(board, model.listThreads[0])
            given(getLinkFilesFromThreadsUseCase.executeAsync(modelOne)).willReturn(GetLinkFilesFromThreadsUseCaseModel(listOf()))

            val modelTwo = GetLinkFilesFromThreadsUseCase.Params(board, model.listThreads[1])
            val files = listOf(FileItem("Asdas"))
            given(getLinkFilesFromThreadsUseCase.executeAsync(modelTwo)).willReturn(GetLinkFilesFromThreadsUseCaseModel(files))
            given(movieUtils.filterFileItemOnlyAsMovie(files)).willReturn(files)
            val resultMovies = listOf(movieEntityOne, movieEntityTwo)
            given(movieConverter.convertFileItemToMovie(files, board, AppConfig.DVACH_URL)).willReturn(resultMovies)
            val resultThreads = listOf(NullThread())
            given(threadConverter.convertFileItemToThread(files, AppConfig.DVACH_URL)).willReturn(resultThreads)
            val dvachInputModel = DvachUseCase.Params(board, happyExecutorResult)
            dvachUseCase.executeAsync(dvachInputModel)
            dvachUseCase.forceStart()
        }
    }

    @Test
    fun `Error emits error in the GetThreadUseCase`() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.executeAsync(threadModel)).willThrow(testException)

            val dvachInputModel = DvachUseCase.Params(board, happyExecutorResult)
            dvachUseCase.executeAsync(dvachInputModel)
        }
    }

    @Test
    fun `Error emits error but return a part of successful result`() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.executeAsync(threadModel)).willReturn(model)

            val linkFilesModelOne = GetLinkFilesFromThreadsUseCase.Params(board, numOne)
            val linkFilesModelTwo = GetLinkFilesFromThreadsUseCase.Params(board, numTwo)

            given(getLinkFilesFromThreadsUseCase
                    .executeAsync(linkFilesModelOne)).willReturn(linkOneModel)
            given(movieUtils.filterFileItemOnlyAsMovie(listOf(fileOne)))
                    .willReturn(listOf(fileOne))
            given(movieConverter.convertFileItemToMovie(listOf(fileOne),
                    board,
                    AppConfig.DVACH_URL)).willReturn(listOf(movieEntityOne))
            given(getLinkFilesFromThreadsUseCase
                    .executeAsync(linkFilesModelTwo)).willThrow(testException)

            val dvachInputModel = DvachUseCase.Params(board, partOfSuccessfulyExecutorResult)
            dvachUseCase.executeAsync(dvachInputModel)
        }
    }

    @Test
    fun `Return zero movies`() {
        runBlocking {
            val threadModel = GetThreadsFromDvachUseCase.Params(board)
            given(getThreadUseCase.executeAsync(threadModel)).willReturn(model)

            val linkFilesModelOne = GetLinkFilesFromThreadsUseCase.Params(board, numOne)
            val linkFilesModelTwo = GetLinkFilesFromThreadsUseCase.Params(board, numTwo)

            given(getLinkFilesFromThreadsUseCase
                    .executeAsync(linkFilesModelOne)).willReturn(linkNullModel)
            given(getLinkFilesFromThreadsUseCase
                    .executeAsync(linkFilesModelTwo)).willReturn(linkNullModel)

            val dvachInputModel = DvachUseCase.Params(board, zeroExecutorResult)
            dvachUseCase.executeAsync(dvachInputModel)
        }
    }
}
