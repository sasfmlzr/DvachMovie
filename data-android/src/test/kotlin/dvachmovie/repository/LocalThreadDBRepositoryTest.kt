package dvachmovie.repository

import dvachmovie.TestException
import dvachmovie.db.model.ThreadDao
import dvachmovie.db.model.ThreadEntity
import kotlinx.coroutines.runBlocking
import org.joda.time.LocalDateTime
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocalThreadDBRepositoryTest {

    @InjectMocks
    lateinit var repository: LocalThreadDBRepository

    @Mock
    lateinit var dao: ThreadDao

    private val testThread = 0L
    private val testBaseUrl = "testBaseUrl"
    private val testNameThread = "testNameThread"
    private val testThreadEntity = ThreadEntity(testThread,
            LocalDateTime(),
            false,
            testNameThread,
            testBaseUrl)

    @Test
    fun `Happy pass get threads`() {
        runBlocking {
            given(dao.getThreads(testBaseUrl)).willReturn(listOf(testThreadEntity))
            Assert.assertEquals(listOf(testThreadEntity), repository.getThreads(testBaseUrl))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong - get threads`() {
        runBlocking {
            given(dao.getThreads(testBaseUrl)).willThrow(TestException())
            repository.getThreads(testBaseUrl)
        }
    }

    @Test
    fun `Happy pass get threads by num`() {
        runBlocking {
            given(dao.getThreadByNum(testThread.toString(), testBaseUrl)).willReturn(testThreadEntity)
            Assert.assertEquals(testThreadEntity, repository.getThreadByNum(testThread.toString(), testBaseUrl))
        }
    }

    @Test
    fun `Happy pass get threads by num but return null`() {
        runBlocking {
            given(dao.getThreadByNum(testThread.toString(), testBaseUrl)).willReturn(null)
            Assert.assertEquals(null, repository.getThreadByNum(testThread.toString(), testBaseUrl))
        }
    }

    @Test(expected = TestException::class)
    fun `Something was wrong - get threads by num`() {
        runBlocking {
            given(dao.getThreadByNum(testThread.toString(), testBaseUrl)).willThrow(TestException())
            repository.getThreadByNum(testThread.toString(), testBaseUrl)
        }
    }

    @Test
    fun `Happy pass insert`() {
        runBlocking {
            repository.insert(testThreadEntity)
        }
    }

    @Test
    fun `Happy pass insert all`() {
        runBlocking {
            repository.insertAll(listOf(testThreadEntity))
        }
    }

    @Test
    fun `Happy pass delete all`() {
        runBlocking {
            repository.deleteAll()
        }
    }
}
