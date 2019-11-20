package dvachmovie.repository

import dvachmovie.AppConfig
import dvachmovie.TestException
import dvachmovie.api.DvachMovieApi
import dvachmovie.api.FourchanApi
import dvachmovie.api.model.gson.b4chan.catalog.FourChanCatalogDTO
import dvachmovie.api.model.gson.b4chan.catalog.ThreadsDTO
import dvachmovie.api.model.gson.b4chan.thread.FourChanThreadDTO
import dvachmovie.api.model.gson.b4chan.thread.PostsDTO
import dvachmovie.api.model.gson.dvach.DvachReportRequest
import dvachmovie.api.model.gson.dvach.catalog.DvachCatalogRequest
import dvachmovie.api.model.gson.dvach.catalog.ThreadsItem
import dvachmovie.api.model.gson.dvach.thread.DvachThreadRequest
import dvachmovie.api.model.gson.dvach.thread.FileItem
import dvachmovie.api.model.gson.dvach.thread.PostsItem
import dvachmovie.architecture.logging.Logger
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocalFourChanRepositoryTest {

    @InjectMocks
    lateinit var repository: LocalFourChanRepository

    @Mock
    lateinit var api: FourchanApi

    @Mock
    lateinit var logger: Logger

    private val board = "test"
    private val thread = 1L
    private val threadName = "testTitle"

    @Test
    fun happyPassGetCatalog() {
        runBlocking {
            val pairs = listOf(Pair(3, "4"))
            given(api.getCatalog(board)).willReturn(listOf(FourChanCatalogDTO(
                    threads = listOf(ThreadsDTO(no = 3, com = "4")))))
            Assert.assertEquals(pairs, repository.getNumThreadsFromCatalog(board))
        }
    }

    @Test(expected = TestException::class)
    fun getCatalogEmittedError() {
        runBlocking {
            given(api.getCatalog(board)).willThrow(TestException())
            repository.getNumThreadsFromCatalog(board)
        }
    }

    @Test
    fun happyPassGetThread() {
        runBlocking {
            val postTim = 2L
            val postExt = "3"
            val fileItem = listOf(
                    dvachmovie.api.FileItem(path = "${AppConfig.FOURCHAN_WEBM_URL}/$board/${postTim}${postExt}",
                            thumbnail = "${AppConfig.FOURCHAN_THUMBNAIL_URL}/$board/${postTim}s.jpg",
                            md5 = "testMd5",
                            numThread = thread,
                            numPost = 1.toLong(),
                            date = "1",
                            threadName = threadName))
            given(api.getThread(board, thread.toString())).willReturn(FourChanThreadDTO(
                    posts = listOf(PostsDTO(),
                            PostsDTO(md5 = "testMd5",
                                    no = 1,
                                    tim = postTim,
                                    ext = postExt,
                                    time = 1))))
            Assert.assertEquals(fileItem, repository.getConcreteThreadByNum(
                    board, thread.toString(), threadName))
        }
    }

    @Test(expected = TestException::class)
    fun getThreadEmittedError() {
        runBlocking {
            given(api.getThread(board, thread.toString())).willThrow(TestException())
            repository.getConcreteThreadByNum(board, thread.toString(), threadName)
        }
    }
}
