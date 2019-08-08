package dvachmovie.pipe.db

import dvachmovie.db.data.NullThread
import dvachmovie.db.data.Thread
import dvachmovie.usecase.db.InsertionThreadListToDBUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InsertionThreadListToDBPipeTest {

    @InjectMocks
    lateinit var pipe: InsertionThreadListToDBPipe

    @Mock
    lateinit var useCase: InsertionThreadListToDBUseCase

    @Test
    fun `Happy pass`() {
        val testValue = listOf<Thread>(NullThread(0),
                NullThread(1),
                NullThread(2))
        runBlocking {
            pipe.execute(testValue)
        }
    }
}
