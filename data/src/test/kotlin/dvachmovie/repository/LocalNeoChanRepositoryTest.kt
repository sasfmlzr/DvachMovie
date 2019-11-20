package dvachmovie.repository

import dvachmovie.AppConfig
import dvachmovie.TestException
import dvachmovie.api.NeoChanApi
import dvachmovie.api.model.gson.neochan.catalog.ExtraFilesDTO
import dvachmovie.api.model.gson.neochan.catalog.NeoChanCatalogDTO
import dvachmovie.api.model.gson.neochan.catalog.ThreadsDTO
import dvachmovie.api.model.gson.neochan.thread.NeoChanThreadDTO
import dvachmovie.api.model.gson.neochan.thread.PostsDTO
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
class LocalNeoChanRepositoryTest {

    @InjectMocks
    lateinit var repository: LocalNeoChanRepository

    @Mock
    lateinit var api: NeoChanApi

    @Mock
    lateinit var logger: Logger

    private val board = "test"
    private val thread = 1L
    private val threadName = "testTitle"

    @Test
    fun happyPassGetCatalog() {
        runBlocking {
            val pairs = listOf(Pair(3, "4"))
            given(api.getCatalog(board)).willReturn(listOf(NeoChanCatalogDTO(
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
                    dvachmovie.api.FileItem(path = "${AppConfig.NEOCHAN_URL}/$board/src/$postTim${postExt}",
                            thumbnail = "${AppConfig.NEOCHAN_URL}/$board/thumb/${postTim}.jpg",
                            md5 = "testMd5",
                            numThread = thread,
                            numPost = 1.toLong(),
                            date = "1",
                            threadName = threadName))
            given(api.getThread(board, thread.toString())).willReturn(NeoChanThreadDTO(
                    posts = listOf(PostsDTO(),
                            PostsDTO(tim = postTim.toString(),
                                    no = 1,
                                    time = 1,
                                    extraFiles = listOf(ExtraFilesDTO(md5 = "testMd5",
                                            ext = postExt,
                                            tim = postTim.toString()))))))
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
