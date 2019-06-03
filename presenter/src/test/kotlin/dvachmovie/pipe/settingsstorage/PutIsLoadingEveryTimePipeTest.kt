package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsStorage.PutIsLoadingEveryTimeUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PutIsLoadingEveryTimePipeTest {

    @InjectMocks
    lateinit var pipe: PutIsLoadingEveryTimePipe

    @Mock
    lateinit var useCase: PutIsLoadingEveryTimeUseCase

    @Test
    fun `Happy pass`() {
        runBlocking {
            pipe.execute(false)
        }
    }
}
