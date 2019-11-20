package dvachmovie.repository

import dvachmovie.TestException
import dvachmovie.api.DvachMovieApi
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
class LocalDvachRepositoryTest {

    @InjectMocks
    lateinit var repository: LocalDvachRepository

    @Mock
    lateinit var api: DvachMovieApi

    @Mock
    lateinit var logger: Logger

    private val board = "test"
    private val thread = 1L
    private val post = 1L
    private val comment = "comment"

    @Test
    fun happyPassGetCatalog() {
        runBlocking {
            val nums = listOf("3")
            given(api.getCatalog(board)).willReturn(DvachCatalogRequest(threads = listOf(ThreadsItem(num = "3"))))
            Assert.assertEquals(nums, repository.getNumThreadsFromCatalog(board))
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
            val fileItem = listOf(dvachmovie.api.FileItem(path = "testPath",
                    thumbnail = "testThumb",
                    md5 = "testMd5",
                    numThread = 1,
                    numPost = 1.toLong(),
                    date = "testDate",
                    threadName = "testTitle"))
            given(api.getThread(board, thread.toString())).willReturn(DvachThreadRequest(
                    title = "testTitle",
                    currentThread = "1",
                    threads = listOf(dvachmovie.api.model.gson.dvach.thread.ThreadsItem(
                            posts = listOf(PostsItem(num = 1,
                                    date = "testDate",
                                    files = listOf(FileItem(path = "testPath",
                                            thumbnail = "testThumb",
                                            md5 = "testMd5"))))))))
            Assert.assertEquals(fileItem, repository.getConcreteThreadByNum(board, thread.toString()))
        }
    }

    @Test(expected = TestException::class)
    fun getThreadEmittedError() {
        runBlocking {
            given(api.getThread(board, thread.toString())).willThrow(TestException())
            repository.getConcreteThreadByNum(board, thread.toString())
        }
    }

    @Test
    fun happyPassReportPost() {
        runBlocking {
            given(api.reportPost("report", board, thread, comment, post))
                    .willReturn(DvachReportRequest("test"))
            Assert.assertEquals("test", repository.reportPost(board, thread, post, comment))
        }
    }

    @Test(expected = TestException::class)
    fun reportPostEmittedError() {
        runBlocking {
            given(api.reportPost("report", board, thread, comment, post))
                    .willThrow(TestException())
            repository.reportPost(board, thread, post, comment)
        }
    }
}