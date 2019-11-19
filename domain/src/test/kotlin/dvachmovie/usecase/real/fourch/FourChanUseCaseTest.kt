package dvachmovie.usecase.real.fourch

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
import dvachmovie.usecase.real.GetThreadsFromFourchUseCaseModel
import dvachmovie.usecase.real.dvach.DvachUseCase
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
class FourChanUseCaseTest {
    @InjectMocks
    lateinit var fourChanUseCase: FourChanUseCase

    @Mock
    lateinit var getThreadsFromFourchUseCase: GetThreadsFromFourchUseCase
    @Mock
    lateinit var getLinkFilesFromThreadsFourchUseCase:
            GetLinkFilesFromThreadsFourchUseCase

    @Mock
    lateinit var movieUtils: MovieUtils

    @Mock
    lateinit var movieConverter: MovieConverter

    @Mock
    lateinit var threadConverter: ThreadConverter

    @Mock
    lateinit var scopeProvider: ScopeProvider

    private val board = "testBoard"

    private val numOne = "One"
    private val numTwo = "Two"
    private val model = GetThreadsFromFourchUseCaseModel(listOf(Pair(1, numOne), Pair(2, numTwo)))

    private val testException = TestException()

    private val movieEntityOne = NullMovie("one")
    private val movieEntityTwo = NullMovie("two")

    private val resultHappyModel = DvachUseCaseModel(listOf(movieEntityOne, movieEntityTwo), listOf(NullThread()))
    private val resultPartOfModel = DvachUseCaseModel(listOf(movieEntityOne), listOf())

    private val threadModel = GetThreadsFromFourchUseCase.Params(board)

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
            given(getThreadsFromFourchUseCase.executeAsync(threadModel)).willReturn(model)

            val dvachInputModel = DvachUseCase.Params(board, happyExecutorResult)
            fourChanUseCase.executeAsync(dvachInputModel)
        }
    }

    @Test
    fun `Deep happy pass`() {
        runBlocking {
            given(getThreadsFromFourchUseCase.executeAsync(threadModel)).willReturn(model)

            val modelOne = GetLinkFilesFromThreadsFourchUseCase.Params(board, model.listThreads[0].first.toString(), model.listThreads[0].second)
            given(getLinkFilesFromThreadsFourchUseCase.executeAsync(modelOne)).willReturn(GetLinkFilesFromThreadsUseCaseModel(listOf()))

            val modelTwo = GetLinkFilesFromThreadsFourchUseCase.Params(board, model.listThreads[1].first.toString(), model.listThreads[1].second)
            val files = listOf(FileItem("Asdas"))
            given(getLinkFilesFromThreadsFourchUseCase.executeAsync(modelTwo)).willReturn(GetLinkFilesFromThreadsUseCaseModel(files))
            given(movieUtils.filterFileItemOnlyAsMovie(files)).willReturn(files)
            val resultMovies = listOf(movieEntityOne, movieEntityTwo)
            given(movieConverter.convertFileItemToMovie(files, board, AppConfig.FOURCHAN_URL)).willReturn(resultMovies)
            val resultThreads = listOf(NullThread())
            given(threadConverter.convertFileItemToThread(files, AppConfig.FOURCHAN_URL)).willReturn(resultThreads)
            val dvachInputModel = DvachUseCase.Params(board, happyExecutorResult)
            fourChanUseCase.executeAsync(dvachInputModel)
            fourChanUseCase.forceStart()
        }
    }

    @Test
    fun `Error emits error in the GetThreadUseCase`() {
        runBlocking {
            given(getThreadsFromFourchUseCase.executeAsync(threadModel)).willThrow(testException)

            val dvachInputModel = DvachUseCase.Params(board, happyExecutorResult)
            fourChanUseCase.executeAsync(dvachInputModel)
        }
    }

    @Test
    fun `Error emits error but return a part of successful result`() {
        runBlocking {
            given(getThreadsFromFourchUseCase.executeAsync(threadModel)).willReturn(model)

            val dvachInputModel = DvachUseCase.Params(board, partOfSuccessfulyExecutorResult)
            fourChanUseCase.executeAsync(dvachInputModel)
        }
    }

    @Test
    fun `Return zero movies`() {
        runBlocking {
            given(getThreadsFromFourchUseCase.executeAsync(threadModel)).willReturn(model)

            val dvachInputModel = DvachUseCase.Params(board, zeroExecutorResult)
            fourChanUseCase.executeAsync(dvachInputModel)
        }
    }
}
