package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsStorage.PutIsListBtnVisibleUseCase
import dvachmovie.usecase.utils.ShuffleMoviesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PutIsListBtnVisiblePipeTest {

    @InjectMocks
    lateinit var pipe: PutIsListBtnVisiblePipe

    @Mock
    lateinit var useCase: PutIsListBtnVisibleUseCase

    @Test
    fun `Happy pass`() {
        runBlocking {
            pipe.execute(false)
        }
    }
}
