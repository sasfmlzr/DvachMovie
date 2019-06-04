package dvachmovie.pipe.settingsstorage

import dvachmovie.usecase.settingsstorage.PutCookieUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PutCookiePipeTest {

    @InjectMocks
    lateinit var pipe: PutCookiePipe

    @Mock
    lateinit var useCase: PutCookieUseCase

    @Test
    fun `Happy pass`() {
        runBlocking {
            pipe.execute("test")
        }
    }
}
