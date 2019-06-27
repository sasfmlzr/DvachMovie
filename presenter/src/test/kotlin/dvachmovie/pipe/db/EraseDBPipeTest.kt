package dvachmovie.pipe.db

import dvachmovie.usecase.db.EraseDBUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class EraseDBPipeTest {

    @InjectMocks
    lateinit var pipe: EraseDBPipe

    @Mock
    lateinit var useCase: EraseDBUseCase

    @Test
    fun `Happy pass`() {
        runBlocking {
            pipe.execute(Unit)
        }
    }
}
