package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsStorage.PutBoardUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PutBoardPipeTest {

    @InjectMocks
    lateinit var pipe: PutBoardPipe

    @Mock
    lateinit var useCase: PutBoardUseCase

    @Test
    fun `Happy pass`() {
        runBlocking {
            pipe.execute("test")
        }
    }
}
