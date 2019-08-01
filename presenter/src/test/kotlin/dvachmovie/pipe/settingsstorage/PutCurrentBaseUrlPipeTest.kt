package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsstorage.PutCurrentBaseUrlUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PutCurrentBaseUrlPipeTest {

    @InjectMocks
    lateinit var pipe: PutCurrentBaseUrlPipe

    @Mock
    lateinit var useCase: PutCurrentBaseUrlUseCase

    @Test
    fun `Happy pass`() {
        runBlocking {
            pipe.execute("test")
        }
    }
}
