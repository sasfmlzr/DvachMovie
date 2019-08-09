package dvachmovie.pipe.db

import dvachmovie.db.data.NullThread
import dvachmovie.usecase.db.InsertionThreadToDBUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InsertionThreadToDBPipeTest {

    @InjectMocks
    lateinit var pipe: InsertionThreadToDBPipe

    @Mock
    lateinit var useCase: InsertionThreadToDBUseCase

    @Test
    fun `Happy pass`() {
        val testValue = NullThread(1)
        runBlocking {
            pipe.execute(testValue)
        }
    }
}
