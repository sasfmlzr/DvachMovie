package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsstorage.PutIsAllowGestureUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PutIsAllowGesturePipeTest {

    @InjectMocks
    lateinit var pipe: PutIsAllowGesturePipe

    @Mock
    lateinit var useCase: PutIsAllowGestureUseCase

    @Test
    fun `Happy pass`() {
        runBlocking {
            pipe.execute(false)
        }
    }
}
